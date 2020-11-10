package it.mynaproject.gestprod.domain;

import java.util.List;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToMany;

import it.mynaproject.gestprod.model.CustomerJson;

@Entity
@Table(name = "customers")
public class Customer extends BaseDomain {

	@Column(nullable=false)
	private String name;
	
	@OneToMany(mappedBy="customer")
	private List<ProductionOrder> productionOrders;

	public String getName() {
		return name;
	}

	public void setName(String general_model) {
		this.name = general_model;
	}

	public void populateCustomerFromInput(CustomerJson input) {
		this.setName(input.getName());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Customer [name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
}
