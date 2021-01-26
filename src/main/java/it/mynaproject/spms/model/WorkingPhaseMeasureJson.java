package it.mynaproject.spms.model;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.mynaproject.spms.util.ClassSerializer;

@JsonInclude(Include.NON_EMPTY)
public class WorkingPhaseMeasureJson {

	private Integer id;

	@NotNull
	private WorkingPhaseJson workingPhase;

	@NotNull
	private Instant time;

	@NotNull
	private Float finished_product_quantity;

	public String toString() {
		StringBuilder builder = new StringBuilder();
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		builder.append(serializer.toString());
		return builder.toString();
	}

/** GETTERS and SETTERS **/

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Instant getTime() {
		return time;
	}

	public void setTime(Instant time) {
		this.time = time;
	}

	public Float getFinished_product_quantity() {
		return finished_product_quantity;
	}

	public void setFinished_product_quantity(Float finished_product_quantity) {
		this.finished_product_quantity = finished_product_quantity;
	}

	public WorkingPhaseJson getWorkingPhase() {
		return workingPhase;
	}

	public void setWorkingPhase(WorkingPhaseJson workingPhase) {
		this.workingPhase = workingPhase;
	}
}
