package it.mynaproject.gestprod.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import it.mynaproject.gestprod.model.CustomerJson;

@Entity
@Table(name = "customers")
public class Customer extends BaseDomain {

	@Column
	private String name;

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
