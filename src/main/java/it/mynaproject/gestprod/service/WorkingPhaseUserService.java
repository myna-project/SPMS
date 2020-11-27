package it.mynaproject.gestprod.service;

import java.util.List;

import it.mynaproject.gestprod.domain.WorkingPhaseUser;
import it.mynaproject.gestprod.model.WorkingPhaseUserJson;

public interface WorkingPhaseUserService {

	public WorkingPhaseUser getWorkingPhaseUser(Integer id, Integer sid, Integer tid, Boolean isAdmin);
	public List<WorkingPhaseUser> getWorkingPhaseUsers(Integer id, Integer sid, Boolean isAdmin);
	public void persist(WorkingPhaseUser workingPhaseUser);
	public WorkingPhaseUser createWorkingPhaseUserFromJson(Integer id, Integer sid, WorkingPhaseUserJson input);
	public void update(WorkingPhaseUser workingPhaseUser);
	public WorkingPhaseUser updateWorkingPhaseUserFromJson(Integer id, Integer sid, Integer tid, WorkingPhaseUserJson input, Boolean isAdmin);
	public void deleteWorkingPhaseUserById(Integer id);
}
