package it.mynaproject.spms.dao;

import java.util.List;

import org.springframework.lang.Nullable;

import it.mynaproject.spms.domain.Additive;

public interface AdditiveDao {

	public void persist(Additive customer);
	public void update(Additive customer);
	public void delete(Additive customer);
	public Additive getAdditive(Integer id);
	public Additive checkAdditiveExists(String name, @Nullable Integer id);
	public List<Additive> getAdditives();
}
