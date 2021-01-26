package it.mynaproject.spms.model;

import java.util.List;

import javax.validation.constraints.NotBlank;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.mynaproject.spms.util.ClassSerializer;

@JsonInclude(Include.NON_EMPTY)
public class ProductionOrderJson {
	
	private Integer id;
	
	private Boolean completed;
	
	private CustomerJson customer;
	
	@NotBlank
	private String production_order_code;
	
	private String production_number_lot;
	
	private RawMaterialJson raw_material;
	
	private Float weight_raw_material;
	
	private Integer tons_raw_material;
	
	private Float dry_residue;
	
	private Float density_raw_material;
	
	private Float expected_mixture_temperature;
	
	private MixtureModeJson expected_mixture_mode;
	
	private Float expected_quantity_finished_product;
	
	private Date delivery_date;
	
	private PackagingJson packaging;
	
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

	public CustomerJson getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerJson customer) {
		this.customer = customer;
	}
	
	public RawMaterialJson getRaw_material() {
		return raw_material;
	}

	public void setRaw_material(RawMaterialJson raw_material) {
		this.raw_material = raw_material;
	}

	public MixtureModeJson getExpected_mixture_mode() {
		return expected_mixture_mode;
	}

	public void setExpected_mixture_mode(MixtureModeJson expected_mixture_mode) {
		this.expected_mixture_mode = expected_mixture_mode;
	}

	public PackagingJson getPackaging() {
		return packaging;
	}

	public void setPackaging(PackagingJson packaging) {
		this.packaging = packaging;
	}
	
	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

}
