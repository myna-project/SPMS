package it.mynaproject.gestprod.model;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.mynaproject.gestprod.util.ClassSerializer;

@JsonInclude(Include.NON_EMPTY)
public class WorkingPhaseMeasureJson {

	private Integer id;
	
	@NotNull
	private Integer working_phase_id;
	
	@NotNull
	private Integer user_id;
	
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

	
	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
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

	public Integer getWorking_phase_id() {
		return working_phase_id;
	}

	public void setWorking_phase_id(final Integer working_phase_id) {
		this.working_phase_id = working_phase_id;
	}

}
