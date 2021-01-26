package it.mynaproject.spms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.spms.dao.AdditiveProductionOrderDao;
import it.mynaproject.spms.domain.Additive;
import it.mynaproject.spms.domain.AdditiveProductionOrder;
import it.mynaproject.spms.domain.ProductionOrder;
import it.mynaproject.spms.exception.NotFoundException;
import it.mynaproject.spms.model.AdditiveProductionOrderJson;
import it.mynaproject.spms.service.AdditiveProductionOrderService;
import it.mynaproject.spms.service.AdditiveService;
import it.mynaproject.spms.service.ProductionOrderService;

@Service
public class AdditiveProductionOrderServiceImpl implements AdditiveProductionOrderService {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AdditiveProductionOrderDao additiveProductionOrderDao;
	
	@Autowired
	private ProductionOrderService productionOrderService;
	
	@Autowired
	private AdditiveService additiveService;

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
	public AdditiveProductionOrder createAdditiveProductionOrderFromJson(Integer pid, AdditiveProductionOrderJson input) {

		log.info("Creating new additiveProductionOrder: {}", input.toString());

		ProductionOrder po = this.productionOrderService.getProductionOrder(pid);
		Additive add = this.additiveService.getAdditive(input.getAdditive().getId());
		AdditiveProductionOrder additiveProductionOrder = new AdditiveProductionOrder();
		additiveProductionOrder.populateAdditiveProductionOrderFromInput(input, po, add);

		this.persist(additiveProductionOrder);

		return additiveProductionOrder;
	}

	@Transactional
	@Override
	public void update(AdditiveProductionOrder additiveProductionOrder) {
		this.additiveProductionOrderDao.update(additiveProductionOrder);
	}

	// TODO may not be necessary
//	@Transactional
//	@Override
//	public AdditiveProductionOrder updateAdditiveProductionOrderFromJson(Integer pid, Integer id, AdditiveProductionOrderJson input) {
//
//		log.info("Updating additiveProductionOrder with id: {}", id);
//
//		ProductionOrder po = this.productionOrderService.getProductionOrder(pid);
//		Additive add = this.additiveService.getAdditive(input.getAdditive().getId());
//		AdditiveProductionOrder additiveProductionOrder = this.getAdditiveProductionOrder(id);
//		additiveProductionOrder.populateAdditiveProductionOrderFromInput(input, po, add);
//
//		this.update(additiveProductionOrder);
//
//		return additiveProductionOrder;
//	}

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
