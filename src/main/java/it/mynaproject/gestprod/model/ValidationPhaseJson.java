package it.mynaproject.gestprod.model;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.mynaproject.gestprod.util.ClassSerializer;

@JsonInclude(Include.NON_EMPTY)
public class ValidationPhaseJson extends PhaseJson {
	
	@NotBlank
	private Float humidity_finished_product;
	
	@NotBlank
	private Float density_finished_product;
	
	private String packaging_state;
	
	private Float sieve_quantity;
	
	private Float chimney_quantity;
	
	private Float tower_entry_temperature;
	
	private Float tower_intern_temperature;
	
	private Float cyclon_entry_temperature;
	
	private String note;
	
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

	public Float getHumidity_finished_product() {
		return humidity_finished_product;
	}

	public void setHumidity_finished_product(final Float humidity_finished_product) {
		this.humidity_finished_product = humidity_finished_product;
	}

	public Float getDensity_finished_product() {
		return density_finished_product;
	}

	public void setDensity_finished_product(final Float density_finished_product) {
		this.density_finished_product = density_finished_product;
	}

	public String getPackaging_state() {
		return packaging_state;
	}

	public void setPackaging_state(String packaging_state) {
		this.packaging_state = packaging_state;
	}

	public Float getSieve_quantity() {
		return sieve_quantity;
	}

	public void setSieve_quantity(final Float sieve_quantity) {
		this.sieve_quantity = sieve_quantity;
	}

	public Float getChimney_quantity() {
		return chimney_quantity;
	}

	public void setChimney_quantity(final Float chimney_quantity) {
		this.chimney_quantity = chimney_quantity;
	}

	public Float getTower_entry_temperature() {
		return tower_entry_temperature;
	}

	public void setTower_entry_temperature(final Float tower_entry_temperature) {
		this.tower_entry_temperature = tower_entry_temperature;
	}

	public Float getTower_intern_temperature() {
		return tower_intern_temperature;
	}

	public void setTower_intern_temperature(final Float tower_intern_temperature) {
		this.tower_intern_temperature = tower_intern_temperature;
	}

	public Float getCyclon_entry_temperature() {
		return cyclon_entry_temperature;
	}

	public void setCyclon_entry_temperature(final Float cyclon_entry_temperature) {
		this.cyclon_entry_temperature = cyclon_entry_temperature;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}