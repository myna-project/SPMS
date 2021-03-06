package it.mynaproject.spms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.spms.dao.UserDao;
import it.mynaproject.spms.domain.Role;
import it.mynaproject.spms.domain.User;
import it.mynaproject.spms.exception.ConflictException;
import it.mynaproject.spms.exception.NotFoundException;
import it.mynaproject.spms.model.UserJson;
import it.mynaproject.spms.service.RoleService;
import it.mynaproject.spms.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PasswordEncoder passwordEncoder;	

	@Transactional(readOnly = true)
	@Override
	public User getUser(Integer id, String username, Boolean isAdmin) {

		User u = this.userDao.getUser(id);
		if (u == null || (!isAdmin && !u.getUsername().equals(username)))
			throw new NotFoundException(404, "User " + id + " not found");

		return u;
	}

	@Transactional(readOnly = true)
	@Override
	public List<User> getUsers() {
		return this.userDao.getUsers();
	}

	@Transactional
	@Override
	public void persist(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		this.userDao.persist(user);
	}

	@Transactional
	@Override
	public User createUserFromInput(UserJson input, String username) {

		log.info("Creating new user: {}", input.toString().replaceFirst("password=.*,", ""));

		User user = new User();

		if (!checkNameForUser(input.getUsername()))
			throw new ConflictException(1001, "Username " + input.getUsername() + " not available");

		if (this.userDao.checkEmailExists(input.getEmail(), null))
			throw new ConflictException(1002, "Email address " + input.getEmail() + " already registered");

		List<Role> roles = new ArrayList<>();

		for (Integer id : input.getRoles())
			roles.add(this.roleService.getRole(id));

		user.populateUserFromInput(input, roles);

		this.persist(user);

		return user;
	}

	@Transactional
	@Override
	public void update(User user, Boolean changePassword) {

		if (changePassword)
			user.setPassword(passwordEncoder.encode(user.getPassword()));

		this.userDao.update(user);
	}

	@Transactional
	@Override
	public User updateUserFromInput(Integer id, UserJson input, String username, Boolean isAdmin) {

		log.info("Updating user with id {} from input: {}", id, input.toString().replaceFirst("password=.*,", ""));

		User user = this.getUser(id, username, isAdmin);

		if (!this.checkNameForUser(input.getUsername(), input.getId()))
			throw new ConflictException(1001, "Username " + input.getUsername() + " not available");

		if (this.userDao.checkEmailExists(input.getEmail(), input.getId()))
			throw new ConflictException(1002, "Email address " + input.getEmail() + " already registered");

		boolean changePassword = false;
		if (input.getOldPassword() != null) {
			if (input.getPassword().equals(input.getOldPassword()))
				throw new ConflictException(1004, "Old and new password must be different");

			if (!this.passwordsMatch(user.getPassword(), input.getOldPassword()))
				throw new ConflictException(1005, "Old password does not match");

			changePassword = true;
		}

		List <Role> roles = new ArrayList<>();
		for (Integer idRole : input.getRoles())
			roles.add(this.roleService.getRole(idRole));

		user.populateUserFromInput(input, roles);

		this.update(user, changePassword);

		return user;
	}

	@Transactional
	@Override
	public void deleteUserById(Integer id, String username, Boolean isAdmin) {

		log.info("Deleting user: {}", id);

		this.userDao.delete(this.getUser(id, username, isAdmin));
	}

	@Transactional(readOnly = true)
	@Override
	public List<Role> getRoles(Integer id) {
		return this.userDao.getRoles(id);
	}

	@Transactional(readOnly = true)
	@Override
	public User getUserByUsername(String username) {
		return this.userDao.getUserByUsername(username);
	}

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username) {

		User user = this.userDao.getUserByUsername(username);
		if (user == null)
			throw new UsernameNotFoundException("User not found");

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, user.getRoleList());
	}

	private Boolean checkNameForUser(String name) {

		if (this.userDao.getUserByUsername(name) != null)
			return false;

		return true;
	}

	private Boolean checkNameForUser(String name, Integer id) {

		User u = this.userDao.getUserByUsername(name);
		if (u != null)
			if (u.getId() != id)
				return false;

		return true;
	}

	private Boolean passwordsMatch(String oldPsw, String newPsw) {
		return this.passwordEncoder.matches(newPsw, oldPsw);
	}
}
