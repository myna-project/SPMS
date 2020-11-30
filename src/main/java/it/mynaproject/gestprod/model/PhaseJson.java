package it.mynaproject.gestprod.model;

import java.time.Instant;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.mynaproject.gestprod.util.ClassSerializer;

@JsonInclude(Include.NON_EMPTY)
public class PhaseJson {
	
	private Integer id;
	
	@NotNull
	private Integer production_order_id;
	
	@NotNull
	private Integer user_id;

	@NotNull
	private Instant start_time;
	
	@NotNull
	private Instant end_time;
	
	public String toString() {
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		return serializer.toString();
	}
	
/** GETTERS and SETTERS **/
	
	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Integer getProduction_order_id() {
		return production_order_id;
	}

	public void setProduction_order_id(final Integer production_order_id) {
		this.production_order_id = production_order_id;
	}
	
	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(final Integer user_id) {
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
