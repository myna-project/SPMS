package it.mynaproject.spms.dao;

import java.util.List;

import it.mynaproject.spms.domain.CleaningPhase;

public interface CleaningPhaseDao {

	public void persist(CleaningPhase cleaningPhase);
	public void update(CleaningPhase cleaningPhase);
	public void delete(CleaningPhase cleaningPhase);
	public CleaningPhase getCleaningPhase(Integer id);
	public List<CleaningPhase> getCleaningPhases();
}
