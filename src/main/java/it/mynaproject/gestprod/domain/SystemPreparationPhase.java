package it.mynaproject.gestprod.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import it.mynaproject.gestprod.model.SystemPreparationPhaseJson;
import it.mynaproject.gestprod.util.ClassSerializer;

@Entity
@Table(name = "system_preparation_phases")
public class SystemPreparationPhase extends Phase {

	public void populateSystemPreparationPhaseFromInput(SystemPreparationPhaseJson input, ProductionOrder po, User u) {
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
	
}