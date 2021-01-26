package it.mynaproject.spms.domain;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import it.mynaproject.spms.model.WorkingPhaseMeasureJson;
import it.mynaproject.spms.util.ClassSerializer;

@Entity
@Table(name = "working_phases_measurements")
public class WorkingPhaseMeasure extends BaseDomain {
	
	@ManyToOne
	@JoinColumn(name="working_phase_id", referencedColumnName="id")
	private WorkingPhase workingPhase;

	@Column(nullable=false)
	private Instant time;

	@Column(nullable=false)
	private Float finished_product_quantity;

	public void populateWorkingPhaseMeasureFromInput(WorkingPhaseMeasureJson input, WorkingPhase w) {
		this.workingPhase = w;
		this.setTime(input.getTime());
		this.setFinished_product_quantity(input.getFinished_product_quantity());
	}

	@Override
	public String toString() {
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		return serializer.toString();
	}

/** GETTERS and SETTERS **/

	public WorkingPhase getWorkingPhase() {
		return workingPhase;
	}

	public void setWorkingPhase(WorkingPhase workingPhase) {
		this.workingPhase = workingPhase;
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
}
