package it.mynaproject.gestprod.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import it.mynaproject.gestprod.model.WorkingPhaseJson;
import it.mynaproject.gestprod.util.ClassSerializer;

@Entity
@Table(name = "working_phases")
public class WorkingPhase extends BaseDomain {
	
	@ManyToOne
	@JoinColumn(name="production_order_id", referencedColumnName="id")
	private ProductionOrder productionOrder;
	
	@OneToMany(mappedBy="workingPhase", cascade=CascadeType.ALL)
	private List<WorkingPhaseUser> workingPhaseUserList;
	
	public ProductionOrder getProductionOrder() {
		return productionOrder;
	}

	public void setProductionOrder(ProductionOrder productionOrder) {
		this.productionOrder = productionOrder;
	}

	public List<WorkingPhaseUser> getWorkingPhaseUserList() {
		return workingPhaseUserList;
	}

	public void setWorkingPhaseUserList(List<WorkingPhaseUser> workingPhaseUserList) {
		this.workingPhaseUserList = workingPhaseUserList;
	}

	public void populateWorkingPhaseFromInput(WorkingPhaseJson input, ProductionOrder po, List<User> u) {
		this.setProductionOrder(po);
	}

	public String toString() {
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		return serializer.toString();
	}
}