package it.mynaproject.spms.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.spms.dao.WorkingPhaseMeasureDao;
import it.mynaproject.spms.domain.WorkingPhase;
import it.mynaproject.spms.domain.WorkingPhaseMeasure;
import it.mynaproject.spms.exception.NotFoundException;
import it.mynaproject.spms.model.IEnergyMeasureJson;
import it.mynaproject.spms.model.IEnergyMeasuresJson;
import it.mynaproject.spms.model.WorkingPhaseMeasureJson;
import it.mynaproject.spms.service.IEnergyService;
import it.mynaproject.spms.service.WorkingPhaseMeasureService;
import it.mynaproject.spms.service.WorkingPhaseService;

@Service
public class WorkingPhaseMeasureServiceImpl implements WorkingPhaseMeasureService {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WorkingPhaseMeasureDao workingPhaseMeasureDao;

	@Autowired
	private WorkingPhaseService workingPhaseService;

	@Autowired
	private IEnergyService iEnergyService;

	@Transactional(readOnly = true)
	@Override
	public WorkingPhaseMeasure getWorkingPhaseMeasure(Integer id, Integer sid, Integer tid) {

		WorkingPhaseMeasure workingPhaseMeasure = null;

		WorkingPhase p = this.workingPhaseService.getWorkingPhase(id, sid);
		for (WorkingPhaseMeasure sf : p.getWorkingPhaseMeasureList()) {
			if (sf.getId() == tid) {
				workingPhaseMeasure = sf;
				break;
			}
		}

		if (workingPhaseMeasure == null)
			throw new NotFoundException(404, "WorkingPhaseMeasure " + tid + " not found");

		return workingPhaseMeasure;
	}

	@Transactional(readOnly = true)
	@Override
	public List<WorkingPhaseMeasure> getWorkingPhaseMeasures(Integer id, Integer sid) {

		WorkingPhase p = this.workingPhaseService.getWorkingPhase(id, sid);

		return p.getWorkingPhaseMeasureList();
	}

	@Transactional(readOnly = true)
	@Override
	public List<WorkingPhaseMeasure> getAllMeasures(Date start, Date end) {
		return this.workingPhaseMeasureDao.getAllMeasures(start, end);
	}

	@Transactional
	@Override
	public void persist(WorkingPhaseMeasure workingPhaseMeasure) {
		this.workingPhaseMeasureDao.persist(workingPhaseMeasure);

		this.updateIEnergyMeasures(workingPhaseMeasure);
	}

	@Transactional
	@Override
	public WorkingPhaseMeasure createWorkingPhaseMeasureFromJson(Integer id, Integer sid, WorkingPhaseMeasureJson input) {

		log.info("Creating new workingPhaseMeasure: {}", input.toString());

		WorkingPhase w = this.workingPhaseService.getWorkingPhase(id, sid);

		WorkingPhaseMeasure workingPhaseMeasure = new WorkingPhaseMeasure();
		workingPhaseMeasure.populateWorkingPhaseMeasureFromInput(input, w);

		this.persist(workingPhaseMeasure);

		return workingPhaseMeasure;
	}

	@Transactional
	@Override
	public void update(WorkingPhaseMeasure workingPhaseMeasure) {
		this.workingPhaseMeasureDao.update(workingPhaseMeasure);

		this.updateIEnergyMeasures(workingPhaseMeasure);
	}

	@Transactional
	@Override
	public WorkingPhaseMeasure updateWorkingPhaseMeasureFromJson(Integer id, Integer sid, Integer tid, WorkingPhaseMeasureJson input) {

		log.info("Updating workingPhaseMeasure with id: {}", id);

		WorkingPhase w = this.workingPhaseService.getWorkingPhase(id, sid);

		WorkingPhaseMeasure workingPhaseMeasure = new WorkingPhaseMeasure();
		workingPhaseMeasure.populateWorkingPhaseMeasureFromInput(input, w);

		this.update(workingPhaseMeasure);

		return workingPhaseMeasure;
	}

	@Transactional
	@Override
	public void deleteWorkingPhaseMeasureById(Integer id) {

		log.info("Deleting workingPhaseMeasure: {}", id);

		WorkingPhaseMeasure workingPhaseMeasure = this.workingPhaseMeasureDao.getWorkingPhaseMeasure(id);

		this.workingPhaseMeasureDao.delete(workingPhaseMeasure);

		this.deleteIEnergyMeasures(workingPhaseMeasure);
	}

	private void updateIEnergyMeasures(WorkingPhaseMeasure workingPhaseMeasure) {

		IEnergyMeasuresJson iEnergyMeasuresJson = new IEnergyMeasuresJson();

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		iEnergyMeasuresJson.setAt(format.format(Date.from(workingPhaseMeasure.getTime())));

		ArrayList<IEnergyMeasureJson> iEnergyMeasuresList = new ArrayList<IEnergyMeasureJson>();

		IEnergyMeasureJson iEnergyMeasureJson = new IEnergyMeasureJson();
		iEnergyMeasureJson.setValue(Float.toString(workingPhaseMeasure.getFinished_product_quantity()));
		iEnergyMeasuresList.add(iEnergyMeasureJson);

		iEnergyMeasuresJson.setMeasures(iEnergyMeasuresList);

		this.iEnergyService.putMeasures(iEnergyMeasuresJson);
	}

	private void deleteIEnergyMeasures(WorkingPhaseMeasure workingPhaseMeasure) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

		this.iEnergyService.deleteMeasures(format.format(Date.from(workingPhaseMeasure.getTime())), format.format(Date.from(workingPhaseMeasure.getTime())));
	}
}