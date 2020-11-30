package it.mynaproject.gestprod.service;

import java.util.List;

import it.mynaproject.gestprod.domain.Customer;
import it.mynaproject.gestprod.model.CustomerJson;

public interface CustomerService {

	public Customer getCustomer(Integer id);
	public List<Customer> getCustomers();
	public void persist(Customer customer);
	public Customer createCustomerFromJson(CustomerJson input);
	public void update(Customer customer);
	public Customer updateCustomerFromJson(Integer id, CustomerJson input);
	public void deleteCustomerById(Integer id);
}
