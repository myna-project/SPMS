package it.mynaproject.spms.service;

import it.mynaproject.spms.domain.CleaningPhase;
import it.mynaproject.spms.model.CleaningPhaseJson;

public interface CleaningPhaseService {

	public CleaningPhase getCleaningPhase(Integer id, Integer sid);
	public void persist(CleaningPhase cleaningPhase);
	public CleaningPhase createCleaningPhaseFromJson(Integer id, CleaningPhaseJson input);
	public void update(CleaningPhase cleaningPhase);
	public CleaningPhase updateCleaningPhaseFromJson(Integer id, Integer sid, CleaningPhaseJson input);
	public void deleteCleaningPhaseById(Integer id);
}