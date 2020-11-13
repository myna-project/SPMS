package it.mynaproject.gestprod.model;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.mynaproject.gestprod.util.ClassSerializer;

@JsonInclude(Include.NON_EMPTY)
public class AdditiveProductionOrderJson {
	
	@NotBlank
	private Integer additive_id;
	
	@NotBlank
	private Integer production_order_code_id;
	
	@NotBlank
	private Float weight_additive;
	
	public Integer getAdditive_id() {
		return additive_id;
	}

	public void setAdditive_id(Integer additive_id) {
		this.additive_id = additive_id;
	}

	public Integer getProduction_order_code_id() {
		return production_order_code_id;
	}

	public void setProduction_order_code_id(Integer production_order_code_id) {
		this.production_order_code_id = production_order_code_id;
	}

	public Float getWeight_additive() {
		return weight_additive;
	}

	public void setWeight_additive(Float weight_additive) {
		this.weight_additive = weight_additive;
	}

	public String toString() {
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		return serializer.toString();
	}
}