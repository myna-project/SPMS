package it.mynaproject.gestprod.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.mynaproject.gestprod.service.ProductionOrderService;
import it.mynaproject.gestprod.service.SettingPhaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.gestprod.dao.SettingPhaseDao;
import it.mynaproject.gestprod.service.UserService;
import it.mynaproject.gestprod.domain.SettingPhase;
import it.mynaproject.gestprod.domain.MixtureMode;
import it.mynaproject.gestprod.service.MixtureModeService;
import it.mynaproject.gestprod.domain.User;
import it.mynaproject.gestprod.domain.ProductionOrder;
import it.mynaproject.gestprod.exception.NotFoundException;
import it.mynaproject.gestprod.model.SettingPhaseJson;

@Service
public class SettingPhaseServiceImpl implements SettingPhaseService {
	
	final private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SettingPhaseDao settingPhaseDao;
	@Autowired
	private UserService userService;
	@Autowired
	private ProductionOrderService productionOrderService;
	@Autowired
	private MixtureModeService mixtureModeService;

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
		
		SettingPhase settingPhase = new SettingPhase();
		org.springframework.security.core.userdetails.User user =
				(org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();    
		User u = this.userService.getUserByUsername(user.getUsername());
		ProductionOrder po = this.productionOrderService.getProductionOrder(id);
		settingPhase.populateSettingPhaseFromInput(input, po, u);
		if(input.getEffective_mixture_mode() != null) {
			MixtureMode m = this.mixtureModeService.getMixtureMode(input.getEffective_mixture_mode().getId());
			settingPhase.setEffective_mixture_mode(m);
		}

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

		ProductionOrder po = this.productionOrderService.getProductionOrder(id);
		List<SettingPhase> sflist = po.getSettingPhaseList();
		SettingPhase settingPhase = null; // alternative: can we look for the setting phase using DAO?
		if(sflist != null) {
			for(SettingPhase sf : sflist) {
				if(sf.getId() == sid) {
					settingPhase = sf;
				}
			}
		}
		if(settingPhase == null)
			throw new NotFoundException(404, "SettingPhase " + sid + " not found");
		
		org.springframework.security.core.userdetails.User user =
				(org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();    
		User u = this.userService.getUserByUsername(user.getUsername());
		ProductionOrder npo = this.productionOrderService.getProductionOrder(input.getProductionOrder().getId());
		settingPhase.populateSettingPhaseFromInput(input, npo, u);
		if(input.getEffective_mixture_mode() != null) {
			MixtureMode m = this.mixtureModeService.getMixtureMode(input.getEffective_mixture_mode().getId());
			settingPhase.setEffective_mixture_mode(m);
		}
		
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
