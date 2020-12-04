package it.mynaproject.gestprod.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.mynaproject.gestprod.util.ClassSerializer;

@JsonInclude(Include.NON_EMPTY)
public class AdditiveProductionOrderJson {
	
	private Integer id;
	
	@NotNull
	private AdditiveJson additive;
	
	@NotNull
	private ProductionOrderJson productionOrder;
	
	@NotNull
	private Float weight_additive;

	public String toString() {
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		return serializer.toString();
	}
	
/** GETTERS and SETTERS **/
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public AdditiveJson getAdditive() {
		return additive;
	}

	public void setAdditive(AdditiveJson additive) {
		this.additive = additive;
	}

	public ProductionOrderJson getProductionOrder() {
		return productionOrder;
	}

	public void setProductionOrder(ProductionOrderJson productionOrder) {
		this.productionOrder = productionOrder;
	}

	public Float getWeight_additive() {
		return weight_additive;
	}

	public void setWeight_additive(Float weight_additive) {
		this.weight_additive = weight_additive;
	}
}