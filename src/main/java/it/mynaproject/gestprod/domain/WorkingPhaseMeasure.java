package it.mynaproject.gestprod.domain;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import it.mynaproject.gestprod.model.WorkingPhaseMeasureJson;
import it.mynaproject.gestprod.util.ClassSerializer;

@Entity
@Table(name = "working_phases_measurements")
public class WorkingPhaseMeasure extends BaseDomain {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="working_phase_id", referencedColumnName="id")
	private WorkingPhase workingPhase;
	
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName="id")
	private User user;
	
	@Column(nullable=false)
	private Instant time;
	
	@Column(nullable=false)
	private Float finished_product_quantity;
	
	public void populateWorkingPhaseMeasureFromInput(WorkingPhaseMeasureJson input, WorkingPhase w, User u) {
		this.workingPhase = w;
		this.user = u;
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
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public WorkingPhase getWorkingPhase() {
		return workingPhase;
	}

	public void setWorkingPhase(WorkingPhase workingPhase) {
		this.workingPhase = workingPhase;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
