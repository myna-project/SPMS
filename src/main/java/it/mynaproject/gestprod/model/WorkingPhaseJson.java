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
	private Integer production_order_id;
	
	private List<WorkingPhaseUserJson> shifts;

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
	
	public Integer getProduction_order_id() {
		return production_order_id;
	}

	public void setProduction_order_id(Integer production_order_id) {
		this.production_order_id = production_order_id;
	}

	public List<WorkingPhaseUserJson> getShifts() {
		return shifts;
	}
	
	public void setShifts(List<WorkingPhaseUserJson> shifts) {
		this.shifts = shifts;
	}
}