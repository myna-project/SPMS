package it.mynaproject.gestprod.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import it.mynaproject.gestprod.util.ClassSerializer;

@Entity
@Table(name = "production_orders")
public class ProductionOrder extends BaseDomain {

	@Column(nullable=false)
	private int customer_id;
	
	@Column(nullable=false, unique=true)
	private String production_order_code;
	
	@Column(nullable=false)
	private String production_number_lot;
	
	@Column(nullable=false)
	private int raw_material_id;
	
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
	private int expected_mixture_mode_id;
	
	@Column
	private float expected_quantity_finished_product;
	
	@Column
	private Date delivery_date;
	
	@Column
	private int packaging_id;
	
	@Column
	private Date production_order_date;
	
	@Column
	private Date ddt_date;
	
	@Column
	private String ddt_number;
	
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "additives_production_orders", joinColumns = { @JoinColumn(name = "production_order_code_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "additive_id", referencedColumnName = "id") })
	private List<Additive> additiveList;

	public String toString() {
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		return serializer.toString();
	}
}
