package it.mynaproject.spms.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.spms.dao.WorkingPhaseDao;
import it.mynaproject.spms.domain.ProductionOrder;
import it.mynaproject.spms.domain.WorkingPhase;
import it.mynaproject.spms.exception.NotFoundException;
import it.mynaproject.spms.model.WorkingPhaseJson;
import it.mynaproject.spms.service.ProductionOrderService;
import it.mynaproject.spms.service.UserService;
import it.mynaproject.spms.service.WorkingPhaseService;

@Service
public class WorkingPhaseServiceImpl implements WorkingPhaseService {
	
	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WorkingPhaseDao workingPhaseDao;

	@Autowired
	private ProductionOrderService productionOrderService;

	@Autowired
	private UserService userService;

	@Transactional(readOnly = true)
	@Override
	public WorkingPhase getWorkingPhase(Integer id, Integer sid) {

		WorkingPhase workingPhase = null;

		ProductionOrder p = this.productionOrderService.getProductionOrder(id);
		for (WorkingPhase sf : p.getWorkingPhaseList()) {
			if (sf.getId() == sid) {
				workingPhase = sf;
				break;
			}
		}

		if (workingPhase == null)
			throw new NotFoundException(404, "WorkingPhase " + sid + " not found");

		return workingPhase;
	}

	@Transactional
	@Override
	public void persist(WorkingPhase workingPhase) {
		this.workingPhaseDao.persist(workingPhase);
	}

	@Transactional
	@Override
	public WorkingPhase createWorkingPhaseFromJson(Integer id, WorkingPhaseJson input) {

		log.info("Creating new workingPhase: {}", input.toString());

		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		ProductionOrder po = this.productionOrderService.getProductionOrder(id);

		WorkingPhase workingPhase = new WorkingPhase();
		workingPhase.populateWorkingPhaseFromInput(input, po, this.userService.getUserByUsername(user.getUsername()));

		this.persist(workingPhase);

		return workingPhase;
	}

	@Transactional
	@Override
	public void update(WorkingPhase workingPhase) {
		this.workingPhaseDao.update(workingPhase);
	}
	
	@Transactional
	@Override
	public WorkingPhase updateWorkingPhaseFromJson(Integer id, Integer sid, WorkingPhaseJson input) {

		log.info("Updating settingPhase with id: {}", id);

		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		WorkingPhase workingPhase = this.getWorkingPhase(id, sid);
		workingPhase.populateWorkingPhaseFromInput(input, this.productionOrderService.getProductionOrder(input.getProductionOrder().getId()), this.userService.getUserByUsername(user.getUsername()));

		this.update(workingPhase);

		return workingPhase;
	}

	@Transactional
	@Override
	public void deleteWorkingPhaseById(Integer id, Integer sid) {

		log.info("Deleting workingPhase: {}", id);

		this.workingPhaseDao.delete(this.getWorkingPhase(id, sid));
	}
}