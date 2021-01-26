package it.mynaproject.gestprod.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.mynaproject.gestprod.util.ClassSerializer;

@JsonInclude(Include.NON_EMPTY)
public class WorkingPhaseJson extends PhaseJson {

	private List<WorkingPhaseMeasureJson> measures;

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

	public List<WorkingPhaseMeasureJson> getMeasures() {
		return measures;
	}

	public void setMeasures(List<WorkingPhaseMeasureJson> measures) {
		this.measures = measures;
	}
}
