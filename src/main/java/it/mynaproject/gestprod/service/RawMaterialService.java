package it.mynaproject.gestprod.service;

import java.util.List;

import it.mynaproject.gestprod.domain.RawMaterial;
import it.mynaproject.gestprod.model.RawMaterialJson;

public interface RawMaterialService {

	public RawMaterial getRawMaterial(Integer id);
	public List<RawMaterial> getRawMaterials();
	public void persist(RawMaterial customer);
	public RawMaterial createRawMaterialFromJson(RawMaterialJson input);
	public void update(RawMaterial customer);
	public RawMaterial updateRawMaterialFromJson(Integer id, RawMaterialJson input);
	public void deleteRawMaterialById(Integer id);
}