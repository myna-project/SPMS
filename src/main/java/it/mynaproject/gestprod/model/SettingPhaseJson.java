package it.mynaproject.gestprod.model;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.mynaproject.gestprod.util.ClassSerializer;

@JsonInclude(Include.NON_EMPTY)
public class SettingPhaseJson extends PhaseJson {
	
	@NotBlank
	private float effective_mixture_temperature;
	
	@NotBlank
	private int effective_mixture_mode_id;

	public float getEffective_mixture_temperature() {
		return effective_mixture_temperature;
	}

	public void setEffective_mixture_temperature(float effective_mixture_temperature) {
		this.effective_mixture_temperature = effective_mixture_temperature;
	}

	public int getEffective_mixture_mode_id() {
		return effective_mixture_mode_id;
	}

	public void setEffective_mixture_mode_id(int effective_mixture_mode_id) {
		this.effective_mixture_mode_id = effective_mixture_mode_id;
	}

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
}
