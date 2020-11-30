package it.mynaproject.gestprod.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.mynaproject.gestprod.service.ProductionOrderService;
import it.mynaproject.gestprod.service.ValidationPhaseService;

import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.gestprod.dao.ValidationPhaseDao;
import it.mynaproject.gestprod.service.UserService;
import it.mynaproject.gestprod.domain.ValidationPhase;
import it.mynaproject.gestprod.domain.User;
import it.mynaproject.gestprod.domain.ProductionOrder;
import it.mynaproject.gestprod.exception.ConflictException;
import it.mynaproject.gestprod.exception.NotFoundException;
import it.mynaproject.gestprod.model.ValidationPhaseJson;

public class ValidationPhaseServiceImpl implements ValidationPhaseService {
	
	final private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private ValidationPhaseDao validationPhaseDao;
	private UserService userService;
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

		if(this.validationPhaseDao.getValidationPhase(input.getId()) != null) {
			throw new ConflictException(8001, "ValidationPhase already registered with id: " + input.getId());
			
		}
		
		User u = this.userService.getUser(input.getUser_id(), "");
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

		for(ValidationPhase e: this.validationPhaseDao.getValidationPhases()) {
			if(e.getId().equals(input.getId())) {
				throw new ConflictException(8001, "ValidationPhase already registered with id: " + input.getId());
			}
		}
		User u = this.userService.getUser(input.getUser_id(), "");
		ProductionOrder po = this.productionOrderService.getProductionOrder(input.getProduction_order_id());
		ValidationPhase validationPhase = this.getValidationPhase(id, sid);
		validationPhase.populateValidationPhaseFromInput(input, po, u);

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