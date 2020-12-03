package it.mynaproject.gestprod.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.mynaproject.gestprod.dao.SettingPhaseDao;
import it.mynaproject.gestprod.domain.SettingPhase;

@Repository
public class SettingPhaseDaoImpl extends BaseDaoImpl implements SettingPhaseDao {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void persist(SettingPhase settingPhase) {

		log.info("Creating new settingPhase: {}", settingPhase.toString());

		em.persist(settingPhase);
		em.flush();
	}

	@Override
	public void update(SettingPhase settingPhase) {

		log.info("Updating settingPhase: {}", settingPhase.toString());

		em.persist(em.merge(settingPhase));
		em.flush();
	}

	@Override
	public void delete(SettingPhase settingPhase) {

		log.info("Deleting settingPhase: {}", settingPhase.toString());

		em.remove(em.merge(settingPhase));
		em.flush();
	}

	@Override
	public SettingPhase getSettingPhase(Integer id) {

		log.debug("Getting settingPhase with id: {}", id );

		Query q = em.createQuery("FROM SettingPhase WHERE id = :id");
		q.setParameter("id", id);

		try {
			return (SettingPhase) q.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SettingPhase> getSettingPhases() {

		return em.createQuery("FROM SettingPhase").getResultList();
	}
}
