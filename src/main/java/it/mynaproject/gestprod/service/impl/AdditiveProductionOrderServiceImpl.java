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
public class AdditiveProductionOrderServiceImpl implements AdditiveProductionOrderService {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AdditiveProductionOrderDao additiveProductionOrderDao;

	@Transactional(readOnly = true)
	@Override
	public AdditiveProductionOrder getAdditiveProductionOrder(Integer id) {

		AdditiveProductionOrder p = this.additiveProductionOrderDao.getAdditiveProductionOrder(id);
		if (p == null)
			throw new NotFoundException(404, "AdditiveProductionOrder " + id + " not found");
		return p;
	}

	@Transactional(readOnly = true)
	@Override
	public List<AdditiveProductionOrder> getAdditiveProductionOrders() {

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
	public AdditiveProductionOrder updateAdditiveProductionOrderFromJson(Integer id, AdditiveProductionOrderJson input) {

		log.info("Updating additiveProductionOrder with id: {}", id);

		AdditiveProductionOrder additiveProductionOrder = this.getAdditiveProductionOrder(id);
		additiveProductionOrder.populateAdditiveProductionOrderFromInput(input);

		this.update(additiveProductionOrder);

		return additiveProductionOrder;
	}

	@Transactional
	@Override
	public void deleteAdditiveProductionOrderById(Integer id) {

		log.info("Deleting additiveProductionOrder: {}", id);
		AdditiveProductionOrder c = this.additiveProductionOrderDao.getAdditiveProductionOrder(id);
		
		if (c == null) {
			throw new NotFoundException(404, "AdditiveProductionOrder " + id + " not found");
		}
		
		this.additiveProductionOrderDao.delete(c);
	}
}
