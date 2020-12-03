package it.mynaproject.gestprod.dao;

import java.util.List;

import org.springframework.lang.Nullable;

import it.mynaproject.gestprod.domain.RawMaterial;

public interface RawMaterialDao {

	public void persist(RawMaterial customer);
	public void update(RawMaterial customer);
	public void delete(RawMaterial customer);
	public RawMaterial getRawMaterial(Integer id);
	public RawMaterial checkRawMaterialExists(String name, @Nullable Integer id);
	public List<RawMaterial> getRawMaterials();
}