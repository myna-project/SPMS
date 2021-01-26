package it.mynaproject.spms.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.mynaproject.spms.dao.SystemPreparationPhaseDao;
import it.mynaproject.spms.domain.SystemPreparationPhase;

@Repository
public class SystemPreparationPhaseDaoImpl extends BaseDaoImpl implements SystemPreparationPhaseDao {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void persist(SystemPreparationPhase systemPreparationPhase) {

		log.info("Creating new systemPreparationPhase: {}", systemPreparationPhase.toString());

		em.persist(systemPreparationPhase);
		em.flush();
	}

	@Override
	public void update(SystemPreparationPhase systemPreparationPhase) {

		log.info("Updating systemPreparationPhase: {}", systemPreparationPhase.toString());

		em.persist(em.merge(systemPreparationPhase));
		em.flush();
	}

	@Override
	public void delete(SystemPreparationPhase systemPreparationPhase) {

		log.info("Deleting systemPreparationPhase: {}", systemPreparationPhase.toString());

		em.remove(em.merge(systemPreparationPhase));
		em.flush();
	}

	@Override
	public SystemPreparationPhase getSystemPreparationPhase(Integer id) {

		log.debug("Getting systemPreparationPhase with id: {}", id );

		Query q = em.createQuery("FROM SystemPreparationPhase WHERE id = :id");
		q.setParameter("id", id);

		try {
			return (SystemPreparationPhase) q.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SystemPreparationPhase> getSystemPreparationPhases() {

		return em.createQuery("FROM SystemPreparationPhase").getResultList();
	}
}