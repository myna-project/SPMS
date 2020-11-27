package it.mynaproject.gestprod.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.mynaproject.gestprod.service.ProductionOrderService;
import it.mynaproject.gestprod.service.SettingPhaseService;

import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.gestprod.dao.SettingPhaseDao;
import it.mynaproject.gestprod.service.UserService;
import it.mynaproject.gestprod.domain.SettingPhase;
import it.mynaproject.gestprod.domain.User;
import it.mynaproject.gestprod.domain.ProductionOrder;
import it.mynaproject.gestprod.exception.ConflictException;
import it.mynaproject.gestprod.exception.NotFoundException;
import it.mynaproject.gestprod.model.SettingPhaseJson;

public class SettingPhaseServiceImpl implements SettingPhaseService {
	
	final private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private SettingPhaseDao settingPhaseDao;
	private UserService userService;
	private ProductionOrderService productionOrderService;

	@Transactional(readOnly = true)
	@Override
	public SettingPhase getSettingPhase(Integer id, Integer sid, Boolean isAdmin) {
		
		SettingPhase settingPhase = null;
		ProductionOrder p = this.productionOrderService.getProductionOrder(id, isAdmin);
		
		if (p == null)
			throw new NotFoundException(404, "ProductionOrder " + id + " not found");
		
		// TODO is this necessary? Each setting phase should be associated with a unique ProductionOrder
		for(SettingPhase sf : this.productionOrderService.getProductionOrder(id, isAdmin).getSettingPhaseList()) {
			// TODO change every data structure to map instead of list? 
			if(sf.getId() == sid) {
				settingPhase = settingPhaseDao.getSettingPhase(sid); // TODO wouldn't sf be sufficient? how do I ensure object in db?
			}
		}
		if (settingPhase == null)
			throw new NotFoundException(404, "SettingPhase " + sid + " not found");
		
		return settingPhase;
	}
	
	@Transactional
	@Override
	public void persist(SettingPhase settingPhase) {
		this.settingPhaseDao.persist(settingPhase);
	}

	@Transactional
	@Override
	public SettingPhase createSettingPhaseFromJson(Integer id, SettingPhaseJson input) {

		log.info("Creating new settingPhase: {}", input.toString());

		if(this.settingPhaseDao.getSettingPhase(input.getId()) != null) {
			throw new ConflictException(8001, "SettingPhase already registered with id: " + input.getId());
			
		}
		Boolean isAdmin = false; // TODO fix
		User u = this.userService.getUser(input.getUser_id(), isAdmin, "");
		ProductionOrder po = this.productionOrderService.getProductionOrder(id, isAdmin);
		SettingPhase settingPhase = new SettingPhase();
		settingPhase.populateSettingPhaseFromInput(input, po, u);

		this.persist(settingPhase); // TODO must the modified productionOrder be registered here or in DAO?

		return settingPhase;
	}
	

	@Transactional
	@Override
	public void update(SettingPhase settingPhase) {
		this.settingPhaseDao.update(settingPhase);
	}
	
	@Transactional
	@Override
	public SettingPhase updateSettingPhaseFromJson(Integer id, Integer sid, SettingPhaseJson input, Boolean isAdmin) {

		log.info("Updating settingPhase with id: {}", id);

		// TODO non sarebbe meglio avere un accesso lineare ai settingPhase gia` presenti?
		for(SettingPhase e: this.settingPhaseDao.getSettingPhases()) {
			if(e.getId().equals(input.getId())) {
				throw new ConflictException(8001, "SettingPhase already registered with id: " + input.getId());
			}
		}
		User u = this.userService.getUser(input.getUser_id(), isAdmin, "");
		ProductionOrder po = this.productionOrderService.getProductionOrder(input.getProduction_order_id(), isAdmin);
		SettingPhase settingPhase = this.getSettingPhase(id, sid, isAdmin);
		settingPhase.populateSettingPhaseFromInput(input, po, u);

		this.update(settingPhase);

		return settingPhase;
	}
	
	@Transactional
	@Override
	public void deleteSettingPhaseById(Integer id) {

		log.info("Deleting settingPhase: {}", id);
		SettingPhase c = this.settingPhaseDao.getSettingPhase(id);
		
		if (c == null) {
			throw new NotFoundException(404, "SettingPhase " + id + " not found");
		}
		
		this.settingPhaseDao.delete(c);
	}
}
