package it.mynaproject.gestprod.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import it.mynaproject.gestprod.dao.ProductionOrderDao;
import it.mynaproject.gestprod.domain.ProductionOrder;

@Repository
public class ProductionOrderDaoImpl extends BaseDaoImpl implements ProductionOrderDao {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void persist(ProductionOrder productionOrder) {

		log.info("Creating new productionOrder: {}", productionOrder.toString());

		em.persist(productionOrder);
		em.flush();
	}

	@Override
	public void update(ProductionOrder productionOrder) {

		log.info("Updating productionOrder: {}", productionOrder.toString());

		em.persist(em.merge(productionOrder));
		em.flush();
	}

	@Override
	public void delete(ProductionOrder productionOrder) {

		log.info("Deleting productionOrder: {}", productionOrder.toString());

		em.remove(em.merge(productionOrder));
		em.flush();
	}

	@Override
	public ProductionOrder getProductionOrder(Integer id) {

		log.debug("Getting productionOrder with id: {}", id );

		Query q = em.createQuery("FROM ProductionOrder WHERE id = :id");
		q.setParameter("id", id);

		try {
			ProductionOrder p = (ProductionOrder) q.getSingleResult();
			initializeProductionOrder(p);
			return p;
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductionOrder> getProductionOrders() {

		List<ProductionOrder> pList = em.createQuery("FROM ProductionOrder").getResultList();
		for(ProductionOrder p : pList) {
			initializeProductionOrder(p);
		}
		return pList;
	}
	
	private void initializeProductionOrder(ProductionOrder p) {
		Hibernate.initialize(p.getAdditiveProductionOrderList());
		Hibernate.initialize(p.getSettingPhaseList());
		Hibernate.initialize(p.getSystemPreparationPhaseList());
		Hibernate.initialize(p.getCleaningPhaseList());
		Hibernate.initialize(p.getWorkingPhaseList());
		Hibernate.initialize(p.getValidationPhaseList());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ProductionOrder checkProductionOrderExists(String name, @Nullable Integer id) {

		Query q = em.createQuery("FROM ProductionOrder WHERE production_order_code LIKE :name");
		q.setParameter("name", name);

		List<ProductionOrder> productionOrders = (List<ProductionOrder>) q.getResultList();

		ProductionOrder productionOrderExists = null;
		for (ProductionOrder r : productionOrders)
			if (((id != null) && (r.getId() != id)) || (id == null))
				productionOrderExists = r;

		return productionOrderExists;
	}
}
