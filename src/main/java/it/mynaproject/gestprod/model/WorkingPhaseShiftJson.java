package it.mynaproject.gestprod.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.mynaproject.gestprod.util.ClassSerializer;

@JsonInclude(Include.NON_EMPTY)
public class WorkingPhaseShiftJson extends PhaseJson {
	
	private Integer working_phase_id;
	
	public int getWorking_phase_id() {
		return working_phase_id;
	}

	public void setWorking_phase_id(int working_phase_id) {
		this.working_phase_id = working_phase_id;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		builder.append(serializer.toString());
		builder.append(" (Superclass: ");
		builder.append(super.toString());
		builder.append(" )");
		return builder.toString();
	}
}