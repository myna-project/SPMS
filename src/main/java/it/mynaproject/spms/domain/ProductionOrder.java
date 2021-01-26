package it.mynaproject.spms.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import it.mynaproject.spms.model.ProductionOrderJson;
import it.mynaproject.spms.util.ClassSerializer;

@Entity
@Table(name = "production_orders")
public class ProductionOrder extends BaseDomain {
	
	@Column(nullable=false)
	private Boolean completed;

	@Column(nullable=false, unique=true)
	private String production_order_code;
	
	@Column(nullable=false)
	private String production_number_lot;
	
	@ManyToOne
	@JoinColumn(name="customer_id", referencedColumnName="id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name="raw_material_id", referencedColumnName="id")
	private RawMaterial rawMaterial;
	
	@ManyToOne
	@JoinColumn(name="packaging_id", referencedColumnName="id")
	private Packaging packaging;
	
	@ManyToOne
	@JoinColumn(name="expected_mixture_mode_id", referencedColumnName="id")
	private MixtureMode mixtureMode;
	
	@Column(nullable=false)
	private Float weight_raw_material;
	
	@Column(nullable=false)
	private Integer tons_raw_material;
	
	@Column
	private Float dry_residue;
	
	@Column
	private Float density_raw_material;
	
	@Column
	private Float expected_mixture_temperature;
	
	@Column
	private Float expected_quantity_finished_product;
	
	@Column(columnDefinition = "DATE")
	private Date delivery_date;
	
	@Column(columnDefinition = "DATE")
	private Date production_order_date;
	
	@Column(columnDefinition = "DATE")
	private Date ddt_date;
	
	@Column
	private String ddt_number;
	
	@OneToMany(mappedBy="productionOrder", cascade = CascadeType.ALL)
	private List<SettingPhase> settingPhaseList;
	
	@OneToMany(mappedBy="productionOrder", cascade = CascadeType.ALL)
	private List<SystemPreparationPhase> systemPreparationPhaseList;
	
	@OneToMany(mappedBy="productionOrder", cascade = CascadeType.ALL)
	private List<CleaningPhase> cleaningPhaseList;
	
	@OneToMany(mappedBy="productionOrder", cascade = CascadeType.ALL)
	private List<ValidationPhase> validationPhaseList;
	
	@OneToMany(mappedBy="productionOrder", cascade = CascadeType.ALL)
	private List<WorkingPhase> workingPhaseList;
	
	@OneToMany(mappedBy="productionOrder", cascade = CascadeType.ALL)
	private List<AdditiveProductionOrder> additiveProductionOrderList;
	
	public String toString() {
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		return serializer.toString();
	}
	
	public void populateProductionOrderFromInput(final ProductionOrderJson input, final Customer customer, final MixtureMode mixtureMode, final Packaging packaging, final RawMaterial rawMaterial) {
		this.setCompleted(input.getCompleted());
		this.setDdt_date(input.getDdt_date());
		this.setDdt_number(input.getDdt_number());
		this.setDelivery_date(input.getDelivery_date());
		this.setDensity_raw_material(input.getDensity_raw_material());
		this.setDry_residue(input.getDry_residue());
		this.setExpected_mixture_temperature(input.getExpected_mixture_temperature());
		this.setExpected_quantity_finished_product(input.getExpected_quantity_finished_product());
		this.setProduction_number_lot(input.getProduction_number_lot());
		this.setProduction_order_code(input.getProduction_order_code());
		this.setProduction_order_date(input.getProduction_order_date());
		this.setTons_raw_material(input.getTons_raw_material());
		this.setWeight_raw_material(input.getWeight_raw_material());
		
		// many-to-one relationships
		this.setCustomer(customer);
		this.setMixtureMode(mixtureMode);
		this.setPackaging(packaging);
		this.setRawMaterial(rawMaterial);
	}
	
/** GETTERS and SETTERS **/
	
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public RawMaterial getRawMaterial() {
		return rawMaterial;
	}

	public void setRawMaterial(RawMaterial rawMaterial) {
		this.rawMaterial = rawMaterial;
	}

	public Packaging getPackaging() {
		return packaging;
	}

	public void setPackaging(Packaging packaging) {
		this.packaging = packaging;
	}

	public MixtureMode getMixtureMode() {
		return mixtureMode;
	}

	public void setMixtureMode(MixtureMode mixtureMode) {
		this.mixtureMode = mixtureMode;
	}

	public Float getWeight_raw_material() {
		return weight_raw_material;
	}

	public void setWeight_raw_material(Float weight_raw_material) {
		this.weight_raw_material = weight_raw_material;
	}

	public Integer getTons_raw_material() {
		return tons_raw_material;
	}

	public void setTons_raw_material(Integer tons_raw_material) {
		this.tons_raw_material = tons_raw_material;
	}

	public Float getDry_residue() {
		return dry_residue;
	}

	public void setDry_residue(Float dry_residue) {
		this.dry_residue = dry_residue;
	}

	public Float getDensity_raw_material() {
		return density_raw_material;
	}

	public void setDensity_raw_material(Float density_raw_material) {
		this.density_raw_material = density_raw_material;
	}

	public Float getExpected_mixture_temperature() {
		return expected_mixture_temperature;
	}

	public void setExpected_mixture_temperature(Float expected_mixture_temperature) {
		this.expected_mixture_temperature = expected_mixture_temperature;
	}

	public Float getExpected_quantity_finished_product() {
		return expected_quantity_finished_product;
	}

	public void setExpected_quantity_finished_product(Float expected_quantity_finished_product) {
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

	public List<SettingPhase> getSettingPhaseList() {
		return settingPhaseList;
	}

	public void setSettingPhaseList(List<SettingPhase> settingPhaseList) {
		this.settingPhaseList = settingPhaseList;
	}

	public List<SystemPreparationPhase> getSystemPreparationPhaseList() {
		return systemPreparationPhaseList;
	}

	public void setSystemPreparationPhaseList(List<SystemPreparationPhase> systemPreparationPhaseList) {
		this.systemPreparationPhaseList = systemPreparationPhaseList;
	}

	public List<CleaningPhase> getCleaningPhaseList() {
		return cleaningPhaseList;
	}

	public void setCleaningPhaseList(List<CleaningPhase> cleaningPhaseList) {
		this.cleaningPhaseList = cleaningPhaseList;
	}

	public List<ValidationPhase> getValidationPhaseList() {
		return validationPhaseList;
	}

	public void setValidationPhaseList(List<ValidationPhase> validationPhaseList) {
		this.validationPhaseList = validationPhaseList;
	}

	public List<WorkingPhase> getWorkingPhaseList() {
		return workingPhaseList;
	}

	public void setWorkingPhaseList(List<WorkingPhase> workingPhaseList) {
		this.workingPhaseList = workingPhaseList;
	}

	public List<AdditiveProductionOrder> getAdditiveProductionOrderList() {
		return additiveProductionOrderList;
	}

	public void setAdditiveProductionOrderList(List<AdditiveProductionOrder> additiveProductionOrderList) {
		this.additiveProductionOrderList = additiveProductionOrderList;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
}
