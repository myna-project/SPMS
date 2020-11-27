package it.mynaproject.gestprod.service;

import it.mynaproject.gestprod.domain.WorkingPhase;
import it.mynaproject.gestprod.model.WorkingPhaseJson;

public interface WorkingPhaseService {

	public WorkingPhase getWorkingPhase(Integer id, Integer sid, Boolean isAdmin);
	public void persist(WorkingPhase workingPhase);
	public WorkingPhase createWorkingPhaseFromJson(Integer id, WorkingPhaseJson input);
	public void update(WorkingPhase workingPhase);
	public WorkingPhase updateWorkingPhaseFromJson(Integer id, Integer sid, WorkingPhaseJson input, Boolean isAdmin);
	public void deleteWorkingPhaseById(Integer id);
}
