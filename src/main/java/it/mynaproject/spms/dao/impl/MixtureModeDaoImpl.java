package it.mynaproject.spms.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import it.mynaproject.spms.dao.MixtureModeDao;
import it.mynaproject.spms.domain.MixtureMode;

@Repository
public class MixtureModeDaoImpl extends BaseDaoImpl implements MixtureModeDao {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void persist(MixtureMode mixtureMode) {

		log.info("Creating new mixtureMode: {}", mixtureMode.toString());

		em.persist(mixtureMode);
		em.flush();
	}

	@Override
	public void update(MixtureMode mixtureMode) {

		log.info("Updating mixtureMode: {}", mixtureMode.toString());

		em.persist(em.merge(mixtureMode));
		em.flush();
	}

	@Override
	public void delete(MixtureMode mixtureMode) {

		log.info("Deleting mixtureMode: {}", mixtureMode.toString());

		em.remove(em.merge(mixtureMode));
		em.flush();
	}

	@Override
	public MixtureMode getMixtureMode(Integer id) {

		log.debug("Getting mixtureMode with id: {}", id );

		Query q = em.createQuery("FROM MixtureMode WHERE id = :id");
		q.setParameter("id", id);

		try {
			return (MixtureMode) q.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MixtureMode> getMixtureModes() {

		return em.createQuery("FROM MixtureMode").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public MixtureMode checkMixtureModeExists(String name, @Nullable Integer id) {

		Query q = em.createQuery("FROM MixtureMode WHERE name LIKE :name");
		q.setParameter("name", name);

		List<MixtureMode> mixtureModes = (List<MixtureMode>) q.getResultList();

		MixtureMode mixtureModeExists = null;
		for (MixtureMode r : mixtureModes)
			if (((id != null) && (r.getId() != id)) || (id == null))
				mixtureModeExists = r;

		return mixtureModeExists;
	}
}