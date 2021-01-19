package it.mynaproject.gestprod.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.mynaproject.gestprod.service.ProductionOrderService;
import it.mynaproject.gestprod.service.SystemPreparationPhaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.gestprod.dao.SystemPreparationPhaseDao;
import it.mynaproject.gestprod.service.UserService;
import it.mynaproject.gestprod.domain.SystemPreparationPhase;
import it.mynaproject.gestprod.domain.User;
import it.mynaproject.gestprod.domain.ProductionOrder;
import it.mynaproject.gestprod.exception.NotFoundException;
import it.mynaproject.gestprod.model.SystemPreparationPhaseJson;

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
		
		if (p == null)
			throw new NotFoundException(404, "ProductionOrder " + id + " not found");
		
		for(SystemPreparationPhase sf : this.productionOrderService.getProductionOrder(id).getSystemPreparationPhaseList()) {
			if(sf.getId() == sid) {
				systemPreparationPhase = sf;
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
		
		org.springframework.security.core.userdetails.User user =
				(org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();    
		User u = this.userService.getUserByUsername(user.getUsername());
		ProductionOrder po = this.productionOrderService.getProductionOrder(id);
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
	public SystemPreparationPhase updateSystemPreparationPhaseFromJson(Integer id, Integer sid, SystemPreparationPhaseJson input) {

		log.info("Updating systemPreparationPhase with id: {}", id);

		ProductionOrder po = this.productionOrderService.getProductionOrder(id);
		List<SystemPreparationPhase> sflist = po.getSystemPreparationPhaseList();
		SystemPreparationPhase systemPreparationPhase = null; // alternative: can we look for the systemPreparation phase using DAO?
		if(sflist != null) {
			for(SystemPreparationPhase sf : sflist) {
				if(sf.getId() == sid) {
					systemPreparationPhase = sf;
				}
			}
		}
		if(systemPreparationPhase == null)
			throw new NotFoundException(404, "SystemPreparationPhase " + sid + " not found");
		

		org.springframework.security.core.userdetails.User user =
				(org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();    
		User u = this.userService.getUserByUsername(user.getUsername());
		ProductionOrder npo = this.productionOrderService.getProductionOrder(input.getProductionOrder().getId());
		systemPreparationPhase.populateSystemPreparationPhaseFromInput(input, npo, u);
		
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
