package it.mynaproject.gestprod.service;

import java.util.List;

import it.mynaproject.gestprod.domain.Packaging;
import it.mynaproject.gestprod.model.PackagingJson;

public interface PackagingService {

	public Packaging getPackaging(Integer id, Boolean isAdmin);
	public List<Packaging> getPackagings(Boolean isAdmin);
	public void persist(Packaging packaging);
	public Packaging createPackagingFromJson(PackagingJson input);
	public void update(Packaging packaging);
	public Packaging updatePackagingFromJson(Integer id, PackagingJson input, Boolean isAdmin);
	public void deletePackagingById(Integer id);
}
