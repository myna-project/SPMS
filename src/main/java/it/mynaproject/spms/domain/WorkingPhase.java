package it.mynaproject.spms.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import it.mynaproject.spms.model.WorkingPhaseJson;
import it.mynaproject.spms.util.ClassSerializer;

@Entity
@Table(name = "working_phases")
public class WorkingPhase extends Phase {

	@OneToMany(mappedBy="workingPhase", cascade=CascadeType.ALL)
	private List<WorkingPhaseMeasure> workingPhaseMeasureList;

	public void populateWorkingPhaseFromInput(WorkingPhaseJson input, ProductionOrder po, User u) {
		this.setProductionOrder(po);
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

	public List<WorkingPhaseMeasure> getWorkingPhaseMeasureList() {
		return workingPhaseMeasureList;
	}

	public void setWorkingPhaseMeasureList(List<WorkingPhaseMeasure> workingPhaseMeasureList) {
		this.workingPhaseMeasureList = workingPhaseMeasureList;
	}
}
