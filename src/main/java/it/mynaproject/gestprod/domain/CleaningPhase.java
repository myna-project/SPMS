package it.mynaproject.gestprod.domain;


import javax.persistence.Entity;
import javax.persistence.Table;

import it.mynaproject.gestprod.model.CleaningPhaseJson;
import it.mynaproject.gestprod.util.ClassSerializer;

@Entity
@Table(name = "cleaning_phases")
public class CleaningPhase extends Phase {
	
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
}