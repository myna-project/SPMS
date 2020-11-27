package it.mynaproject.gestprod.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.mynaproject.gestprod.service.WorkingPhaseUserService;

import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.gestprod.dao.WorkingPhaseUserDao;
import it.mynaproject.gestprod.service.UserService;
import it.mynaproject.gestprod.service.WorkingPhaseService;
import it.mynaproject.gestprod.domain.WorkingPhaseUser;
import it.mynaproject.gestprod.domain.User;
import it.mynaproject.gestprod.domain.WorkingPhase;
import it.mynaproject.gestprod.exception.ConflictException;
import it.mynaproject.gestprod.exception.NotFoundException;
import it.mynaproject.gestprod.model.WorkingPhaseUserJson;

public class WorkingPhaseUserServiceImpl implements WorkingPhaseUserService {
	
	final private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private WorkingPhaseUserDao workingPhaseUserDao;
	private UserService userService;
	private WorkingPhaseService workingPhaseService;

	@Transactional(readOnly = true)
	@Override
	public WorkingPhaseUser getWorkingPhaseUser(Integer id, Integer sid, Integer tid, Boolean isAdmin) {
		
		WorkingPhaseUser workingPhaseUser = null;
		WorkingPhase p = this.workingPhaseService.getWorkingPhase(id, sid, isAdmin);
		if (p == null)
			throw new NotFoundException(404, "WorkingPhase " + sid + " not found");
		
		// TODO is this necessary? Each setting phase should be associated with a unique ProductionOrder
		for(WorkingPhaseUser sf : p.getWorkingPhaseUserList()) {
			// TODO change every data structure to map instead of list? 
//			if(sf.getId() == tid) { // TODO WorkingPhaseUser has composite ID
				workingPhaseUser = workingPhaseUserDao.getWorkingPhaseUser(sid); // TODO wouldn't sf be sufficient? how do I ensure object in db?
			//}
		}
		if (workingPhaseUser == null)
			throw new NotFoundException(404, "WorkingPhaseUser " + tid + " not found");
		
		return workingPhaseUser;
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<WorkingPhaseUser> getWorkingPhaseUsers(Integer id, Integer sid, Boolean isAdmin) {
		
		List<WorkingPhaseUser> wpul = null;
		WorkingPhase p = this.workingPhaseService.getWorkingPhase(id, sid, isAdmin);
		if (p == null)
			throw new NotFoundException(404, "WorkingPhase " + sid + " not found");
		
		for(WorkingPhaseUser sf : p.getWorkingPhaseUserList()) {
				wpul.add(workingPhaseUserDao.getWorkingPhaseUser(sid)); // TODO wouldn't sf be sufficient? how do I ensure object in db?
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

		if(this.workingPhaseUserDao.getWorkingPhaseUser(input.getId()) != null) {
			throw new ConflictException(8001, "WorkingPhaseUser already registered with id: " + input.getId());
			
		}
		Boolean isAdmin = false; // TODO fix
		User u = this.userService.getUser(input.getUser_id(), isAdmin, "");
		WorkingPhase w = this.workingPhaseService.getWorkingPhase(id, sid, isAdmin);
		WorkingPhaseUser workingPhaseUser = new WorkingPhaseUser();
		workingPhaseUser.populateWorkingPhaseUserFromInput(input, w, u);

		this.persist(workingPhaseUser); // TODO must the modified workingPhase be registered here or in DAO?

		return workingPhaseUser;
	}
	

	@Transactional
	@Override
	public void update(WorkingPhaseUser workingPhaseUser) {
		this.workingPhaseUserDao.update(workingPhaseUser);
	}
	
	@Transactional
	@Override
	public WorkingPhaseUser updateWorkingPhaseUserFromJson(Integer id, Integer sid, Integer tid, WorkingPhaseUserJson input, Boolean isAdmin) {

		log.info("Updating workingPhaseUser with id: {}", id);

		// TODO non sarebbe meglio avere un accesso lineare ai workingPhaseUser gia` presenti?
		for(WorkingPhaseUser e: this.workingPhaseUserDao.getWorkingPhaseUsers()) {
//			if(e.getId().equals(input.getId())) { // TODO vedi TODO in getWorkingPhaseUser
				throw new ConflictException(8001, "WorkingPhaseUser already registered with id: " + input.getId());
//			}
		}
		User u = this.userService.getUser(input.getUser_id(), isAdmin, "");
		WorkingPhase w = this.workingPhaseService.getWorkingPhase(id, sid, isAdmin);
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
