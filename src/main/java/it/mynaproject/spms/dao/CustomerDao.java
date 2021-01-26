package it.mynaproject.spms.dao;

import java.util.List;

import org.springframework.lang.Nullable;

import it.mynaproject.spms.domain.Customer;

public interface CustomerDao {

	public void persist(Customer customer);
	public void update(Customer customer);
	public void delete(Customer customer);
	public Customer getCustomer(Integer id);
	public Customer checkCustomerExists(String name, @Nullable Integer id);
	public List<Customer> getCustomers();
}
