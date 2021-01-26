package it.mynaproject.gestprod.model;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.mynaproject.gestprod.util.ClassSerializer;

@JsonInclude(Include.NON_EMPTY)
public class PhaseJson {
	
	private Integer id;
	
	@NotNull
	private ProductionOrderJson productionOrder;
	
	private UserJson user;

	private Instant start_time;
	
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
	
	public ProductionOrderJson getProductionOrder() {
		return productionOrder;
	}

	public void setProductionOrder(ProductionOrderJson productionOrder) {
		this.productionOrder = productionOrder;
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
