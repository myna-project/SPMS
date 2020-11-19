package it.mynaproject.gestprod.dao;

import java.util.List;

import it.mynaproject.gestprod.domain.RawMaterial;

public interface RawMaterialDao {

	public void persist(RawMaterial customer);
	public void update(RawMaterial customer);
	public void delete(RawMaterial customer);
	public RawMaterial getRawMaterial(Integer id);
	public List<RawMaterial> getRawMaterials();
}