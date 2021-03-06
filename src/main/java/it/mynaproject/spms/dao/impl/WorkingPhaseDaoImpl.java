package it.mynaproject.spms.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.mynaproject.spms.dao.WorkingPhaseDao;
import it.mynaproject.spms.domain.WorkingPhase;

@Repository
public class WorkingPhaseDaoImpl extends BaseDaoImpl implements WorkingPhaseDao {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void persist(WorkingPhase workingPhase) {

		log.info("Creating new workingPhase: {}", workingPhase.toString());

		em.persist(workingPhase);
		em.flush();
	}

	@Override
	public void update(WorkingPhase workingPhase) {

		log.info("Updating workingPhase: {}", workingPhase.toString());

		em.persist(em.merge(workingPhase));
		em.flush();
	}

	@Override
	public void delete(WorkingPhase workingPhase) {

		log.info("Deleting workingPhase: {}", workingPhase.toString());

		em.remove(em.merge(workingPhase));
		em.flush();
	}

	@Override
	public WorkingPhase getWorkingPhase(Integer id) {

		log.debug("Getting workingPhase with id: {}", id );

		Query q = em.createQuery("FROM WorkingPhase WHERE id = :id");
		q.setParameter("id", id);

		try {
			WorkingPhase w = (WorkingPhase) q.getSingleResult();
			initializeWorkingPhase(w);
			return w;
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkingPhase> getWorkingPhases() {

		List<WorkingPhase> wList = em.createQuery("FROM WorkingPhase").getResultList();
		for (WorkingPhase w : wList)
			initializeWorkingPhase(w);

		return wList;
	}

	private void initializeWorkingPhase(WorkingPhase w) {
		Hibernate.initialize(w.getWorkingPhaseMeasureList());
	}
}