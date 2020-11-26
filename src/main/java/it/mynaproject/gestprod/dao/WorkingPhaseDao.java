package it.mynaproject.gestprod.dao;

import java.util.List;

import it.mynaproject.gestprod.domain.WorkingPhase;

public interface WorkingPhaseDao {

	public void persist(WorkingPhase workingPhase);
	public void update(WorkingPhase workingPhase);
	public void delete(WorkingPhase workingPhase);
	public WorkingPhase getWorkingPhase(Integer id);
	public List<WorkingPhase> getWorkingPhases();
}