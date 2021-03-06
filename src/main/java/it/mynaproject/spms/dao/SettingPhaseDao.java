package it.mynaproject.spms.dao;

import java.util.List;

import it.mynaproject.spms.domain.SettingPhase;

public interface SettingPhaseDao {

	public void persist(SettingPhase settingPhase);
	public void update(SettingPhase settingPhase);
	public void delete(SettingPhase settingPhase);
	public SettingPhase getSettingPhase(Integer id);
	public List<SettingPhase> getSettingPhases();
}
