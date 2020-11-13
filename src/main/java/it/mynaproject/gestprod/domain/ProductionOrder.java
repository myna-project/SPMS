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
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="customer_id", referencedColumnName="id")
	private Customer customer;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="raw_material_id", referencedColumnName="id")
	private RawMaterial rawMaterial;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="packaging", referencedColumnName="id")
	private Packaging packaging;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="expected_mixture_mode_id", referencedColumnName="id")
	private MixtureMode mixtureMode;
	
	@Column(nullable=false)
	private float weight_raw_material;
	
	@Column(nullable=false)
	private int tons_raw_material;
	
	@Column
	private float dry_residue;
	
	@Column
	private float density_raw_material;
	
	@Column
	private float expected_mixture_temperature;
	
	@Column
	private float expected_quantity_finished_product;
	
	@Column
	private Date delivery_date;
	
	@Column
	private Date production_order_date;
	
	@Column
	private Date ddt_date;
	
	@Column
	private String ddt_number;
	
	@OneToMany(mappedBy="productionOrder")
	private List<SettingPhase> settingPhaseList;
	
	@OneToMany(mappedBy="productionOrder")
	private List<SystemPreparationPhase> systemPreparationPhaseList;
	
	@OneToMany(mappedBy="productionOrder")
	private List<CleaningPhase> cleaningPhaseList;
	
	@OneToMany(mappedBy="productionOrder")
	private List<ValidationPhase> validationPhaseList;
	
	@OneToMany(mappedBy="productionOrder")
	private List<WorkingPhase> workingPhaseList;
	
	@OneToMany(mappedBy="productionOrder")
	private List<AdditiveProductionOrder> additiveProductionOrderList;
	
	public String toString() {
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		return serializer.toString();
	}
}
