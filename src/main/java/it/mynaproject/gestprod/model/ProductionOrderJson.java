package it.mynaproject.gestprod.model;

import java.util.List;

import javax.validation.constraints.NotBlank;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.mynaproject.gestprod.domain.AdditiveProductionOrder;
import it.mynaproject.gestprod.util.ClassSerializer;

@JsonInclude(Include.NON_EMPTY)
public class ProductionOrderJson {
	
	@NotBlank
	private Integer id;
	
	@NotBlank
	private Integer customer_id;
	
	@NotBlank
	private String production_order_code;
	
	private String production_number_lot;
	
	@NotBlank
	private Integer raw_material_id;
	
	private Float weight_raw_material;
	
	private Integer tons_raw_material;
	
	private Float dry_residue;
	
	private Float density_raw_material;
	
	private Float expected_mixture_temperature;
	
	@NotBlank
	private Integer expected_mixture_mode_id;
	
	private Float expected_quantity_finished_product;
	
	private Date delivery_date;
	
	@NotBlank
	private Integer packaging_id;
	
	private Date production_order_date;
	
	private Date ddt_date;
	
	private String ddt_number;
	
	private List<SettingPhaseJson> setting_phases;
	
	private List<SystemPreparationPhaseJson> system_preparation_phases;
	
	private List<CleaningPhaseJson> cleaning_phases;
	
	private List<WorkingPhaseJson> working_phases;
	
	private List<ValidationPhaseJson> validation_phases;
	
	private List<AdditiveProductionOrderJson> additives;
	
	public String toString() {
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		return serializer.toString();
	}
	
/** GETTERS and SETTERS **/
	
	public List<AdditiveProductionOrderJson> getAdditives() {
		return additives;
	}

	public void setAdditives(List<AdditiveProductionOrderJson> additives) {
		this.additives = additives;
	}

	
	public Integer getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
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

	public Integer getRaw_material_id() {
		return raw_material_id;
	}

	public void setRaw_material_id(final Integer raw_material_id) {
		this.raw_material_id = raw_material_id;
	}

	public Float getWeight_raw_material() {
		return weight_raw_material;
	}

	public void setWeight_raw_material(final Float weight_raw_material) {
		this.weight_raw_material = weight_raw_material;
	}

	public Integer getTons_raw_material() {
		return tons_raw_material;
	}

	public void setTons_raw_material(final Integer tons_raw_material) {
		this.tons_raw_material = tons_raw_material;
	}

	public Float getDry_residue() {
		return dry_residue;
	}

	public void setDry_residue(final Float dry_residue) {
		this.dry_residue = dry_residue;
	}

	public Float getDensity_raw_material() {
		return density_raw_material;
	}

	public void setDensity_raw_material(final Float density_raw_material) {
		this.density_raw_material = density_raw_material;
	}

	public Float getExpected_mixture_temperature() {
		return expected_mixture_temperature;
	}

	public void setExpected_mixture_temperature(final Float expected_mixture_temperature) {
		this.expected_mixture_temperature = expected_mixture_temperature;
	}

	public Integer getExpected_mixture_mode_id() {
		return expected_mixture_mode_id;
	}

	public void setExpected_mixture_mode_id(final Integer expected_mixture_mode_id) {
		this.expected_mixture_mode_id = expected_mixture_mode_id;
	}

	public Float getExpected_quantity_finished_product() {
		return expected_quantity_finished_product;
	}

	public void setExpected_quantity_finished_product(final Float expected_quantity_finished_product) {
		this.expected_quantity_finished_product = expected_quantity_finished_product;
	}

	public Date getDelivery_date() {
		return delivery_date;
	}

	public void setDelivery_date(Date delivery_date) {
		this.delivery_date = delivery_date;
	}

	public Integer getPackaging_id() {
		return packaging_id;
	}

	public void setPackaging_id(final Integer packaging_id) {
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

	public List<SettingPhaseJson> getSetting_phases() {
		return setting_phases;
	}

	public void setSetting_phases(List<SettingPhaseJson> setting_phases) {
		this.setting_phases = setting_phases;
	}

	public List<SystemPreparationPhaseJson> getSystem_preparation_phases() {
		return system_preparation_phases;
	}

	public void setSystem_preparation_phases(List<SystemPreparationPhaseJson> system_preparation_phases) {
		this.system_preparation_phases = system_preparation_phases;
	}

	public List<CleaningPhaseJson> getCleaning_phases() {
		return cleaning_phases;
	}

	public void setCleaning_phases(List<CleaningPhaseJson> cleaning_phases) {
		this.cleaning_phases = cleaning_phases;
	}

	public List<WorkingPhaseJson> getWorking_phases() {
		return working_phases;
	}

	public void setWorking_phases(List<WorkingPhaseJson> working_phases) {
		this.working_phases = working_phases;
	}

	public List<ValidationPhaseJson> getValidation_phases() {
		return validation_phases;
	}

	public void setValidation_phases(List<ValidationPhaseJson> validation_phases) {
		this.validation_phases = validation_phases;
	}
}
