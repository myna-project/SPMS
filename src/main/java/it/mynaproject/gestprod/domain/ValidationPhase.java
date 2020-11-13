package it.mynaproject.gestprod.domain;

import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import it.mynaproject.gestprod.model.ValidationPhaseJson;
import it.mynaproject.gestprod.util.ClassSerializer;

@Entity
@Table(name = "validation_phases")
public class ValidationPhase extends BaseDomain {
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="production_order_id", referencedColumnName="id")
	private ProductionOrder productionOrder;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="user_id", referencedColumnName="id")
	private User user;
	
	@Column
	private Instant start_time;
	
	@Column
	private Instant end_time;
	
	@Column(nullable=false)
	private Float humidity_finished_product;
	
	@Column(nullable=false)
	private Float density_finished_product;
	
	@Column
	private String packaging_state;
	
	@Column
	private Float sieve_quantity;
	
	@Column
	private Float chimney_quantity;
	
	@Column
	private Float tower_entry_temperature;
	
	@Column
	private Float tower_intern_temperature;
	
	@Column
	private Float cyclon_entry_temperature;
	
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

	@Column
	private String note;
	
	public ProductionOrder getProductionOrder() {
		return productionOrder;
	}

	public void setProductionOrder(ProductionOrder productionOrder) {
		this.productionOrder = productionOrder;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public void populateValidationPhaseFromInput(ValidationPhaseJson input, ProductionOrder po, User u) {
		this.setProductionOrder(po);
		this.setUser(u);
		this.setStart_time(input.getStart_time());
		this.setEnd_time(input.getEnd_time());
		this.setHumidity_finished_product(input.getHumidity_finished_product());
		this.setDensity_finished_product(input.getDensity_finished_product());
		this.setPackaging_state(input.getPackaging_state());
		this.setSieve_quantity(input.getSieve_quantity());
		this.setChimney_quantity(input.getChimney_quantity());
		this.setTower_entry_temperature(input.getTower_entry_temperature());
		this.setTower_intern_temperature(input.getTower_intern_temperature());
		this.setCyclon_entry_temperature(input.getCyclon_entry_temperature());
	}

	public String toString() {
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		return serializer.toString();
	}
}