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
import it.mynaproject.spms.domain.ProductionOrder;
import it.mynaproject.spms.model.JsonUtil;
import it.mynaproject.spms.model.ProductionOrderJson;
import it.mynaproject.spms.service.ProductionOrderService;

@RestController
public class ProductionOrderController {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProductionOrderService productionOrderService;

	/*
	 *  -------------
	 *  ADMIN SECTION
	 *  -------------
	 *  These routes must be accessible only for ROLE_ADMIN
	 */
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/productionOrders")
	public List<ProductionOrderJson> getProductionOrders() {

		log.info("Request for productionOrders");

		List<ProductionOrderJson> productionOrders = new ArrayList<>();
		for (ProductionOrder productionOrder : this.productionOrderService.getProductionOrders())
			productionOrders.add(JsonUtil.productionOrderToProductionOrderJson(productionOrder, false));

		return productionOrders;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/productionOrders/{id}")
	public ProductionOrderJson getProductionOrder(@PathVariable("id") Integer id) {

		log.info("Request for productionOrder with id {}", id);

		return JsonUtil.productionOrderToProductionOrderJson(this.productionOrderService.getProductionOrder(id), true);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/admin/productionOrders")
	public Object postProductionOrder(@Valid @RequestBody ProductionOrderJson input, HttpServletRequest request) {

		return new ResponseEntity<>(JsonUtil.productionOrderToProductionOrderJson(this.productionOrderService.createProductionOrderFromJson(input), false), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/admin/productionOrders/{id}")
	public Object updateProductionOrder(@PathVariable("id") Integer id, @Valid @RequestBody ProductionOrderJson input) {

		return new ResponseEntity<>(JsonUtil.productionOrderToProductionOrderJson(this.productionOrderService.updateProductionOrderFromJson(id, input), true), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/admin/productionOrders/{id}")
	public Object deleteProductionOrder(@PathVariable("id") Integer id) {

		this.productionOrderService.deleteProductionOrderById(id);

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
	@GetMapping(value = "/resp/productionOrders")
	public List<ProductionOrderJson> getProductionOrdersForResp() {

		log.info("Request for productionOrders");

		List<ProductionOrderJson> productionOrders = new ArrayList<>();
		for (ProductionOrder productionOrder : this.productionOrderService.getProductionOrders())
			productionOrders.add(JsonUtil.productionOrderToProductionOrderJson(productionOrder, false));

		return productionOrders;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/resp/productionOrders/{id}")
	public ProductionOrderJson getProductionOrderForResp(@PathVariable("id") Integer id) {

		log.info("Request for productionOrder with id {}", id);

		return JsonUtil.productionOrderToProductionOrderJson(this.productionOrderService.getProductionOrder(id), true);
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
	@GetMapping(value = "/productionOrders")
	public List<ProductionOrderJson> getProductionOrdersForUser() {

		log.info("Request for productionOrders");

		List<ProductionOrderJson> productionOrders = new ArrayList<>();
		for (ProductionOrder productionOrder : this.productionOrderService.getProductionOrders())
			productionOrders.add(JsonUtil.productionOrderToProductionOrderJson(productionOrder, false));

		return productionOrders;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/productionOrders/{id}")
	public ProductionOrderJson getProductionOrderForUser(@PathVariable("id") Integer id) {

		log.info("Request for productionOrder with id {}", id);

		return JsonUtil.productionOrderToProductionOrderJson(this.productionOrderService.getProductionOrder(id), true);
	}
}
