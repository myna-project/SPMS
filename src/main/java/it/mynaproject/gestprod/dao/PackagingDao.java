package it.mynaproject.gestprod.dao;

import java.util.List;

import org.springframework.lang.Nullable;

import it.mynaproject.gestprod.domain.Packaging;

public interface PackagingDao {

	public void persist(Packaging packaging);
	public void update(Packaging packaging);
	public void delete(Packaging packaging);
	public Packaging getPackaging(Integer id);
	public Packaging checkPackagingExists(String name, @Nullable Integer id);
	public List<Packaging> getPackagings();
}