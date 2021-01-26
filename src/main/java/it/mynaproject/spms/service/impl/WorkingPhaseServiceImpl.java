package it.mynaproject.spms.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.spms.dao.WorkingPhaseDao;
import it.mynaproject.spms.domain.ProductionOrder;
import it.mynaproject.spms.domain.User;
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
		if (p == null)
			throw new NotFoundException(404, "ProductionOrder " + id + " not found");

		for (WorkingPhase sf : this.productionOrderService.getProductionOrder(id).getWorkingPhaseList())
			if(sf.getId() == sid)
				workingPhase = sf;
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

		User u = this.userService.getUserByUsername(user.getUsername());

		ProductionOrder po = this.productionOrderService.getProductionOrder(id);

		WorkingPhase workingPhase = new WorkingPhase();
		workingPhase.populateWorkingPhaseFromInput(input, po, u);

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

		WorkingPhase settingPhase = null;

		ProductionOrder po = this.productionOrderService.getProductionOrder(id);

		List<WorkingPhase> sflist = po.getWorkingPhaseList();
		if (sflist != null)
			for (WorkingPhase sf : sflist)
				if (sf.getId() == sid)
					settingPhase = sf;
		if (settingPhase == null)
			throw new NotFoundException(404, "WorkingPhase " + sid + " not found");

		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();    

		User u = this.userService.getUserByUsername(user.getUsername());

		ProductionOrder npo = this.productionOrderService.getProductionOrder(input.getProductionOrder().getId());
		settingPhase.populateWorkingPhaseFromInput(input, npo, u);

		this.update(settingPhase);

		return settingPhase;
	}

	@Transactional
	@Override
	public void deleteWorkingPhaseById(Integer id) {

		log.info("Deleting workingPhase: {}", id);

		WorkingPhase c = this.workingPhaseDao.getWorkingPhase(id);
		if (c == null)
			throw new NotFoundException(404, "WorkingPhase " + id + " not found");

		this.workingPhaseDao.delete(c);
	}
}
