package it.mynaproject.gestprod.model;

import java.time.Instant;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.mynaproject.gestprod.util.ClassSerializer;

@JsonInclude(Include.NON_EMPTY)
public class WorkingPhaseUserJson {
	
	@NotBlank
	private Integer working_phase_id;
	
	@NotBlank
	private Integer user_id;
	
	@NotBlank
	private Instant start_time;
	
	@NotBlank
	private Instant end_time;
	
	public String toString() {
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		return serializer.toString();
	}
	
/** GETTERS and SETTERS **/
	
	public Integer getWorking_phase_id() {
		return working_phase_id;
	}

	public void setWorking_phase_id(Integer working_phase_id) {
		this.working_phase_id = working_phase_id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
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