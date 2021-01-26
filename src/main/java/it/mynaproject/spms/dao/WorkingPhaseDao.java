package it.mynaproject.spms.dao;

import java.util.List;

import it.mynaproject.spms.domain.WorkingPhase;

public interface WorkingPhaseDao {

	public void persist(WorkingPhase workingPhase);
	public void update(WorkingPhase workingPhase);
	public void delete(WorkingPhase workingPhase);
	public WorkingPhase getWorkingPhase(Integer id);
	public List<WorkingPhase> getWorkingPhases();
}