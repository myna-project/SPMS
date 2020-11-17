package it.mynaproject.gestprod.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.gestprod.dao.CustomerDao;
import it.mynaproject.gestprod.domain.Customer;
import it.mynaproject.gestprod.domain.ProductionOrder;
import it.mynaproject.gestprod.exception.*;
import it.mynaproject.gestprod.model.CustomerJson;
import it.mynaproject.gestprod.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CustomerDao customerDao;

	@Transactional(readOnly = true)
	@Override
	public Customer getCustomer(Integer id, Boolean isAdmin) {

		Customer p = this.customerDao.getCustomer(id);
		if (p == null)
			throw new NotFoundException(404, "Customer " + id + " not found");
		return p;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Customer> getCustomers(Boolean isAdmin) {

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

		if(this.customerDao.getCustomer(input.getId()) != null) {
			throw new ConflictException(2001, "Customer " + input.getName() + " already registered with id: " + input.getId());
			
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
	public Customer updateCustomerFromJson(Integer id, CustomerJson input, Boolean isAdmin) {

		log.info("Updating customer with id: {}", id);

//		if(this.customerDao.getCustomer(id) != null) { // TODO deve essere gia` registrato per un update?
//		}
		Customer customer = this.getCustomer(id, isAdmin);
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
					throw new ConflictException(2101, "Cannot delete customer: " + id + ", production order already delivered.");
			}
		}
		this.customerDao.delete(c);
	}
}