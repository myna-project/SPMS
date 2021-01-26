package it.mynaproject.spms.service.impl;

import java.util.ArrayList;

import java.util.List;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.spms.dao.CustomerDao;
import it.mynaproject.spms.domain.Customer;
import it.mynaproject.spms.domain.ProductionOrder;
import it.mynaproject.spms.exception.ConflictException;
import it.mynaproject.spms.exception.NotFoundException;
import it.mynaproject.spms.model.CustomerJson;
import it.mynaproject.spms.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CustomerDao customerDao;

	@Transactional(readOnly = true)
	@Override
	public Customer getCustomer(Integer id) {

		Customer p = this.customerDao.getCustomer(id);
		if (p == null)
			throw new NotFoundException(404, "Customer " + id + " not found");
		return p;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Customer> getCustomers() {

		List<Customer> pList = new ArrayList<Customer>();
		for (Customer p : this.customerDao.getCustomers())
			pList.add(p);

		return pList;
	}

	@Transactional
	@Override
	public void persist(Customer customer) {
		this.customerDao.persist(customer);
	}
	
	@Transactional
	@Override
	public Customer createCustomerFromJson(CustomerJson input) {

		log.info("Creating new customer: {}", input.toString());

		Customer c = this.customerDao.checkCustomerExists(input.getName(), null);
		if(c != null) {
			throw new ConflictException(8001, "Customer " + input.getName() + " already registered with id: " + c.getId());
		}
		
		Customer customer = new Customer();
		customer.populateCustomerFromInput(input);

		this.persist(customer);

		return customer;
	}

	@Transactional
	@Override
	public void update(Customer customer) {
		this.customerDao.update(customer);
	}

	@Transactional
	@Override
	public Customer updateCustomerFromJson(Integer id, CustomerJson input) {

		log.info("Updating customer with id: {}", id);

		Customer c = this.customerDao.checkCustomerExists(input.getName(), id);
		if(c != null) {
			throw new ConflictException(8001, "Customer " + input.getName() + " already registered with id: " + c.getId());
		}
		
		Customer customer = this.getCustomer(id);
		customer.populateCustomerFromInput(input);

		this.update(customer);

		return customer;
	}

	@Transactional
	@Override
	public void deleteCustomerById(Integer id) {

		log.info("Deleting customer: {}", id);
		Customer c = this.customerDao.getCustomer(id);
		
		if (c == null) {
			throw new NotFoundException(404, "Customer " + id + " not found");
		}
		
		Date now = new Date();
		if (c.getProductionOrders() != null) {
			for(ProductionOrder po : c.getProductionOrders()) {
				if(now.after(po.getDelivery_date()))
					throw new ConflictException(8101, "Cannot delete customer: " + id + ", production order already delivered.");
			}
		}
		this.customerDao.delete(c);
	}
	
}