package it.mynaproject.spms.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.mynaproject.spms.dao.UserDao;
import it.mynaproject.spms.domain.Role;
import it.mynaproject.spms.domain.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void persist(User user) {

		log.info("Creating new user: {}", user.getUsername());

		em.persist(user);
		em.flush();
	}

	@Override
	public void update(User user) {

		log.info("Updating user: {}", user.getUsername());

		em.persist(em.merge(user));
		em.flush();
	}

	@Override
	public void delete(User user) {

		log.info("Deleting user: {}", user.getUsername());

		em.remove(em.merge(user));
		em.flush();
	}
	
	private void initializeUser(User u) {
		Hibernate.initialize(u.getRoleList());
	}

	@Override
	public User getUser(Integer id) {

		log.debug("Getting users with id: {}", id );

		Query q = em.createQuery("FROM User WHERE id = :id");
		q.setParameter("id", id);

		try {
			User u = (User) q.getSingleResult();
			initializeUser(u);
			return u;
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> getUsers() {

		log.debug("Getting users");

		List<User> users = em.createQuery("FROM User").getResultList();
		for (User u : users){
			initializeUser(u);
		}
		return users;
	}

	@Override
	public List<Role> getRoles(Integer id) {

		User u = this.getUser(id);
		initializeUser(u);
		return (u == null) ? null : u.getRoleList();
	}

	@Override
	public User getUserByUsername(String username) {

		log.debug("Getting user with username: {}", username);

		Query q = em.createQuery("FROM User WHERE username LIKE :username");
		q.setParameter("username", username);

		try {
			User u = (User) q.getSingleResult();
			Hibernate.initialize(u.getRoleList());
			return u;
		} catch (NoResultException nre) {
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Boolean checkEmailExists(String email, Integer id) {

		Query q = em.createQuery("FROM User WHERE email LIKE :email");
		q.setParameter("email", email);

		List<User> users = (List<User>) q.getResultList();

		Boolean mailExists = false;
		for (User u : users)
			if (((id != null) && (u.getId() != id)) || (id == null))
				mailExists = true;

		return mailExists;
	}
}
