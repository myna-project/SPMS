package it.mynaproject.gestprod.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import it.mynaproject.gestprod.util.ClassSerializer;

@Entity
@Table(name = "production_orders")
public class ProductionOrder extends BaseDomain {

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
}
