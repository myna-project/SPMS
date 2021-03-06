package it.mynaproject.spms.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.mynaproject.spms.util.ClassSerializer;

@JsonInclude(Include.NON_EMPTY)
public class SettingPhaseJson extends PhaseJson {
	
	private Float effective_mixture_temperature;
	
	private MixtureModeJson effective_mixture_mode;

	public String toString() {
		StringBuilder builder = new StringBuilder();
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		builder.append(serializer.toString());
		builder.append(" (Superclass: ");
		builder.append(super.toString());
		builder.append(" )");
		return builder.toString();
	}
	
/** GETTERS and SETTERS **/

	public Float getEffective_mixture_temperature() {
		return effective_mixture_temperature;
	}

	public void setEffective_mixture_temperature(final Float effective_mixture_temperature) {
		this.effective_mixture_temperature = effective_mixture_temperature;
	}

	public MixtureModeJson getEffective_mixture_mode() {
		return effective_mixture_mode;
	}

	public void setEffective_mixture_mode(final MixtureModeJson effective_mixture_mode) {
		this.effective_mixture_mode = effective_mixture_mode;
	}
}
