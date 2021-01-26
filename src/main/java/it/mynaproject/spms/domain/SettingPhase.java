package it.mynaproject.spms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import it.mynaproject.spms.model.SettingPhaseJson;
import it.mynaproject.spms.util.ClassSerializer;

@Entity
@Table(name = "setting_phases")
public class SettingPhase extends Phase {
	
	@Column
	private Float effective_mixture_temperature;
	
	@ManyToOne
	@JoinColumn(name="effective_mixture_mode_id", referencedColumnName="id")
	private MixtureMode effective_mixture_mode;

	public void populateSettingPhaseFromInput(SettingPhaseJson input, ProductionOrder po, User u) {
		this.setProductionOrder(po);
		this.setUser(u);
		this.setStart_time(input.getStart_time());
		this.setEnd_time(input.getEnd_time());
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

	public MixtureMode getEffective_mixture_mode() {
		return effective_mixture_mode;
	}

	public void setEffective_mixture_mode(final MixtureMode effective_mixture_mode_id) {
		this.effective_mixture_mode = effective_mixture_mode_id;
	}
}
