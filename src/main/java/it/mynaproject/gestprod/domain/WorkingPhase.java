package it.mynaproject.gestprod.domain;

import java.util.List;
import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import it.mynaproject.gestprod.model.WorkingPhaseJson;
import it.mynaproject.gestprod.util.ClassSerializer;

@Entity
@Table(name = "working_phases")
public class WorkingPhase extends BaseDomain {
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="production_order_id", referencedColumnName="id")
	private ProductionOrder productionOrder;
	
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "working_phase_users", joinColumns = { @JoinColumn(name = "working_phase_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") })
	private List<User> users;
	
	@Column
	private Instant start_time;
	
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

	@Column
	private Instant end_time;
	
	public ProductionOrder getProductionOrder() {
		return productionOrder;
	}

	public void setProductionOrder(ProductionOrder productionOrder) {
		this.productionOrder = productionOrder;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void populateWorkingPhaseFromInput(WorkingPhaseJson input, ProductionOrder po, List<User> u) {
		this.setProductionOrder(po);
		this.setUsers(u);
		// TODO handle start / end time
//		this.setStart_time(input.getStart_time());
//		this.setEnd_time(input.getEnd_time());
	}

	public String toString() {
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		return serializer.toString();
	}
}