package it.mynaproject.gestprod.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.mynaproject.gestprod.dao.WorkingPhaseMeasureDao;
import it.mynaproject.gestprod.domain.WorkingPhaseMeasure;

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

	@Override
	public WorkingPhaseMeasure getWorkingPhaseMeasure(Integer id) {

		log.debug("Getting workingPhaseMeasure with id: {}", id );

		Query q = em.createQuery("FROM WorkingPhaseMeasure WHERE id = :id");
		q.setParameter("id", id);

		try {
			return (WorkingPhaseMeasure) q.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WorkingPhaseMeasure> getWorkingPhaseMeasures() {
		return em.createQuery("FROM WorkingPhaseMeasure").getResultList();
	}
}
