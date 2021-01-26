package it.mynaproject.spms.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import it.mynaproject.spms.dao.CustomerDao;
import it.mynaproject.spms.domain.Customer;

@Repository
public class CustomerDaoImpl extends BaseDaoImpl implements CustomerDao {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void persist(Customer customer) {

		log.info("Creating new customer: {}", customer.toString());

		em.persist(customer);
		em.flush();
	}

	@Override
	public void update(Customer customer) {

		log.info("Updating customer: {}", customer.toString());

		em.persist(em.merge(customer));
		em.flush();
	}

	@Override
	public void delete(Customer customer) {

		log.info("Deleting customer: {}", customer.toString());

		em.remove(em.merge(customer));
		em.flush();
	}

	@Override
	public Customer getCustomer(Integer id) {

		log.debug("Getting customer with id: {}", id );

		Query q = em.createQuery("FROM Customer WHERE id = :id");
		q.setParameter("id", id);

		try {
			return (Customer) q.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getCustomers() {

		return em.createQuery("FROM Customer").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Customer checkCustomerExists(String name, @Nullable Integer id) {

		Query q = em.createQuery("FROM Customer WHERE name LIKE :name");
		q.setParameter("name", name);

		List<Customer> customers = (List<Customer>) q.getResultList();

		Customer customerExists = null;
		for (Customer r : customers)
			if (((id != null) && (r.getId() != id)) || (id == null))
				customerExists = r;

		return customerExists;
	}
}
