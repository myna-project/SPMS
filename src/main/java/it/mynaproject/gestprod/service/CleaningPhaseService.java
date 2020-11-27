package it.mynaproject.gestprod.service;

import it.mynaproject.gestprod.domain.CleaningPhase;
import it.mynaproject.gestprod.model.CleaningPhaseJson;

public interface CleaningPhaseService {

	public CleaningPhase getCleaningPhase(Integer id, Integer sid, Boolean isAdmin);
	public void persist(CleaningPhase cleaningPhase);
	public CleaningPhase createCleaningPhaseFromJson(Integer id, CleaningPhaseJson input);
	public void update(CleaningPhase cleaningPhase);
	public CleaningPhase updateCleaningPhaseFromJson(Integer id, Integer sid, CleaningPhaseJson input, Boolean isAdmin);
	public void deleteCleaningPhaseById(Integer id);
}