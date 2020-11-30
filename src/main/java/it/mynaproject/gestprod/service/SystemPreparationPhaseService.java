package it.mynaproject.gestprod.service;

import it.mynaproject.gestprod.domain.SystemPreparationPhase;
import it.mynaproject.gestprod.model.SystemPreparationPhaseJson;

public interface SystemPreparationPhaseService {

	public SystemPreparationPhase getSystemPreparationPhase(Integer id, Integer sid);
	public void persist(SystemPreparationPhase systemPreparationPhase);
	public SystemPreparationPhase createSystemPreparationPhaseFromJson(Integer id, SystemPreparationPhaseJson input);
	public void update(SystemPreparationPhase systemPreparationPhase);
	public SystemPreparationPhase updateSystemPreparationPhaseFromJson(Integer id, Integer sid, SystemPreparationPhaseJson input);
	public void deleteSystemPreparationPhaseById(Integer id);
}