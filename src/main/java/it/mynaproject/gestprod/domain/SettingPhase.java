package it.mynaproject.gestprod.domain;

import java.time.Instant;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToMany;

import it.mynaproject.gestprod.model.SettingPhaseJson;
import it.mynaproject.gestprod.util.ClassSerializer;

@Entity
@Table(name = "additives")
public class SettingPhase extends BaseDomain {
	
	@Column(nullable=false)
	private int production_order_id;
	
	@Column(nullable=false)
	private int user_id;
	
	@Column
	private Instant start_time;
	
	@Column
	private Instant end_time;
	
	@Column
	private float effective_mixture_temperature;
	
	@Column
	private int effective_mixture_mode_id;
	
	public int getProduction_order_id() {
		return production_order_id;
	}

	public void setProduction_order_id(int production_order_id) {
		this.production_order_id = production_order_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Instant getStart_time() {
		return start_time;
	}

	public void setStart_time(Instant start_time) {
		this.start_time = start_time;
	}

	public Instant getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Instant end_time) {
		this.end_time = end_time;
	}

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

	public void populateSettingPhaseFromInput(SettingPhaseJson input) {
		this.setProduction_order_id(input.getProduction_order_id());
		this.setUser_id(input.getUser_id());
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
}
