package it.mynaproject.spms.dao;

import java.util.List;

import it.mynaproject.spms.domain.WorkingPhaseMeasure;

public interface WorkingPhaseMeasureDao {

	public void persist(WorkingPhaseMeasure workingPhaseMeasure);
	public void update(WorkingPhaseMeasure workingPhaseMeasure);
	public void delete(WorkingPhaseMeasure workingPhaseMeasure);
	public WorkingPhaseMeasure getWorkingPhaseMeasure(Integer id);
	public List<WorkingPhaseMeasure> getWorkingPhaseMeasures();
}
