package it.mynaproject.gestprod.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.mynaproject.gestprod.service.WorkingPhaseMeasureService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.gestprod.dao.WorkingPhaseMeasureDao;
import it.mynaproject.gestprod.service.UserService;
import it.mynaproject.gestprod.service.WorkingPhaseService;
import it.mynaproject.gestprod.domain.WorkingPhaseMeasure;
import it.mynaproject.gestprod.domain.User;
import it.mynaproject.gestprod.domain.WorkingPhase;
import it.mynaproject.gestprod.exception.NotFoundException;
import it.mynaproject.gestprod.model.WorkingPhaseMeasureJson;

@Service
public class WorkingPhaseMeasureServiceImpl implements WorkingPhaseMeasureService {
	
	final private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WorkingPhaseMeasureDao workingPhaseMeasureDao;
	@Autowired
	private UserService userService;
	@Autowired
	private WorkingPhaseService workingPhaseService;

	@Transactional(readOnly = true)
	@Override
	public WorkingPhaseMeasure getWorkingPhaseMeasure(Integer id, Integer sid, Integer tid) {
		
		WorkingPhaseMeasure workingPhaseMeasure = null;
		WorkingPhase p = this.workingPhaseService.getWorkingPhase(id, sid);
		if (p == null)
			throw new NotFoundException(404, "WorkingPhase " + sid + " not found");
		
		for(WorkingPhaseMeasure sf : p.getWorkingPhaseMeasureList()) {
			if(sf.getId() == tid) {
				workingPhaseMeasure = sf;
			}
		}
		if (workingPhaseMeasure == null)
			throw new NotFoundException(404, "WorkingPhaseMeasure " + tid + " not found");
		
		return workingPhaseMeasure;
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<WorkingPhaseMeasure> getWorkingPhaseMeasures(Integer id, Integer sid) {
		
		List<WorkingPhaseMeasure> wpul = new ArrayList<>();
		WorkingPhase p = this.workingPhaseService.getWorkingPhase(id, sid);
		if (p == null)
			throw new NotFoundException(404, "WorkingPhase " + sid + " not found");
		
		for(WorkingPhaseMeasure sf : p.getWorkingPhaseMeasureList()) {
				wpul.add(sf);
		}
		
		return wpul;
	}
	
	@Transactional
	@Override
	public void persist(WorkingPhaseMeasure workingPhaseMeasure) {
		this.workingPhaseMeasureDao.persist(workingPhaseMeasure);
	}

	@Transactional
	@Override
	public WorkingPhaseMeasure createWorkingPhaseMeasureFromJson(Integer id, Integer sid, WorkingPhaseMeasureJson input) {

		log.info("Creating new workingPhaseMeasure: {}", input.toString());

		org.springframework.security.core.userdetails.User user =
				(org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();    
		User u = this.userService.getUser(user.getUsername());
		WorkingPhase w = this.workingPhaseService.getWorkingPhase(id, sid);
		WorkingPhaseMeasure workingPhaseMeasure = new WorkingPhaseMeasure();
		workingPhaseMeasure.populateWorkingPhaseMeasureFromInput(input, w, u);

		this.persist(workingPhaseMeasure);

		return workingPhaseMeasure;
	}
	

	@Transactional
	@Override
	public void update(WorkingPhaseMeasure workingPhaseMeasure) {
		this.workingPhaseMeasureDao.update(workingPhaseMeasure);
	}
	
	@Transactional
	@Override
	public WorkingPhaseMeasure updateWorkingPhaseMeasureFromJson(Integer id, Integer sid, Integer tid, WorkingPhaseMeasureJson input) {

		log.info("Updating workingPhaseMeasure with id: {}", id);

		org.springframework.security.core.userdetails.User user =
				(org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();    
		User u = this.userService.getUser(user.getUsername());
		WorkingPhase w = this.workingPhaseService.getWorkingPhase(id, sid);
		WorkingPhaseMeasure workingPhaseMeasure = new WorkingPhaseMeasure();
		workingPhaseMeasure.populateWorkingPhaseMeasureFromInput(input, w, u);

		this.update(workingPhaseMeasure);

		return workingPhaseMeasure;
	}
	
	@Transactional
	@Override
	public void deleteWorkingPhaseMeasureById(Integer id) {

		log.info("Deleting workingPhaseMeasure: {}", id);
		WorkingPhaseMeasure c = this.workingPhaseMeasureDao.getWorkingPhaseMeasure(id);
		
		if (c == null) {
			throw new NotFoundException(404, "WorkingPhaseMeasure " + id + " not found");
		}
		
		this.workingPhaseMeasureDao.delete(c);
	}
}