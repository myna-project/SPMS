package it.mynaproject.gestprod.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.mynaproject.gestprod.dao.ValidationPhaseDao;
import it.mynaproject.gestprod.domain.ValidationPhase;

// TODO fix sql queries on table names

@Repository
public class ValidationPhaseDaoImpl extends BaseDaoImpl implements ValidationPhaseDao {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void persist(ValidationPhase validationPhase) {

		log.info("Creating new validationPhase: {}", validationPhase.toString());

		em.persist(validationPhase);
		em.flush();
	}

	@Override
	public void update(ValidationPhase validationPhase) {

		log.info("Updating validationPhase: {}", validationPhase.toString());

		em.persist(em.merge(validationPhase));
		em.flush();
	}

	@Override
	public void delete(ValidationPhase validationPhase) {

		log.info("Deleting validationPhase: {}", validationPhase.toString());

		em.remove(em.merge(validationPhase));
		em.flush();
	}

	@Override
	public ValidationPhase getValidationPhase(Integer id) {

		log.debug("Getting validationPhase with id: {}", id );

		Query q = em.createQuery("FROM ValidationPhase WHERE id = :id");
		q.setParameter("id", id);

		try {
			return (ValidationPhase) q.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ValidationPhase> getValidationPhases() {

		return em.createQuery("FROM ValidationPhase").getResultList();
	}
}
