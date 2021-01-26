package it.mynaproject.spms.service;

import java.util.List;

import it.mynaproject.spms.domain.Packaging;
import it.mynaproject.spms.model.PackagingJson;

public interface PackagingService {

	public Packaging getPackaging(Integer id);
	public List<Packaging> getPackagings();
	public void persist(Packaging packaging);
	public Packaging createPackagingFromJson(PackagingJson input);
	public void update(Packaging packaging);
	public Packaging updatePackagingFromJson(Integer id, PackagingJson input);
	public void deletePackagingById(Integer id);
}
