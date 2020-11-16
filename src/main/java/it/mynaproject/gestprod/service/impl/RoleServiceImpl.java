package it.mynaproject.gestprod.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.gestprod.dao.RoleDao;
import it.mynaproject.gestprod.domain.Role;
import it.mynaproject.gestprod.domain.User;
import it.mynaproject.gestprod.exception.ConflictException;
import it.mynaproject.gestprod.exception.NotFoundException;
import it.mynaproject.gestprod.model.RoleJson;
import it.mynaproject.gestprod.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RoleDao roleDao;

	@Transactional(readOnly = true)
	@Override
	public Role getRole(Integer id) {

		Role r = this.roleDao.getRole(id);
		if (r == null)
			throw new NotFoundException(404, "Role " + id + " not found");

		return r;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Role> getRoles() {
		return this.roleDao.getRoles();
	}

	@Transactional
	@Override
	public void persist(Role role) {
		this.roleDao.persist(role);
	}

	@Transactional
	@Override
	public Role createRoleFromJson(RoleJson input) {

		log.info("Creating new role: {}", input.toString());

		if (this.roleDao.checkRoleExists(input.getName(), null))
			throw new ConflictException(409, 2001, "Role " + input.getName() + " already exists");

		Role role = new Role();
		role.populateRoleFromInput(input);

		this.persist(role);

		return role;
	}

	@Transactional
	@Override
	public void update(Role role) {
		this.roleDao.update(role);
	}

	@Transactional
	@Override
	public Role updateRoleFromJson(Integer id, RoleJson input) {

		log.info("Updating role with id: {}", id);

		if (this.roleDao.checkRoleExists(input.getName(), id))
			throw new ConflictException(409, 2001, "Role " + input.getName() + " already exists");

		Role role = this.getRole(id);
		role.populateRoleFromInput(input);

		this.update(role);

		return role;
	}
	
//  TODO non ci sono in definizione REST
	@Transactional
	@Override
	public void deleteRoleById(Integer id) {

		log.info("Deleting role: {}", id);

		Role r = this.getRole(id);

		if (r.getUsers().size() > 0)
			throw new ConflictException(409, 2101, "Cannot delete role " + r.getName() + " because it is assigned to one or more users");

		this.roleDao.delete(r);
	}

	@Transactional(readOnly = true)
	@Override
	public List<User> getUsersByRoleId(Integer id) {
		return this.roleDao.getUsers(id);
	}
}
