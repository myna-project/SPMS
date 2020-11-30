package it.mynaproject.gestprod.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.gestprod.dao.AdditiveProductionOrderDao;
import it.mynaproject.gestprod.domain.AdditiveProductionOrder;
import it.mynaproject.gestprod.domain.ProductionOrder;
import it.mynaproject.gestprod.exception.*;
import it.mynaproject.gestprod.model.AdditiveProductionOrderJson;
import it.mynaproject.gestprod.service.AdditiveProductionOrderService;

@Service
public class AdditiveProductionOrderServiceImpl implements AdditiveProductionOrderService { // TODO fix codici errore (mancano nella definizione dei servizi

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AdditiveProductionOrderDao additiveProductionOrderDao;

	// TODO su tutti ricontrollare codici errore
	@Transactional(readOnly = true)
	@Override
	public AdditiveProductionOrder getAdditiveProductionOrder(Integer additiveId, Integer productionOrderId, Boolean isAdmin) {

		AdditiveProductionOrder p = this.additiveProductionOrderDao.getAdditiveProductionOrder(additiveId, productionOrderId);
		if (p == null)
			throw new NotFoundException(404, "AdditiveProductionOrder " + additiveId + ", " + productionOrderId + " not found");
		return p;
	}

	@Transactional(readOnly = true)
	@Override
	public List<AdditiveProductionOrder> getAdditiveProductionOrders(Boolean isAdmin) {

		List<AdditiveProductionOrder> pList = new ArrayList<AdditiveProductionOrder>();
		for (AdditiveProductionOrder p : this.additiveProductionOrderDao.getAdditiveProductionOrders())
			pList.add(p);

		return pList;
	}

	@Transactional
	@Override
	public void persist(AdditiveProductionOrder additiveProductionOrder) {
		this.additiveProductionOrderDao.persist(additiveProductionOrder);
	}

	@Transactional
	@Override
	public AdditiveProductionOrder createAdditiveProductionOrderFromJson(AdditiveProductionOrderJson input) {

		log.info("Creating new additiveProductionOrder: {}", input.toString());

		if(this.additiveProductionOrderDao.getAdditiveProductionOrder(input.getAdditive_id(), input.getProduction_order_code_id()) != null) { // TODO fix id
			throw new ConflictException(2001, "AdditiveProductionOrder already registered with id: " + input.getAdditive_id() + ", " + input.getProduction_order_code_id());
		}
		AdditiveProductionOrder additiveProductionOrder = new AdditiveProductionOrder();
		additiveProductionOrder.populateAdditiveProductionOrderFromInput(input);

		this.persist(additiveProductionOrder);

		return additiveProductionOrder;
	}

	@Transactional
	@Override
	public void update(AdditiveProductionOrder additiveProductionOrder) {
		this.additiveProductionOrderDao.update(additiveProductionOrder);
	}

	@Transactional
	@Override
	public AdditiveProductionOrder updateAdditiveProductionOrderFromJson(Integer additiveId, Integer productionOrderId, AdditiveProductionOrderJson input, Boolean isAdmin) {

		log.info("Updating additiveProductionOrder with id: {}", additiveId, productionOrderId);

		for(AdditiveProductionOrder e: this.additiveProductionOrderDao.getAdditiveProductionOrders()) { // TODO fix comparison over ID
			if(e.getAdditive().getId().equals(input.getAdditive_id()) && e.getProductionOrder().getId().equals(input.getProduction_order_code_id())) {
				throw new ConflictException(2001, "AdditiveProductionOrder (additive id: " + input.getAdditive_id() + ", PO id: " + input.getProduction_order_code_id() + ") already registered.");
			}
		}
		AdditiveProductionOrder additiveProductionOrder = this.getAdditiveProductionOrder(additiveId, productionOrderId, isAdmin);
		additiveProductionOrder.populateAdditiveProductionOrderFromInput(input);

		this.update(additiveProductionOrder);

		return additiveProductionOrder;
	}

	@Transactional
	@Override
	public void deleteAdditiveProductionOrderById(Integer additiveId, Integer productionOrderId) {

		log.info("Deleting additiveProductionOrder: {}", additiveId, productionOrderId);
		AdditiveProductionOrder c = this.additiveProductionOrderDao.getAdditiveProductionOrder(additiveId, productionOrderId);
		
		if (c == null) {
			throw new NotFoundException(404, "AdditiveProductionOrder " + additiveId + ", " + productionOrderId + " not found");
		}
		
		Date now = new Date();
		ProductionOrder po = c.getProductionOrder(); 
		if(now.after(po.getDelivery_date()))
			throw new ConflictException(2101, "Cannot delete additiveProductionOrder: " + additiveId + ", " + productionOrderId + ", production order already delivered.");
		this.additiveProductionOrderDao.delete(c);
	}
}
