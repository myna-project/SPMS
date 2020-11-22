package it.mynaproject.gestprod.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.mynaproject.gestprod.dao.AdditiveProductionOrderDao;
import it.mynaproject.gestprod.domain.AdditiveProductionOrder;

@Repository
public class AdditiveProductionOrderDaoImpl extends BaseDaoImpl implements AdditiveProductionOrderDao {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void persist(AdditiveProductionOrder additiveProductionOrder) {

		log.info("Creating new additiveProductionOrder: {}", additiveProductionOrder.toString());

		em.persist(additiveProductionOrder);
		em.flush();
	}

	@Override
	public void update(AdditiveProductionOrder additiveProductionOrder) {

		log.info("Updating additiveProductionOrder: {}", additiveProductionOrder.toString());

		em.persist(em.merge(additiveProductionOrder));
		em.flush();
	}

	@Override
	public void delete(AdditiveProductionOrder additiveProductionOrder) {

		log.info("Deleting additiveProductionOrder: {}", additiveProductionOrder.toString());

		em.remove(em.merge(additiveProductionOrder));
		em.flush();
	}

	@Override
	public AdditiveProductionOrder getAdditiveProductionOrder(Integer id) {

		log.debug("Getting additiveProductionOrder with id: {}", id );

		Query q = em.createQuery("FROM AdditiveProductionOrder WHERE id = :id");
		q.setParameter("id", id);

		try {
			return (AdditiveProductionOrder) q.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdditiveProductionOrder> getAdditiveProductionOrders() {

		return em.createQuery("FROM AdditiveProductionOrder").getResultList();
	}
}
