package it.mynaproject.gestprod.service;

import java.util.List;

import it.mynaproject.gestprod.domain.Customer;
import it.mynaproject.gestprod.model.CustomerJson;

public interface CustomerService {

	public Customer getCustomer(Integer id, Boolean isAdmin);
	public List<Customer> getCustomers(Boolean isAdmin);
	public void persist(Customer customer);
	public Customer createCustomerFromJson(CustomerJson input);
	public void update(Customer customer);
	public Customer updateCustomerFromJson(Integer id, CustomerJson input, Boolean isAdmin);
	public void deleteCustomerById(Integer id);
}
