package it.mynaproject.gestprod.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import it.mynaproject.gestprod.model.SettingPhaseJson;
import it.mynaproject.gestprod.util.ClassSerializer;

@Entity
@Table(name = "setting_phases")
public class SettingPhase extends Phase {
	
	@Column
	private Float effective_mixture_temperature;
	
	@Column
	private Integer effective_mixture_mode_id;

	public void populateSettingPhaseFromInput(SettingPhaseJson input, ProductionOrder po, User u) {
		this.setProductionOrder(po);
		this.setUser(u);
		this.setStart_time(input.getStart_time());
		this.setEnd_time(input.getEnd_time());
		this.setEffective_mixture_mode_id(input.getEffective_mixture_mode_id());
		this.setEffective_mixture_temperature(input.getEffective_mixture_temperature());
	}

	public String toString() {
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		return serializer.toString();
	}
	
/** GETTERS and SETTERS **/

	public Float getEffective_mixture_temperature() {
		return effective_mixture_temperature;
	}

	public void setEffective_mixture_temperature(final Float effective_mixture_temperature) {
		this.effective_mixture_temperature = effective_mixture_temperature;
	}

	public Integer getEffective_mixture_mode_id() {
		return effective_mixture_mode_id;
	}

	public void setEffective_mixture_mode_id(final Integer effective_mixture_mode_id) {
		this.effective_mixture_mode_id = effective_mixture_mode_id;
	}
}
