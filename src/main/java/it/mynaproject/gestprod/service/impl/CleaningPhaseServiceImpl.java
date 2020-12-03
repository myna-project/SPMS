package it.mynaproject.gestprod.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.mynaproject.gestprod.service.ProductionOrderService;
import it.mynaproject.gestprod.service.CleaningPhaseService;

import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.gestprod.dao.CleaningPhaseDao;
import it.mynaproject.gestprod.service.UserService;
import it.mynaproject.gestprod.domain.CleaningPhase;
import it.mynaproject.gestprod.domain.User;
import it.mynaproject.gestprod.domain.ProductionOrder;
import it.mynaproject.gestprod.exception.ConflictException;
import it.mynaproject.gestprod.exception.NotFoundException;
import it.mynaproject.gestprod.model.CleaningPhaseJson;

public class CleaningPhaseServiceImpl implements CleaningPhaseService {
	
	final private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private CleaningPhaseDao cleaningPhaseDao;
	private UserService userService;
	private ProductionOrderService productionOrderService;

	@Transactional(readOnly = true)
	@Override
	public CleaningPhase getCleaningPhase(Integer id, Integer sid) {
		
		CleaningPhase cleaningPhase = null;
		ProductionOrder p = this.productionOrderService.getProductionOrder(id);
		
		if (p == null)
			throw new NotFoundException(404, "ProductionOrder " + id + " not found");
		
		for(CleaningPhase sf : this.productionOrderService.getProductionOrder(id).getCleaningPhaseList()) {
			if(sf.getId() == sid) {
				cleaningPhase = sf;
			}
		}
		if (cleaningPhase == null)
			throw new NotFoundException(404, "CleaningPhase " + sid + " not found");
		
		return cleaningPhase;
	}
	
	@Transactional
	@Override
	public void persist(CleaningPhase cleaningPhase) {
		this.cleaningPhaseDao.persist(cleaningPhase);
	}

	@Transactional
	@Override
	public CleaningPhase createCleaningPhaseFromJson(Integer id, CleaningPhaseJson input) {

		log.info("Creating new cleaningPhase: {}", input.toString());

		User u = this.userService.getUser(input.getUser_id(), "");
		ProductionOrder po = this.productionOrderService.getProductionOrder(id);
		CleaningPhase cleaningPhase = new CleaningPhase();
		cleaningPhase.populateCleaningPhaseFromInput(input, po, u);

		this.persist(cleaningPhase);

		return cleaningPhase;
	}
	

	@Transactional
	@Override
	public void update(CleaningPhase cleaningPhase) {
		this.cleaningPhaseDao.update(cleaningPhase);
	}
	
	@Transactional
	@Override
	public CleaningPhase updateCleaningPhaseFromJson(Integer id, Integer sid, CleaningPhaseJson input) {

		log.info("Updating cleaningPhase with id: {}", id);

		User u = this.userService.getUser(input.getUser_id(), "");
		ProductionOrder po = this.productionOrderService.getProductionOrder(input.getProduction_order_id());
		CleaningPhase cleaningPhase = this.getCleaningPhase(id, sid);
		cleaningPhase.populateCleaningPhaseFromInput(input, po, u);

		this.update(cleaningPhase);

		return cleaningPhase;
	}
	
	@Transactional
	@Override
	public void deleteCleaningPhaseById(Integer id) {

		log.info("Deleting cleaningPhase: {}", id);
		CleaningPhase c = this.cleaningPhaseDao.getCleaningPhase(id);
		
		if (c == null) {
			throw new NotFoundException(404, "CleaningPhase " + id + " not found");
		}
		
		this.cleaningPhaseDao.delete(c);
	}
}