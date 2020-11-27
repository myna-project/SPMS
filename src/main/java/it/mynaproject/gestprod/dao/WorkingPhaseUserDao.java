package it.mynaproject.gestprod.dao;

import java.util.List;

import it.mynaproject.gestprod.domain.WorkingPhaseUser;

public interface WorkingPhaseUserDao {

	public void persist(WorkingPhaseUser workingPhaseUser);
	public void update(WorkingPhaseUser workingPhaseUser);
	public void delete(WorkingPhaseUser workingPhaseUser);
	public WorkingPhaseUser getWorkingPhaseUser(Integer id);
	public List<WorkingPhaseUser> getWorkingPhaseUsers();
}