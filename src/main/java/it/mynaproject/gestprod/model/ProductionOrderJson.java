package it.mynaproject.gestprod.model;

import java.util.List;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.mynaproject.gestprod.util.ClassSerializer;

@JsonInclude(Include.NON_EMPTY)
public class ProductionOrderJson {
	
	private int id;
	
	private String production_order_code;
	
	private String production_number_lot;
	
	private int raw_material_id;
	
	private float weight_raw_material;
	
	private int tons_raw_material;
	
	private float dry_residue;
	
	private float density_raw_material;
	
	private float expected_mixture_temperature;
	
	private int expected_mixture_mode_id;
	
	private float expected_quantity_finished_product;
	
	private Date delivery_date;
	
	private int packaging_id;
	
	private Date production_order_date;
	
	private Date ddt_date;
	
	private String ddt_number;
	
	private List<Integer> additivesId; // TODO weight additive
	
	public List<Integer> getAdditivesId() {
		return additivesId;
	}

	public void setAdditivesId(List<Integer> additivesId) {
		this.additivesId = additivesId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProduction_order_code() {
		return production_order_code;
	}

	public void setProduction_order_code(String production_order_code) {
		this.production_order_code = production_order_code;
	}

	public String getProduction_number_lot() {
		return production_number_lot;
	}

	public void setProduction_number_lot(String production_number_lot) {
		this.production_number_lot = production_number_lot;
	}

	public int getRaw_material_id() {
		return raw_material_id;
	}

	public void setRaw_material_id(int raw_material_id) {
		this.raw_material_id = raw_material_id;
	}

	public float getWeight_raw_material() {
		return weight_raw_material;
	}

	public void setWeight_raw_material(float weight_raw_material) {
		this.weight_raw_material = weight_raw_material;
	}

	public int getTons_raw_material() {
		return tons_raw_material;
	}

	public void setTons_raw_material(int tons_raw_material) {
		this.tons_raw_material = tons_raw_material;
	}

	public float getDry_residue() {
		return dry_residue;
	}

	public void setDry_residue(float dry_residue) {
		this.dry_residue = dry_residue;
	}

	public float getDensity_raw_material() {
		return density_raw_material;
	}

	public void setDensity_raw_material(float density_raw_material) {
		this.density_raw_material = density_raw_material;
	}

	public float getExpected_mixture_temperature() {
		return expected_mixture_temperature;
	}

	public void setExpected_mixture_temperature(float expected_mixture_temperature) {
		this.expected_mixture_temperature = expected_mixture_temperature;
	}

	public int getExpected_mixture_mode_id() {
		return expected_mixture_mode_id;
	}

	public void setExpected_mixture_mode_id(int expected_mixture_mode_id) {
		this.expected_mixture_mode_id = expected_mixture_mode_id;
	}

	public float getExpected_quantity_finished_product() {
		return expected_quantity_finished_product;
	}

	public void setExpected_quantity_finished_product(float expected_quantity_finished_product) {
		this.expected_quantity_finished_product = expected_quantity_finished_product;
	}

	public Date getDelivery_date() {
		return delivery_date;
	}

	public void setDelivery_date(Date delivery_date) {
		this.delivery_date = delivery_date;
	}

	public int getPackaging_id() {
		return packaging_id;
	}

	public void setPackaging_id(int packaging_id) {
		this.packaging_id = packaging_id;
	}

	public Date getProduction_order_date() {
		return production_order_date;
	}

	public void setProduction_order_date(Date production_order_date) {
		this.production_order_date = production_order_date;
	}

	public Date getDdt_date() {
		return ddt_date;
	}

	public void setDdt_date(Date ddt_date) {
		this.ddt_date = ddt_date;
	}

	public String getDdt_number() {
		return ddt_number;
	}

	public void setDdt_number(String ddt_number) {
		this.ddt_number = ddt_number;
	}
	
	public String toString() {
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		return serializer.toString();
	}
}
