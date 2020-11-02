package it.mynaproject.gestprod.dao;

import java.util.List;

import it.mynaproject.gestprod.domain.Customer;

public interface CustomerDao {

	public void persist(Customer customer);
	public void update(Customer customer);
	public void delete(Customer customer);
	public Customer getCustomer(Integer id);
	public List<Customer> getCustomers();
}
