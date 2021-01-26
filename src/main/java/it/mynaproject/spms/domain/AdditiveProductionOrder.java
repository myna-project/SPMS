package it.mynaproject.spms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import it.mynaproject.spms.model.AdditiveProductionOrderJson;
import it.mynaproject.spms.util.ClassSerializer;

@Entity
@Table(name = "additives_production_orders")
public class AdditiveProductionOrder extends BaseDomain {
	
	@ManyToOne
	@JoinColumn(name="additive_id", referencedColumnName="id")
	private Additive additive;
	
	@ManyToOne
	@JoinColumn(name="production_order_code_id", referencedColumnName="id")
	private ProductionOrder productionOrder;
	
	@Column
	private Float weight_additive;

	public void populateAdditiveProductionOrderFromInput(AdditiveProductionOrderJson input, ProductionOrder po, Additive add) {
		this.setWeight_additive(input.getWeight_additive());
		this.setProductionOrder(po);
		this.setAdditive(add);
	}

	public String toString() {
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		return serializer.toString();
	}
	
/** GETTERS and SETTERS **/
		
	public Additive getAdditive() {
		return additive;
	}

	public void setAdditive(Additive additive) {
		this.additive = additive;
	}

	public ProductionOrder getProductionOrder() {
		return productionOrder;
	}

	public void setProductionOrder(ProductionOrder productionOrder) {
		this.productionOrder = productionOrder;
	}

	public Float getWeight_additive() {
		return weight_additive;
	}

	public void setWeight_additive(Float weight_additive) {
		this.weight_additive = weight_additive;
	}

}