package it.mynaproject.spms.service;

import java.util.List;

import it.mynaproject.spms.domain.WorkingPhaseMeasure;
import it.mynaproject.spms.model.WorkingPhaseMeasureJson;

public interface WorkingPhaseMeasureService {

	public WorkingPhaseMeasure getWorkingPhaseMeasure(Integer id, Integer sid, Integer tid);
	public List<WorkingPhaseMeasure> getWorkingPhaseMeasures(Integer id, Integer sid);
	public void persist(WorkingPhaseMeasure workingPhaseMeasure);
	public WorkingPhaseMeasure createWorkingPhaseMeasureFromJson(Integer id, Integer sid, WorkingPhaseMeasureJson input);
	public void update(WorkingPhaseMeasure workingPhaseMeasure);
	public WorkingPhaseMeasure updateWorkingPhaseMeasureFromJson(Integer id, Integer sid, Integer tid, WorkingPhaseMeasureJson input);
	public void deleteWorkingPhaseMeasureById(Integer id);
}
