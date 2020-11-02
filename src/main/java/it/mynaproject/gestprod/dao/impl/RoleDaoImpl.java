package it.mynaproject.gestprod.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.mynaproject.gestprod.dao.RoleDao;
import it.mynaproject.gestprod.domain.Role;
import it.mynaproject.gestprod.domain.User;

@Repository
public class RoleDaoImpl extends BaseDaoImpl implements RoleDao {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void persist(Role role) {

		log.info("Creating new role: {}", role.toString());

		em.persist(role);
		em.flush();
	}

	@Override
	public void update(Role role) {

		log.info("Updating role: {}", role.toString());

		em.persist(em.merge(role));
		em.flush();
	}

	@Override
	public void delete(Role role) {

		log.info("Deleting role: {}", role.toString());

		em.remove(em.merge(role));
		em.flush();
	}

	@Override
	public Role getRole(Integer id) {

		log.debug("Getting role with id: {}", id );

		Query q = em.createQuery("FROM Role WHERE id = :id");
		q.setParameter("id", id);

		try {
			Role r = (Role) q.getSingleResult();
			Hibernate.initialize(r.getUsers());
			return r;
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getRoles() {

		List<Role> roles = em.createQuery("FROM Role").getResultList();
		for (Role r : roles)
			Hibernate.initialize(r.getUsers());

		return roles;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Boolean checkRoleExists(String name, Integer id) {

		Query q = em.createQuery("FROM Role WHERE name LIKE :name");
		q.setParameter("name", name);

		List<Role> roles = (List<Role>) q.getResultList();

		Boolean roleExists = false;
		for (Role r : roles)
			if (((id != null) && (r.getId() != id)) || (id == null))
				roleExists = true;

		return roleExists;
	}

	@Override
	public List<User> getUsers(Integer id) {

		Role r = this.getRole(id);
		return (r == null) ? null : r.getUsers();
	}
}
