package it.mynaproject.spms.service;

import it.mynaproject.spms.model.IEnergyMeasuresJson;

public interface IEnergyService {

	public void getToken();
	public void putMeasures(IEnergyMeasuresJson measures);
	public void deleteMeasures(String start, String end);
}
