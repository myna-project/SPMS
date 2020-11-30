package it.mynaproject.gestprod.service;

import it.mynaproject.gestprod.domain.SettingPhase;
import it.mynaproject.gestprod.model.SettingPhaseJson;

public interface SettingPhaseService {

	public SettingPhase getSettingPhase(Integer id, Integer sid);
	public void persist(SettingPhase settingPhase);
	public SettingPhase createSettingPhaseFromJson(Integer id, SettingPhaseJson input);
	public void update(SettingPhase settingPhase);
	public SettingPhase updateSettingPhaseFromJson(Integer id, Integer sid, SettingPhaseJson input);
	public void deleteSettingPhaseById(Integer id);
}
