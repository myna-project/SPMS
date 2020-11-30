package it.mynaproject.gestprod.service;

import java.util.List;

import it.mynaproject.gestprod.domain.MixtureMode;
import it.mynaproject.gestprod.model.MixtureModeJson;

public interface MixtureModeService {

	public MixtureMode getMixtureMode(Integer id);
	public List<MixtureMode> getMixtureModes();
	public void persist(MixtureMode mixtureMode);
	public MixtureMode createMixtureModeFromJson(MixtureModeJson input);
	public void update(MixtureMode mixtureMode);
	public MixtureMode updateMixtureModeFromJson(Integer id, MixtureModeJson input);
	public void deleteMixtureModeById(Integer id);
}
