package it.mynaproject.spms.dao;

import java.util.List;

import it.mynaproject.spms.domain.SystemPreparationPhase;

public interface SystemPreparationPhaseDao {

	public void persist(SystemPreparationPhase systemPreparationPhase);
	public void update(SystemPreparationPhase systemPreparationPhase);
	public void delete(SystemPreparationPhase systemPreparationPhase);
	public SystemPreparationPhase getSystemPreparationPhase(Integer id);
	public List<SystemPreparationPhase> getSystemPreparationPhases();
}