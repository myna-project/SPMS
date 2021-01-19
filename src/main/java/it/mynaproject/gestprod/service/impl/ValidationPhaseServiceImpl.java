package it.mynaproject.gestprod.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.mynaproject.gestprod.service.ProductionOrderService;
import it.mynaproject.gestprod.service.ValidationPhaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.gestprod.dao.ValidationPhaseDao;
import it.mynaproject.gestprod.service.UserService;
import it.mynaproject.gestprod.domain.ValidationPhase;
import it.mynaproject.gestprod.domain.User;
import it.mynaproject.gestprod.domain.ProductionOrder;
import it.mynaproject.gestprod.exception.ConflictException;
import it.mynaproject.gestprod.exception.NotFoundException;
import it.mynaproject.gestprod.model.ValidationPhaseJson;

@Service
public class ValidationPhaseServiceImpl implements ValidationPhaseService {
	
	final private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ValidationPhaseDao validationPhaseDao;
	@Autowired
	private UserService userService;
	@Autowired
	private ProductionOrderService productionOrderService;

	@Transactional(readOnly = true)
	@Override
	public ValidationPhase getValidationPhase(Integer id, Integer sid) {
		
		ValidationPhase validationPhase = null;
		ProductionOrder p = this.productionOrderService.getProductionOrder(id);
		
		if (p == null)
			throw new NotFoundException(404, "ProductionOrder " + id + " not found");
		
		for(ValidationPhase sf : this.productionOrderService.getProductionOrder(id).getValidationPhaseList()) {
			if(sf.getId() == sid) {
				validationPhase = sf;
			}
		}
		if (validationPhase == null)
			throw new NotFoundException(404, "ValidationPhase " + sid + " not found");
		
		return validationPhase;
	}
	
	@Transactional
	@Override
	public void persist(ValidationPhase validationPhase) {
		this.validationPhaseDao.persist(validationPhase);
	}

	@Transactional
	@Override
	public ValidationPhase createValidationPhaseFromJson(Integer id, ValidationPhaseJson input) {

		log.info("Creating new validationPhase: {}", input.toString());

		org.springframework.security.core.userdetails.User user =
				(org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();    
		User u = this.userService.getUserByUsername(user.getUsername());
		ProductionOrder po = this.productionOrderService.getProductionOrder(id);
		ValidationPhase validationPhase = new ValidationPhase();
		validationPhase.populateValidationPhaseFromInput(input, po, u);

		this.persist(validationPhase);

		return validationPhase;
	}
	

	@Transactional
	@Override
	public void update(ValidationPhase validationPhase) {
		this.validationPhaseDao.update(validationPhase);
	}
	
	@Transactional
	@Override
	public ValidationPhase updateValidationPhaseFromJson(Integer id, Integer sid, ValidationPhaseJson input) {

		log.info("Updating validationPhase with id: {}", id);

		ProductionOrder po = this.productionOrderService.getProductionOrder(id);
		List<ValidationPhase> sflist = po.getValidationPhaseList();
		ValidationPhase validationPhase = null; // alternative: can we look for the validation phase using DAO?
		if(sflist != null) {
			for(ValidationPhase sf : sflist) {
				if(sf.getId() == sid) {
					validationPhase = sf;
				}
			}
		}
		if(validationPhase == null)
			throw new NotFoundException(404, "ValidationPhase " + sid + " not found");
		
		org.springframework.security.core.userdetails.User user =
				(org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();    
		User u = this.userService.getUserByUsername(user.getUsername());
		ProductionOrder npo = this.productionOrderService.getProductionOrder(input.getProductionOrder().getId());
		validationPhase.populateValidationPhaseFromInput(input, npo, u);
		
		this.update(validationPhase);

		return validationPhase;
	}
	
	@Transactional
	@Override
	public void deleteValidationPhaseById(Integer id) {

		log.info("Deleting validationPhase: {}", id);
		ValidationPhase c = this.validationPhaseDao.getValidationPhase(id);
		
		if (c == null) {
			throw new NotFoundException(404, "ValidationPhase " + id + " not found");
		}
		
		this.validationPhaseDao.delete(c);
	}
}