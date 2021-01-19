package it.mynaproject.gestprod.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.mynaproject.gestprod.service.ProductionOrderService;
import it.mynaproject.gestprod.service.CleaningPhaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.gestprod.dao.CleaningPhaseDao;
import it.mynaproject.gestprod.service.UserService;
import it.mynaproject.gestprod.domain.CleaningPhase;
import it.mynaproject.gestprod.domain.User;
import it.mynaproject.gestprod.domain.ProductionOrder;
import it.mynaproject.gestprod.exception.ConflictException;
import it.mynaproject.gestprod.exception.NotFoundException;
import it.mynaproject.gestprod.model.CleaningPhaseJson;

@Service
public class CleaningPhaseServiceImpl implements CleaningPhaseService {
	
	final private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CleaningPhaseDao cleaningPhaseDao;
	@Autowired
	private UserService userService;
	@Autowired
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

		org.springframework.security.core.userdetails.User user =
				(org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();    
		User u = this.userService.getUserByUsername(user.getUsername());
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

		ProductionOrder po = this.productionOrderService.getProductionOrder(id);
		List<CleaningPhase> sflist = po.getCleaningPhaseList();
		CleaningPhase cleaningPhase = null; // alternative: can we look for the cleaning phase using DAO?
		if(sflist != null) {
			for(CleaningPhase sf : sflist) {
				if(sf.getId() == sid) {
					cleaningPhase = sf;
				}
			}
		}
		if(cleaningPhase == null)
			throw new NotFoundException(404, "CleaningPhase " + sid + " not found");
		
		org.springframework.security.core.userdetails.User user =
				(org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();    
		User u = this.userService.getUserByUsername(user.getUsername());
		ProductionOrder npo = this.productionOrderService.getProductionOrder(input.getProductionOrder().getId());
		cleaningPhase.populateCleaningPhaseFromInput(input, npo, u);
		
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