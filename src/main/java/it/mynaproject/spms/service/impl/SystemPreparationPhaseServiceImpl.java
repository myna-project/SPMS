package it.mynaproject.spms.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.spms.dao.SystemPreparationPhaseDao;
import it.mynaproject.spms.domain.ProductionOrder;
import it.mynaproject.spms.domain.SystemPreparationPhase;
import it.mynaproject.spms.exception.NotFoundException;
import it.mynaproject.spms.model.SystemPreparationPhaseJson;
import it.mynaproject.spms.service.ProductionOrderService;
import it.mynaproject.spms.service.SystemPreparationPhaseService;
import it.mynaproject.spms.service.UserService;

@Service
public class SystemPreparationPhaseServiceImpl implements SystemPreparationPhaseService {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SystemPreparationPhaseDao systemPreparationPhaseDao;

	@Autowired
	private UserService userService;

	@Autowired
	private ProductionOrderService productionOrderService;

	@Transactional(readOnly = true)
	@Override
	public SystemPreparationPhase getSystemPreparationPhase(Integer id, Integer sid) {

		SystemPreparationPhase systemPreparationPhase = null;

		ProductionOrder p = this.productionOrderService.getProductionOrder(id);
		for (SystemPreparationPhase sf : p.getSystemPreparationPhaseList()) {
			if (sf.getId() == sid) {
				systemPreparationPhase = sf;
				break;
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
	public SystemPreparationPhase createSystemPreparationPhaseFromJson(Integer id, SystemPreparationPhaseJson input) {

		log.info("Creating new systemPreparationPhase: {}", input.toString());

		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		ProductionOrder po = this.productionOrderService.getProductionOrder(id);

		SystemPreparationPhase systemPreparationPhase = new SystemPreparationPhase();
		systemPreparationPhase.populateSystemPreparationPhaseFromInput(input, po, this.userService.getUserByUsername(user.getUsername()));

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
	public SystemPreparationPhase updateSystemPreparationPhaseFromJson(Integer id, Integer sid, SystemPreparationPhaseJson input) {

		log.info("Updating systemPreparationPhase with id: {}", id);

		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		SystemPreparationPhase systemPreparationPhase = this.getSystemPreparationPhase(id, sid);
		systemPreparationPhase.populateSystemPreparationPhaseFromInput(input, this.productionOrderService.getProductionOrder(input.getProductionOrder().getId()), this.userService.getUserByUsername(user.getUsername()));

		this.update(systemPreparationPhase);

		return systemPreparationPhase;
	}
	
	@Transactional
	@Override
	public void deleteSystemPreparationPhaseById(Integer id, Integer sid) {

		log.info("Deleting systemPreparationPhase: {}", id);

		this.systemPreparationPhaseDao.delete(this.getSystemPreparationPhase(id, sid));
	}
}