package it.mynaproject.gestprod.model;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.mynaproject.gestprod.util.ClassSerializer;

@JsonInclude(Include.NON_EMPTY)
public class WorkingPhaseUserJson {

	private Integer id;
	
	@NotNull
	private WorkingPhaseJson workingPhase;
	
	private UserJson user;

	private Instant start_time;
	
	private Instant end_time;
	
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
	
/** GETTERS and SETTERS **/

	public WorkingPhaseJson getWorkingPhase() {
		return workingPhase;
	}

	public void setWorkingPhase(WorkingPhaseJson workingPhase) {
		this.workingPhase = workingPhase;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserJson getUser() {
		return user;
	}

	public void setUser(UserJson user) {
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

}