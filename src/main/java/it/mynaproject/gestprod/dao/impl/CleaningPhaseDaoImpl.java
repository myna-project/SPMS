package it.mynaproject.gestprod.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.mynaproject.gestprod.dao.CleaningPhaseDao;
import it.mynaproject.gestprod.domain.CleaningPhase;

// TODO fix sql queries on table names

@Repository
public class CleaningPhaseDaoImpl extends BaseDaoImpl implements CleaningPhaseDao {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void persist(CleaningPhase cleaningPhase) {

		log.info("Creating new cleaningPhase: {}", cleaningPhase.toString());

		em.persist(cleaningPhase);
		em.flush();
	}

	@Override
	public void update(CleaningPhase cleaningPhase) {

		log.info("Updating cleaningPhase: {}", cleaningPhase.toString());

		em.persist(em.merge(cleaningPhase));
		em.flush();
	}

	@Override
	public void delete(CleaningPhase cleaningPhase) {

		log.info("Deleting cleaningPhase: {}", cleaningPhase.toString());

		em.remove(em.merge(cleaningPhase));
		em.flush();
	}

	@Override
	public CleaningPhase getCleaningPhase(Integer id) {

		log.debug("Getting cleaningPhase with id: {}", id );

		Query q = em.createQuery("FROM CleaningPhase WHERE id = :id");
		q.setParameter("id", id);

		try {
			return (CleaningPhase) q.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CleaningPhase> getCleaningPhases() {

		return em.createQuery("FROM CleaningPhase").getResultList();
	}
}