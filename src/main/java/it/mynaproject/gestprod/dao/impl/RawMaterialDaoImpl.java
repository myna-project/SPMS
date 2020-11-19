package it.mynaproject.gestprod.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.mynaproject.gestprod.dao.RawMaterialDao;
import it.mynaproject.gestprod.domain.RawMaterial;

@Repository
public class RawMaterialDaoImpl extends BaseDaoImpl implements RawMaterialDao {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void persist(RawMaterial rawMaterial) {

		log.info("Creating new rawMaterial: {}", rawMaterial.toString());

		em.persist(rawMaterial);
		em.flush();
	}

	@Override
	public void update(RawMaterial rawMaterial) {

		log.info("Updating rawMaterial: {}", rawMaterial.toString());

		em.persist(em.merge(rawMaterial));
		em.flush();
	}

	@Override
	public void delete(RawMaterial rawMaterial) {

		log.info("Deleting rawMaterial: {}", rawMaterial.toString());

		em.remove(em.merge(rawMaterial));
		em.flush();
	}

	@Override
	public RawMaterial getRawMaterial(Integer id) {

		log.debug("Getting rawMaterial with id: {}", id );

		Query q = em.createQuery("FROM RawMaterial WHERE id = :id");
		q.setParameter("id", id);

		try {
			return (RawMaterial) q.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RawMaterial> getRawMaterials() {

		return em.createQuery("FROM RawMaterial").getResultList();
	}
}
