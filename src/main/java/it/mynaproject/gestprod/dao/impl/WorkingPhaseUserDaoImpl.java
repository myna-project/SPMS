package it.mynaproject.gestprod.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.mynaproject.gestprod.dao.WorkingPhaseUserDao;
import it.mynaproject.gestprod.domain.WorkingPhaseUser;

// TODO fix sql queries on table names

@Repository
public class WorkingPhaseUserDaoImpl extends BaseDaoImpl implements WorkingPhaseUserDao {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void persist(WorkingPhaseUser workingPhaseUser) {

		log.info("Creating new workingPhaseUser: {}", workingPhaseUser.toString());

		em.persist(workingPhaseUser);
		em.flush();
	}

	@Override
	public void update(WorkingPhaseUser workingPhaseUser) {

		log.info("Updating workingPhaseUser: {}", workingPhaseUser.toString());

		em.persist(em.merge(workingPhaseUser));
		em.flush();
	}

	@Override
	public void delete(WorkingPhaseUser workingPhaseUser) {

		log.info("Deleting workingPhaseUser: {}", workingPhaseUser.toString());

		em.remove(em.merge(workingPhaseUser));
		em.flush();
	}

	@Override
	public WorkingPhaseUser getWorkingPhaseUser(Integer id) {

		log.debug("Getting workingPhaseUser with id: {}", id );

		Query q = em.createQuery("FROM WorkingPhaseUser WHERE id = :id");
		q.setParameter("id", id);

		try {
			return (WorkingPhaseUser) q.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkingPhaseUser> getWorkingPhaseUsers() {

		return em.createQuery("FROM WorkingPhaseUser").getResultList();
	}
}