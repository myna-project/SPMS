package it.mynaproject.gestprod.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.mynaproject.gestprod.dao.WorkingPhaseMeasureDao;
import it.mynaproject.gestprod.domain.WorkingPhaseMeasure;
import it.mynaproject.gestprod.domain.User;

@Repository
public class WorkingPhaseMeasureDaoImpl extends BaseDaoImpl implements WorkingPhaseMeasureDao {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void persist(WorkingPhaseMeasure workingPhaseMeasure) {

		log.info("Creating new workingPhaseMeasure: {}", workingPhaseMeasure.toString());

		em.persist(workingPhaseMeasure);
		em.flush();
	}

	@Override
	public void update(WorkingPhaseMeasure workingPhaseMeasure) {

		log.info("Updating workingPhaseMeasure: {}", workingPhaseMeasure.toString());

		em.persist(em.merge(workingPhaseMeasure));
		em.flush();
	}

	@Override
	public void delete(WorkingPhaseMeasure workingPhaseMeasure) {

		log.info("Deleting workingPhaseMeasure: {}", workingPhaseMeasure.toString());

		em.remove(em.merge(workingPhaseMeasure));
		em.flush();
	}
	
	private void initializeWorkingPhaseMeasure(WorkingPhaseMeasure w) {
		Hibernate.initialize(w.getUser());
		User u = w.getUser();
		Hibernate.initialize(u.getRoleList());
	}

	@Override
	public WorkingPhaseMeasure getWorkingPhaseMeasure(Integer id) {

		log.debug("Getting workingPhaseMeasure with id: {}", id );

		Query q = em.createQuery("FROM WorkingPhaseMeasure WHERE id = :id");
		q.setParameter("id", id);

		try {
			WorkingPhaseMeasure w = (WorkingPhaseMeasure) q.getSingleResult();
			initializeWorkingPhaseMeasure(w);
			return w;
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkingPhaseMeasure> getWorkingPhaseMeasures() {

		List<WorkingPhaseMeasure> wl = em.createQuery("FROM WorkingPhaseMeasure").getResultList();
		for(WorkingPhaseMeasure w : wl) {
			initializeWorkingPhaseMeasure(w);
		}
		return wl;
	}
}
