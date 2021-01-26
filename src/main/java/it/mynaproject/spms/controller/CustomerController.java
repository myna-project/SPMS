package it.mynaproject.spms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.mynaproject.spms.domain.Customer;
import it.mynaproject.spms.model.CustomerJson;
import it.mynaproject.spms.model.JsonUtil;
import it.mynaproject.spms.service.CustomerService;

@RestController
public class CustomerController {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CustomerService customerService;

	/*
	 *  -------------
	 *  ADMIN SECTION
	 *  -------------
	 *  These routes must be accessible only for ROLE_ADMIN
	 */
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/customers")
	public List<CustomerJson> getCustomers() {

		log.info("Request for customers");

		List<CustomerJson> customers = new ArrayList<>();

		for (Customer customer : this.customerService.getCustomers())
			customers.add(JsonUtil.customerToCustomerJson(customer));

		return customers;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/customers/{id}")
	public CustomerJson getCustomer(@PathVariable("id") Integer id) {

		log.info("Request for customer with id {}", id);

		return JsonUtil.customerToCustomerJson(this.customerService.getCustomer(id));
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/admin/customers")
	public Object postCustomer(@Valid @RequestBody CustomerJson input, HttpServletRequest request) {

		Customer customer = this.customerService.createCustomerFromJson(input);

		return new ResponseEntity<>(JsonUtil.customerToCustomerJson(customer), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/admin/customers/{id}")
	public Object updateCustomer(@PathVariable("id") Integer id, @Valid @RequestBody CustomerJson input) {

		Customer customer = this.customerService.updateCustomerFromJson(id, input);

		return new ResponseEntity<>(JsonUtil.customerToCustomerJson(customer), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/admin/customers/{id}")
	public Object deleteCustomer(@PathVariable("id") Integer id) {

		this.customerService.deleteCustomerById(id);

		return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
	}
	
	/*
	 *  -------------
	 *  RESP SECTION
	 *  -------------
	 *  These routes must be accessible only for ROLE_RESP
	 */
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/resp/customers")
	public List<CustomerJson> getCustomersForResp() {

		log.info("Request for customers");

		List<CustomerJson> customers = new ArrayList<>();

		for (Customer customer : this.customerService.getCustomers())
			customers.add(JsonUtil.customerToCustomerJson(customer));

		return customers;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/resp/customers/{id}")
	public CustomerJson getCustomerForResp(@PathVariable("id") Integer id) {

		log.info("Request for customer with id {}", id);

		return JsonUtil.customerToCustomerJson(this.customerService.getCustomer(id));
	}

	/*
	 *  -------------
	 *  USER SECTION
	 *  -------------
	 *  These routes must be accessible only for ROLE_USER
	 */
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/customers")
	public List<CustomerJson> getCustomersForUser() {

		log.info("Request for customers");

		List<CustomerJson> customers = new ArrayList<>();

		for (Customer customer : this.customerService.getCustomers())
			customers.add(JsonUtil.customerToCustomerJson(customer));

		return customers;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/customers/{id}")
	public CustomerJson getCustomerForUser(@PathVariable("id") Integer id) {

		log.info("Request for customer with id {}", id);

		return JsonUtil.customerToCustomerJson(this.customerService.getCustomer(id));
	}
}
