package it.mynaproject.gestprod.model;

import java.util.List;


import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.mynaproject.gestprod.util.ClassSerializer;

@JsonInclude(Include.NON_EMPTY)
public class WorkingPhaseJson {
	
	private Integer id;
	
	@NotNull
	private ProductionOrderJson productionOrder;
	
	private List<WorkingPhaseUserJson> shifts;

	private List<WorkingPhaseMeasureJson> measures;
	
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
	
	public ProductionOrderJson getProductionOrder() {
		return productionOrder;
	}

	public void setProductionOrder(ProductionOrderJson productionOrder) {
		this.productionOrder = productionOrder;
	}

	public List<WorkingPhaseUserJson> getShifts() {
		return shifts;
	}
	
	public void setShifts(List<WorkingPhaseUserJson> shifts) {
		this.shifts = shifts;
	}
	
	public List<WorkingPhaseMeasureJson> getMeasures() {
		return measures;
	}

	public void setMeasures(List<WorkingPhaseMeasureJson> measures) {
		this.measures = measures;
	}
}