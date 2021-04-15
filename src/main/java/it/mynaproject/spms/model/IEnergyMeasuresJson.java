package it.mynaproject.spms.model;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IEnergyMeasuresJson {

	@NotNull
	@JsonProperty("client_id")
	Integer clientId;

	@NotNull
	@JsonProperty("device_id")
	String deviceId;

	@NotNull
	@JsonProperty("at")
	String at;

	@NotNull
	@JsonProperty("measures")
	ArrayList<IEnergyMeasureJson> measures;

	public Integer getClientId() {
		return clientId;
	}

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getAt() {
		return at;
	}

	public void setAt(String at) {
		this.at = at;
	}

	public ArrayList<IEnergyMeasureJson> getMeasures() {
		return measures;
	}

	public void setMeasures(ArrayList<IEnergyMeasureJson> measures){
		this.measures = measures;
	}
}