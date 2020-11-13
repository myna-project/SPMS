package it.mynaproject.gestprod.model;

import java.time.Instant;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.mynaproject.gestprod.util.ClassSerializer;

@JsonInclude(Include.NON_EMPTY)
public class PhaseJson {
	
	@NotBlank
	private Integer id;
	
	@NotBlank
	private Integer production_order_id;
	
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
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProduction_order_id() {
		return production_order_id;
	}

	public void setProduction_order_id(int production_order_id) {
		this.production_order_id = production_order_id;
	}
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
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
