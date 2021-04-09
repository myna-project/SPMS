package it.mynaproject.spms.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.spms.dao.SettingPhaseDao;
import it.mynaproject.spms.domain.ProductionOrder;
import it.mynaproject.spms.domain.SettingPhase;
import it.mynaproject.spms.exception.NotFoundException;
import it.mynaproject.spms.model.SettingPhaseJson;
import it.mynaproject.spms.service.MixtureModeService;
import it.mynaproject.spms.service.ProductionOrderService;
import it.mynaproject.spms.service.SettingPhaseService;
import it.mynaproject.spms.service.UserService;

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
			if (sf.getId() == sid) {
				settingPhase = sf;
				break;
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

		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		ProductionOrder po = this.productionOrderService.getProductionOrder(id);

		SettingPhase settingPhase = new SettingPhase();
		settingPhase.populateSettingPhaseFromInput(input, po, this.userService.getUserByUsername(user.getUsername()));
		if (input.getEffective_mixture_mode() != null)
			settingPhase.setEffective_mixture_mode(this.mixtureModeService.getMixtureMode(input.getEffective_mixture_mode().getId()));

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

		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		SettingPhase settingPhase = this.getSettingPhase(id, sid);
		settingPhase.populateSettingPhaseFromInput(input, this.productionOrderService.getProductionOrder(input.getProductionOrder().getId()), this.userService.getUserByUsername(user.getUsername()));
		if (input.getEffective_mixture_mode() != null)
			settingPhase.setEffective_mixture_mode(this.mixtureModeService.getMixtureMode(input.getEffective_mixture_mode().getId()));

		this.update(settingPhase);

		return settingPhase;
	}

	@Transactional
	@Override
	public void deleteSettingPhaseById(Integer id, Integer sid) {

		log.info("Deleting settingPhase: {}", id);

		this.settingPhaseDao.delete(this.getSettingPhase(id, sid));
	}
}