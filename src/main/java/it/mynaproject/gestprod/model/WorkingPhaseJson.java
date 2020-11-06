package it.mynaproject.gestprod.model;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.mynaproject.gestprod.util.ClassSerializer;

@JsonInclude(Include.NON_EMPTY)
public class WorkingPhaseJson {
	
	@NotBlank
	private List<WorkingPhaseShiftJson> shifts;

	public List<WorkingPhaseShiftJson> getShifts() {
		return shifts;
	}

	public void setShifts(List<WorkingPhaseShiftJson> shifts) {
		this.shifts = shifts;
	}

	public String toString() {
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		return serializer.toString();
	}
}