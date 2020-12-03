package it.mynaproject.gestprod.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import it.mynaproject.gestprod.dao.AdditiveDao;
import it.mynaproject.gestprod.domain.Additive;

@Repository
public class AdditiveDaoImpl extends BaseDaoImpl implements AdditiveDao {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void persist(Additive additive) {

		log.info("Creating new additive: {}", additive.toString());

		em.persist(additive);
		em.flush();
	}

	@Override
	public void update(Additive additive) {

		log.info("Updating additive: {}", additive.toString());

		em.persist(em.merge(additive));
		em.flush();
	}

	@Override
	public void delete(Additive additive) {

		log.info("Deleting additive: {}", additive.toString());

		em.remove(em.merge(additive));
		em.flush();
	}

	@Override
	public Additive getAdditive(Integer id) {

		log.debug("Getting additive with id: {}", id );

		Query q = em.createQuery("FROM Additive WHERE id = :id");
		q.setParameter("id", id);

		try {
			return (Additive) q.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Additive> getAdditives() {

		return em.createQuery("FROM Additive").getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Additive checkAdditiveExists(String name, @Nullable Integer id) {

		Query q = em.createQuery("FROM Additive WHERE name LIKE :name");
		q.setParameter("name", name);

		List<Additive> additives = (List<Additive>) q.getResultList();

		Additive additiveExists = null;
		for (Additive r : additives)
			if (((id != null) && (r.getId() != id)) || (id == null))
				additiveExists = r;

		return additiveExists;
	}
}
