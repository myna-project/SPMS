package it.mynaproject.gestprod.dao;

import java.util.List;

import it.mynaproject.gestprod.domain.SettingPhase;

public interface SettingPhaseDao {

	public void persist(SettingPhase settingPhase);
	public void update(SettingPhase settingPhase);
	public void delete(SettingPhase settingPhase);
	public SettingPhase getSettingPhase(Integer id);
	public List<SettingPhase> getSettingPhases();
}
