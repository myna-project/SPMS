package it.mynaproject.gestprod.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.mynaproject.gestprod.service.ProductionOrderService;
import it.mynaproject.gestprod.service.SystemPreparationPhaseService;

import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.gestprod.dao.SystemPreparationPhaseDao;
import it.mynaproject.gestprod.service.UserService;
import it.mynaproject.gestprod.domain.SystemPreparationPhase;
import it.mynaproject.gestprod.domain.User;
import it.mynaproject.gestprod.domain.ProductionOrder;
import it.mynaproject.gestprod.exception.ConflictException;
import it.mynaproject.gestprod.exception.NotFoundException;
import it.mynaproject.gestprod.model.SystemPreparationPhaseJson;

public class SystemPreparationPhaseServiceImpl implements SystemPreparationPhaseService {
	
	final private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private SystemPreparationPhaseDao systemPreparationPhaseDao;
	private UserService userService;
	private ProductionOrderService productionOrderService;

	@Transactional(readOnly = true)
	@Override
	public SystemPreparationPhase getSystemPreparationPhase(Integer id, Integer sid, Boolean isAdmin) {
		
		SystemPreparationPhase systemPreparationPhase = null;
		ProductionOrder p = this.productionOrderService.getProductionOrder(id, isAdmin);
		
		if (p == null)
			throw new NotFoundException(404, "ProductionOrder " + id + " not found");
		
		// TODO is this necessary? Each systemPreparation phase should be associated with a unique ProductionOrder
		for(SystemPreparationPhase sf : this.productionOrderService.getProductionOrder(id, isAdmin).getSystemPreparationPhaseList()) {
			// TODO change every data structure to map instead of list? 
			if(sf.getId() == sid) {
				systemPreparationPhase = systemPreparationPhaseDao.getSystemPreparationPhase(sid); // TODO wouldn't sf be sufficient? how do I ensure object in db?
			}
		}
		if (systemPreparationPhase == null)
			throw new NotFoundException(404, "SystemPreparationPhase " + sid + " not found");
		
		return systemPreparationPhase;
	}
	
	@Transactional
	@Override
	public void persist(SystemPreparationPhase systemPreparationPhase) {
		this.systemPreparationPhaseDao.persist(systemPreparationPhase);
	}

	@Transactional
	@Override
	public SystemPreparationPhase createSystemPreparationPhaseFromJson(SystemPreparationPhaseJson input) {

		log.info("Creating new systemPreparationPhase: {}", input.toString());

		if(this.systemPreparationPhaseDao.getSystemPreparationPhase(input.getId()) != null) {
			throw new ConflictException(8001, "SystemPreparationPhase already registered with id: " + input.getId());
			
		}
		Boolean isAdmin = false; // TODO fix
		User u = this.userService.getUser(input.getUser_id(), isAdmin, "");
		ProductionOrder po = this.productionOrderService.getProductionOrder(input.getProduction_order_id(), isAdmin);
		SystemPreparationPhase systemPreparationPhase = new SystemPreparationPhase();
		systemPreparationPhase.populateSystemPreparationPhaseFromInput(input, po, u);

		this.persist(systemPreparationPhase);

		return systemPreparationPhase;
	}
	

	@Transactional
	@Override
	public void update(SystemPreparationPhase systemPreparationPhase) {
		this.systemPreparationPhaseDao.update(systemPreparationPhase);
	}
	
	@Transactional
	@Override
	public SystemPreparationPhase updateSystemPreparationPhaseFromJson(Integer id, Integer sid, SystemPreparationPhaseJson input, Boolean isAdmin) {

		log.info("Updating systemPreparationPhase with id: {}", id);

		// TODO non sarebbe meglio avere un accesso lineare ai systemPreparationPhase gia` presenti?
		for(SystemPreparationPhase e: this.systemPreparationPhaseDao.getSystemPreparationPhases()) {
			if(e.getId().equals(input.getId())) {
				throw new ConflictException(8001, "SystemPreparationPhase already registered with id: " + input.getId());
			}
		}
		User u = this.userService.getUser(input.getUser_id(), isAdmin, "");
		ProductionOrder po = this.productionOrderService.getProductionOrder(input.getProduction_order_id(), isAdmin);
		SystemPreparationPhase systemPreparationPhase = this.getSystemPreparationPhase(id, sid, isAdmin);
		systemPreparationPhase.populateSystemPreparationPhaseFromInput(input, po, u);

		this.update(systemPreparationPhase);

		return systemPreparationPhase;
	}
	
	@Transactional
	@Override
	public void deleteSystemPreparationPhaseById(Integer id) {

		log.info("Deleting systemPreparationPhase: {}", id);
		SystemPreparationPhase c = this.systemPreparationPhaseDao.getSystemPreparationPhase(id);
		
		if (c == null) {
			throw new NotFoundException(404, "SystemPreparationPhase " + id + " not found");
		}
		
		this.systemPreparationPhaseDao.delete(c);
	}
}