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
	public SettingPhase getSettingPhase(Integer id, Integer sid) {
		
		SettingPhase settingPhase = null;
		ProductionOrder p = this.productionOrderService.getProductionOrder(id);
		
		for(SettingPhase sf : p.getSettingPhaseList()) {
			if(sf.getId() == sid) {
				settingPhase = sf;
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
		
		User u = this.userService.getUser(input.getUser().getId(), "");
		ProductionOrder po = this.productionOrderService.getProductionOrder(input.getProductionOrder().getId());
		SettingPhase settingPhase = new SettingPhase();
		settingPhase.populateSettingPhaseFromInput(input, po, u);

		this.persist(settingPhase);

		return settingPhase;
	}
	

	@Transactional
	@Override
	public void update(SettingPhase settingPhase) {
		this.settingPhaseDao.update(settingPhase);
	}
	
	@Transactional
	@Override
	public SettingPhase updateSettingPhaseFromJson(Integer id, Integer sid, SettingPhaseJson input) {

		log.info("Updating settingPhase with id: {}", id);

		User u = this.userService.getUser(input.getUser().getId(), "");
		ProductionOrder po = this.productionOrderService.getProductionOrder(input.getProductionOrder().getId());
		SettingPhase settingPhase = this.getSettingPhase(id, sid);
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
