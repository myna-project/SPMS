package it.mynaproject.gestprod.service;

import java.util.List;

import it.mynaproject.gestprod.domain.Additive;
import it.mynaproject.gestprod.model.AdditiveJson;

public interface AdditiveService {

	public Additive getAdditive(Integer id, Boolean isAdmin);
	public List<Additive> getAdditives(Boolean isAdmin);
	public void persist(Additive additive);
	public Additive createAdditiveFromJson(AdditiveJson input);
	public void update(Additive additive);
	public Additive updateAdditiveFromJson(Integer id, AdditiveJson input, Boolean isAdmin);
	public void deleteAdditiveById(Integer id);
}
