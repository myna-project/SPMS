package it.mynaproject.gestprod.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.mynaproject.gestprod.service.ProductionOrderService;
import it.mynaproject.gestprod.service.WorkingPhaseService;

import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.gestprod.dao.WorkingPhaseDao;
import it.mynaproject.gestprod.service.UserService;
import it.mynaproject.gestprod.domain.WorkingPhase;
import it.mynaproject.gestprod.domain.ProductionOrder;
import it.mynaproject.gestprod.exception.ConflictException;
import it.mynaproject.gestprod.exception.NotFoundException;
import it.mynaproject.gestprod.model.WorkingPhaseJson;

public class WorkingPhaseServiceImpl implements WorkingPhaseService {
	
	final private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private WorkingPhaseDao workingPhaseDao;
	private UserService userService;
	private ProductionOrderService productionOrderService;

	@Transactional(readOnly = true)
	@Override
	public WorkingPhase getWorkingPhase(Integer id, Integer sid) {
		
		WorkingPhase workingPhase = null;
		ProductionOrder p = this.productionOrderService.getProductionOrder(id);
		
		if (p == null)
			throw new NotFoundException(404, "ProductionOrder " + id + " not found");
		
		for(WorkingPhase sf : this.productionOrderService.getProductionOrder(id).getWorkingPhaseList()) {
			if(sf.getId() == sid) {
				workingPhase = sf;
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

		if(this.workingPhaseDao.getWorkingPhase(input.getId()) != null) {
			throw new ConflictException(8001, "WorkingPhase already registered with id: " + input.getId());
			
		}
		
		ProductionOrder po = this.productionOrderService.getProductionOrder(id);
		WorkingPhase workingPhase = new WorkingPhase();
		workingPhase.populateWorkingPhaseFromInput(input, po);

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

		log.info("Updating workingPhase with id: {}", id);

		for(WorkingPhase e: this.workingPhaseDao.getWorkingPhases()) {
			if(e.getId().equals(input.getId())) {
				throw new ConflictException(8001, "WorkingPhase already registered with id: " + input.getId());
			}
		}
		ProductionOrder po = this.productionOrderService.getProductionOrder(input.getProduction_order_id());
		WorkingPhase workingPhase = this.getWorkingPhase(id, sid);
		workingPhase.populateWorkingPhaseFromInput(input, po);

		this.update(workingPhase);

		return workingPhase;
	}
	
	@Transactional
	@Override
	public void deleteWorkingPhaseById(Integer id) {

		log.info("Deleting workingPhase: {}", id);
		WorkingPhase c = this.workingPhaseDao.getWorkingPhase(id);
		
		if (c == null) {
			throw new NotFoundException(404, "WorkingPhase " + id + " not found");
		}
		
		this.workingPhaseDao.delete(c);
	}
}
