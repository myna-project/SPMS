package it.mynaproject.gestprod.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.mynaproject.gestprod.service.WorkingPhaseUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.gestprod.dao.WorkingPhaseUserDao;
import it.mynaproject.gestprod.service.UserService;
import it.mynaproject.gestprod.service.WorkingPhaseService;
import it.mynaproject.gestprod.domain.WorkingPhaseUser;
import it.mynaproject.gestprod.domain.User;
import it.mynaproject.gestprod.domain.WorkingPhase;
import it.mynaproject.gestprod.exception.NotFoundException;
import it.mynaproject.gestprod.model.WorkingPhaseUserJson;

@Service
public class WorkingPhaseUserServiceImpl implements WorkingPhaseUserService {
	
	final private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WorkingPhaseUserDao workingPhaseUserDao;
	@Autowired
	private UserService userService;
	@Autowired
	private WorkingPhaseService workingPhaseService;

	@Transactional(readOnly = true)
	@Override
	public WorkingPhaseUser getWorkingPhaseUser(Integer id, Integer sid, Integer tid) {
		
		WorkingPhaseUser workingPhaseUser = null;
		WorkingPhase p = this.workingPhaseService.getWorkingPhase(id, sid);
		if (p == null)
			throw new NotFoundException(404, "WorkingPhase " + sid + " not found");
		
		for(WorkingPhaseUser sf : p.getWorkingPhaseUserList()) {
			if(sf.getId() == tid) {
				workingPhaseUser = sf;
			}
		}
		if (workingPhaseUser == null)
			throw new NotFoundException(404, "WorkingPhaseUser " + tid + " not found");
		
		return workingPhaseUser;
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<WorkingPhaseUser> getWorkingPhaseUsers(Integer id, Integer sid) {
		
		List<WorkingPhaseUser> wpul = new ArrayList<>();
		WorkingPhase p = this.workingPhaseService.getWorkingPhase(id, sid);
		if (p == null)
			throw new NotFoundException(404, "WorkingPhase " + sid + " not found");
		
		for(WorkingPhaseUser sf : p.getWorkingPhaseUserList()) {
				wpul.add(sf);
		}
		
		return wpul;
	}
	
	@Transactional
	@Override
	public void persist(WorkingPhaseUser workingPhaseUser) {
		this.workingPhaseUserDao.persist(workingPhaseUser);
	}

	@Transactional
	@Override
	public WorkingPhaseUser createWorkingPhaseUserFromJson(Integer id, Integer sid, WorkingPhaseUserJson input) {

		log.info("Creating new workingPhaseUser: {}", input.toString());
		
		org.springframework.security.core.userdetails.User user =
				(org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();    
		User u = this.userService.getUser(user.getUsername());
		WorkingPhase w = this.workingPhaseService.getWorkingPhase(id, sid);
		WorkingPhaseUser workingPhaseUser = new WorkingPhaseUser();
		workingPhaseUser.populateWorkingPhaseUserFromInput(input, w, u);

		this.persist(workingPhaseUser);

		return workingPhaseUser;
	}
	

	@Transactional
	@Override
	public void update(WorkingPhaseUser workingPhaseUser) {
		this.workingPhaseUserDao.update(workingPhaseUser);
	}
	
	@Transactional
	@Override
	public WorkingPhaseUser updateWorkingPhaseUserFromJson(Integer id, Integer sid, Integer tid, WorkingPhaseUserJson input) {

		log.info("Updating workingPhaseUser with id: {}", id);

		org.springframework.security.core.userdetails.User user =
				(org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();    
		User u = this.userService.getUser(user.getUsername());
		WorkingPhase w = this.workingPhaseService.getWorkingPhase(id, sid);
		WorkingPhaseUser workingPhaseUser = new WorkingPhaseUser();
		workingPhaseUser.populateWorkingPhaseUserFromInput(input, w, u);

		this.update(workingPhaseUser);

		return workingPhaseUser;
	}
	
	@Transactional
	@Override
	public void deleteWorkingPhaseUserById(Integer id) {

		log.info("Deleting workingPhaseUser: {}", id);
		WorkingPhaseUser c = this.workingPhaseUserDao.getWorkingPhaseUser(id);
		
		if (c == null) {
			throw new NotFoundException(404, "WorkingPhaseUser " + id + " not found");
		}
		
		this.workingPhaseUserDao.delete(c);
	}
}
