package it.mynaproject.spms.service;

import it.mynaproject.spms.model.TogoMeasuresJson;

public interface TogoService {

	public void getToken();
	public void putMeasures(TogoMeasuresJson measures);
	public void deleteMeasures(String start, String end);
}
