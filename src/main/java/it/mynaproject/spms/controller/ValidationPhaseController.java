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
import it.mynaproject.spms.domain.ValidationPhase;
import it.mynaproject.spms.model.JsonUtil;
import it.mynaproject.spms.model.ValidationPhaseJson;
import it.mynaproject.spms.service.ProductionOrderService;
import it.mynaproject.spms.service.ValidationPhaseService;

@RestController
public class ValidationPhaseController {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ValidationPhaseService validationPhaseService;

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
	@GetMapping(value = "/admin/productionOrders/{id}/validationPhases")
	public List<ValidationPhaseJson> getValidationPhases(@PathVariable("id") Integer id) {

		log.info("Request for validationPhases in ProductionOrder with id {}", id);

		List<ValidationPhaseJson> ret = new ArrayList<>();
		for (ValidationPhase sf : this.productionOrderService.getProductionOrder(id).getValidationPhaseList())
			ret.add(JsonUtil.validationPhaseToValidationPhaseJson(sf, true));

		return ret;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/productionOrders/{id}/validationPhases/{sid}")
	public ValidationPhaseJson getValidationPhase(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		log.info("Request for validationPhase with id {}", sid);

		return JsonUtil.validationPhaseToValidationPhaseJson(this.validationPhaseService.getValidationPhase(id, sid), true);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/admin/productionOrders/{id}/validationPhases")
	public Object postValidationPhase(@PathVariable("id") Integer id, @Valid @RequestBody ValidationPhaseJson input, HttpServletRequest request) {

		return new ResponseEntity<>(JsonUtil.validationPhaseToValidationPhaseJson(this.validationPhaseService.createValidationPhaseFromJson(id, input), true), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/admin/productionOrders/{id}/validationPhases/{sid}")
	public Object updateValidationPhase(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @Valid @RequestBody ValidationPhaseJson input) {

		return new ResponseEntity<>(JsonUtil.validationPhaseToValidationPhaseJson(this.validationPhaseService.updateValidationPhaseFromJson(id, sid, input), true), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/admin/productionOrders/{id}/validationPhases/{sid}")
	public Object deleteValidationPhase(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		this.validationPhaseService.deleteValidationPhaseById(id, sid);

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
	@GetMapping(value = "/resp/productionOrders/{id}/validationPhases")
	public List<ValidationPhaseJson> getValidationPhasesForResp(@PathVariable("id") Integer id) {

		log.info("Request for validationPhases in ProductionOrder with id {}", id);

		List<ValidationPhaseJson> ret = new ArrayList<>();
		for (ValidationPhase sf : this.productionOrderService.getProductionOrder(id).getValidationPhaseList())
			ret.add(JsonUtil.validationPhaseToValidationPhaseJson(sf, true));

		return ret;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/resp/productionOrders/{id}/validationPhases/{sid}")
	public ValidationPhaseJson getValidationPhaseForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		log.info("Request for validationPhase with id {}", sid);

		return JsonUtil.validationPhaseToValidationPhaseJson(this.validationPhaseService.getValidationPhase(id, sid), true);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/resp/productionOrders/{id}/validationPhases")
	public Object postValidationPhaseForResp(@PathVariable("id") Integer id, @Valid @RequestBody ValidationPhaseJson input, HttpServletRequest request) {

		return new ResponseEntity<>(JsonUtil.validationPhaseToValidationPhaseJson(this.validationPhaseService.createValidationPhaseFromJson(id, input), true), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/resp/productionOrders/{id}/validationPhases/{sid}")
	public Object updateValidationPhaseForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @Valid @RequestBody ValidationPhaseJson input) {

		return new ResponseEntity<>(JsonUtil.validationPhaseToValidationPhaseJson(this.validationPhaseService.updateValidationPhaseFromJson(id, sid, input), true), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/resp/productionOrders/{id}/validationPhases/{sid}")
	public Object deleteValidationPhaseForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		this.validationPhaseService.deleteValidationPhaseById(id, sid);

		return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
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
	@GetMapping(value = "/productionOrders/{id}/validationPhases")
	public List<ValidationPhaseJson> getValidationPhasesForUser(@PathVariable("id") Integer id) {

		log.info("Request for validationPhases in ProductionOrder with id {}", id);

		List<ValidationPhaseJson> ret = new ArrayList<>();
		for (ValidationPhase sf : this.productionOrderService.getProductionOrder(id).getValidationPhaseList())
			ret.add(JsonUtil.validationPhaseToValidationPhaseJson(sf, true));

		return ret;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/productionOrders/{id}/validationPhases/{sid}")
	public ValidationPhaseJson getValidationPhaseForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		log.info("Request for validationPhase with id {}", sid);

		return JsonUtil.validationPhaseToValidationPhaseJson(this.validationPhaseService.getValidationPhase(id, sid), true);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/productionOrders/{id}/validationPhases")
	public Object postValidationPhaseForUser(@PathVariable("id") Integer id, @Valid @RequestBody ValidationPhaseJson input, HttpServletRequest request) {

		return new ResponseEntity<>(JsonUtil.validationPhaseToValidationPhaseJson(this.validationPhaseService.createValidationPhaseFromJson(id, input), true), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/productionOrders/{id}/validationPhases/{sid}")
	public Object updateValidationPhaseForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @Valid @RequestBody ValidationPhaseJson input) {

		return new ResponseEntity<>(JsonUtil.validationPhaseToValidationPhaseJson(this.validationPhaseService.updateValidationPhaseFromJson(id, sid, input), true), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/productionOrders/{id}/validationPhases/{sid}")
	public Object deleteValidationPhaseForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		this.validationPhaseService.deleteValidationPhaseById(id, sid);

		return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
	}
}
