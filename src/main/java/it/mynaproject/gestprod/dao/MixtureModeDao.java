package it.mynaproject.gestprod.dao;

import java.util.List;

import it.mynaproject.gestprod.domain.MixtureMode;

public interface MixtureModeDao {

	public void persist(MixtureMode customer);
	public void update(MixtureMode customer);
	public void delete(MixtureMode customer);
	public MixtureMode getMixtureMode(Integer id);
	public List<MixtureMode> getMixtureModes();
}
