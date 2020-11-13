package it.mynaproject.gestprod.domain;

import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Id;

import it.mynaproject.gestprod.model.WorkingPhaseUserJson;
import it.mynaproject.gestprod.util.ClassSerializer;

@Entity
@Table(name = "working_phases_users")
public class WorkingPhaseUser implements java.io.Serializable {
	
	@Id
	@ManyToOne
	@JoinColumn(name="working_phase_id", referencedColumnName="id")
	private WorkingPhase workingPhase;
	
	@Id
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName="id")
	private User user;
	
	@Id
	@Column(nullable=false)
	private Instant start_time;
	
	@Id
	@Column(nullable=false)
	private Instant end_time;

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

	public Instant getStart_time() {
		return start_time;
	}

	public void setStart_time(Instant start_time) {
		this.start_time = start_time;
	}

	public Instant getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Instant end_time) {
		this.end_time = end_time;
	}

	public void populateWorkingPhaseUserFromInput(WorkingPhaseUserJson input) {
		this.setStart_time(input.getStart_time());
		this.setEnd_time(input.getEnd_time());
	}

	@Override
	public String toString() {
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		return serializer.toString();
	}
}
