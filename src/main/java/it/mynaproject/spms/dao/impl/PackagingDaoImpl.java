package it.mynaproject.spms.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import it.mynaproject.spms.dao.PackagingDao;
import it.mynaproject.spms.domain.Packaging;

@Repository
public class PackagingDaoImpl extends BaseDaoImpl implements PackagingDao {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void persist(Packaging packaging) {

		log.info("Creating new packaging: {}", packaging.toString());

		em.persist(packaging);
		em.flush();
	}

	@Override
	public void update(Packaging packaging) {

		log.info("Updating packaging: {}", packaging.toString());

		em.persist(em.merge(packaging));
		em.flush();
	}

	@Override
	public void delete(Packaging packaging) {

		log.info("Deleting packaging: {}", packaging.toString());

		em.remove(em.merge(packaging));
		em.flush();
	}

	@Override
	public Packaging getPackaging(Integer id) {

		log.debug("Getting packaging with id: {}", id );

		Query q = em.createQuery("FROM Packaging WHERE id = :id");
		q.setParameter("id", id);

		try {
			return (Packaging) q.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Packaging> getPackagings() {

		return em.createQuery("FROM Packaging").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Packaging checkPackagingExists(String name, @Nullable Integer id) {

		Query q = em.createQuery("FROM Packaging WHERE packaging_mode LIKE :name");
		q.setParameter("name", name);

		List<Packaging> packagings = (List<Packaging>) q.getResultList();

		Packaging packagingExists = null;
		for (Packaging r : packagings)
			if (((id != null) && (r.getId() != id)) || (id == null))
				packagingExists = r;

		return packagingExists;
	}
}
