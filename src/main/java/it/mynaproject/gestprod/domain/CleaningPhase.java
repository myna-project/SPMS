package it.mynaproject.gestprod.domain;

import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import it.mynaproject.gestprod.model.CleaningPhaseJson;
import it.mynaproject.gestprod.util.ClassSerializer;

@Entity
@Table(name = "cleaning_phases")
public class CleaningPhase extends BaseDomain {
	
	@ManyToOne
	@JoinColumn(name="production_order_id", referencedColumnName="id")
	private ProductionOrder productionOrder;
	
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName="id")
	private User user;
	
	@Column
	private Instant start_time;
	
	@Column
	private Instant end_time;
	public void populateCleaningPhaseFromInput(CleaningPhaseJson input, ProductionOrder po, User u) {
		this.setProductionOrder(po);
		this.setUser(u);
		this.setStart_time(input.getStart_time());
		this.setEnd_time(input.getEnd_time());
	}

	public String toString() {
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		return serializer.toString();
	}
	
/** GETTERS and SETTERS **/
		
	public ProductionOrder getProductionOrder() {
		return productionOrder;
	}

	public void setProductionOrder(ProductionOrder productionOrder) {
		this.productionOrder = productionOrder;
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

}