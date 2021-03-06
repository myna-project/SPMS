package it.mynaproject.spms.service;

import it.mynaproject.spms.domain.WorkingPhase;
import it.mynaproject.spms.model.WorkingPhaseJson;

public interface WorkingPhaseService {

	public WorkingPhase getWorkingPhase(Integer id, Integer sid);
	public void persist(WorkingPhase workingPhase);
	public WorkingPhase createWorkingPhaseFromJson(Integer id, WorkingPhaseJson input);
	public void update(WorkingPhase workingPhase);
	public WorkingPhase updateWorkingPhaseFromJson(Integer id, Integer sid, WorkingPhaseJson input);
	public void deleteWorkingPhaseById(Integer id, Integer sid);
}
