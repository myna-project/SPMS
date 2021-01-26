package it.mynaproject.spms.service;

import java.util.List;

import it.mynaproject.spms.domain.Additive;
import it.mynaproject.spms.model.AdditiveJson;

public interface AdditiveService {

	public Additive getAdditive(Integer id);
	public List<Additive> getAdditives();
	public void persist(Additive additive);
	public Additive createAdditiveFromJson(AdditiveJson input);
	public void update(Additive additive);
	public Additive updateAdditiveFromJson(Integer id, AdditiveJson input);
	public void deleteAdditiveById(Integer id);
}
