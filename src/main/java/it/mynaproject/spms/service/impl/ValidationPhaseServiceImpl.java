package it.mynaproject.spms.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.spms.dao.ValidationPhaseDao;
import it.mynaproject.spms.domain.ProductionOrder;
import it.mynaproject.spms.domain.ValidationPhase;
import it.mynaproject.spms.exception.NotFoundException;
import it.mynaproject.spms.model.ValidationPhaseJson;
import it.mynaproject.spms.service.ProductionOrderService;
import it.mynaproject.spms.service.UserService;
import it.mynaproject.spms.service.ValidationPhaseService;

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
		for (ValidationPhase sf : p.getValidationPhaseList()) {
			if (sf.getId() == sid) {
				validationPhase = sf;
				break;
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

		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		ProductionOrder po = this.productionOrderService.getProductionOrder(id);

		ValidationPhase validationPhase = new ValidationPhase();
		validationPhase.populateValidationPhaseFromInput(input, po, this.userService.getUserByUsername(user.getUsername()));

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

		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		ValidationPhase validationPhase = this.getValidationPhase(id, sid);
		validationPhase.populateValidationPhaseFromInput(input, this.productionOrderService.getProductionOrder(input.getProductionOrder().getId()), this.userService.getUserByUsername(user.getUsername()));

		this.update(validationPhase);

		return validationPhase;
	}

	@Transactional
	@Override
	public void deleteValidationPhaseById(Integer id, Integer sid) {

		log.info("Deleting validationPhase: {}", id);

		this.validationPhaseDao.delete(this.getValidationPhase(id, sid));
	}
}