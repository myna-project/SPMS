package it.mynaproject.gestprod.model;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.mynaproject.gestprod.util.ClassSerializer;

@JsonInclude(Include.NON_EMPTY)
public class ValidationPhaseJson extends PhaseJson {
	
	@NotBlank
	private float humidity_finished_product;
	
	@NotBlank
	private float density_finished_product;
	
	private String packaging_state;
	
	private float sieve_quantity;
	
	private float chimney_quantity;
	
	private float tower_entry_temperature;
	
	private float tower_intern_temperature;
	
	private float cyclon_entry_temperature;
	
	private String note;
	
	

	public float getHumidity_finished_product() {
		return humidity_finished_product;
	}



	public void setHumidity_finished_product(float humidity_finished_product) {
		this.humidity_finished_product = humidity_finished_product;
	}



	public float getDensity_finished_product() {
		return density_finished_product;
	}



	public void setDensity_finished_product(float density_finished_product) {
		this.density_finished_product = density_finished_product;
	}



	public String getPackaging_state() {
		return packaging_state;
	}



	public void setPackaging_state(String packaging_state) {
		this.packaging_state = packaging_state;
	}



	public float getSieve_quantity() {
		return sieve_quantity;
	}



	public void setSieve_quantity(float sieve_quantity) {
		this.sieve_quantity = sieve_quantity;
	}



	public float getChimney_quantity() {
		return chimney_quantity;
	}



	public void setChimney_quantity(float chimney_quantity) {
		this.chimney_quantity = chimney_quantity;
	}



	public float getTower_entry_temperature() {
		return tower_entry_temperature;
	}



	public void setTower_entry_temperature(float tower_entry_temperature) {
		this.tower_entry_temperature = tower_entry_temperature;
	}



	public float getTower_intern_temperature() {
		return tower_intern_temperature;
	}



	public void setTower_intern_temperature(float tower_intern_temperature) {
		this.tower_intern_temperature = tower_intern_temperature;
	}



	public float getCyclon_entry_temperature() {
		return cyclon_entry_temperature;
	}



	public void setCyclon_entry_temperature(float cyclon_entry_temperature) {
		this.cyclon_entry_temperature = cyclon_entry_temperature;
	}



	public String getNote() {
		return note;
	}



	public void setNote(String note) {
		this.note = note;
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