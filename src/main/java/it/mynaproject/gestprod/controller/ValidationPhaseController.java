package it.mynaproject.gestprod.controller;

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
import it.mynaproject.gestprod.domain.ValidationPhase;
import it.mynaproject.gestprod.model.JsonUtil;
import it.mynaproject.gestprod.model.ValidationPhaseJson;
import it.mynaproject.gestprod.service.ProductionOrderService;
import it.mynaproject.gestprod.service.ValidationPhaseService;

@RestController
public class ValidationPhaseController {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ValidationPhaseService validationPhaseService;
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
	@GetMapping(value = "/admin/productionorders/{id}/validationphases")
	public List<ValidationPhaseJson> getValidationPhases(@PathVariable("id") Integer id) {

		log.info("Request for validationPhases in ProductionOrder with id {}", id);
		
		List<ValidationPhaseJson> ret = new ArrayList<>();
		Boolean isAdmin = true;
		
		for(ValidationPhase sf : this.productionOrderService.getProductionOrder(id, isAdmin).getValidationPhaseList()) {
			ret.add(JsonUtil.validationPhaseToValidationPhaseJson(sf));
		}
		return ret;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/productionorders/{id}/validationphases/{sid}")
	public ValidationPhaseJson getValidationPhase(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		log.info("Request for validationPhase with id {}", sid);
		Boolean isAdmin = true;
		
		return JsonUtil.validationPhaseToValidationPhaseJson(this.validationPhaseService.getValidationPhase(id, sid, isAdmin));
	}
	
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/admin/productionorders/{id}/validationphases")
	public Object postValidationPhase(@PathVariable("id") Integer id, @Valid @RequestBody ValidationPhaseJson input, HttpServletRequest request) {

		ValidationPhase validationPhase = this.validationPhaseService.createValidationPhaseFromJson(id, input);

		return new ResponseEntity<>(JsonUtil.validationPhaseToValidationPhaseJson(validationPhase), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/admin/productionorders/{id}/validationphases/{sid}")
	public Object updateValidationPhase(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @Valid @RequestBody ValidationPhaseJson input) {

		ValidationPhase validationPhase = this.validationPhaseService.updateValidationPhaseFromJson(id, sid, input, true);

		return new ResponseEntity<>(JsonUtil.validationPhaseToValidationPhaseJson(validationPhase), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/admin/productionorders/{id}/validationphases/{sid}")
	public Object deleteValidationPhase(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		this.validationPhaseService.deleteValidationPhaseById(sid); // TODO modify the associated ProductionOrder?

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
	@GetMapping(value = "/resp/productionorders/{id}/validationphases")
	public List<ValidationPhaseJson> getValidationPhasesForResp(@PathVariable("id") Integer id) {

		log.info("Request for validationPhases in ProductionOrder with id {}", id);
		
		List<ValidationPhaseJson> ret = new ArrayList<>();
		Boolean isAdmin = false;
		
		for(ValidationPhase sf : this.productionOrderService.getProductionOrder(id, isAdmin).getValidationPhaseList()) {
			ret.add(JsonUtil.validationPhaseToValidationPhaseJson(sf));
		}
		return ret;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/resp/productionorders/{id}/validationphases/{sid}")
	public ValidationPhaseJson getValidationPhaseForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		log.info("Request for validationPhase with id {}", sid);
		Boolean isAdmin = false;
		
		return JsonUtil.validationPhaseToValidationPhaseJson(this.validationPhaseService.getValidationPhase(id, sid, isAdmin));
	}
	
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/resp/productionorders/{id}/validationphases")
	public Object postValidationPhaseForResp(@PathVariable("id") Integer id, @Valid @RequestBody ValidationPhaseJson input, HttpServletRequest request) {

		ValidationPhase validationPhase = this.validationPhaseService.createValidationPhaseFromJson(id, input);

		return new ResponseEntity<>(JsonUtil.validationPhaseToValidationPhaseJson(validationPhase), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/resp/productionorders/{id}/validationphases/{sid}")
	public Object updateValidationPhaseForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @Valid @RequestBody ValidationPhaseJson input) {

		Boolean isAdmin = false;
		ValidationPhase validationPhase = this.validationPhaseService.updateValidationPhaseFromJson(id, sid, input, isAdmin);

		return new ResponseEntity<>(JsonUtil.validationPhaseToValidationPhaseJson(validationPhase), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/resp/productionorders/{id}/validationphases/{sid}")
	public Object deleteValidationPhaseForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		this.validationPhaseService.deleteValidationPhaseById(sid); // TODO modify the associated ProductionOrder?

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
	@GetMapping(value = "/productionorders/{id}/validationphases")
	public List<ValidationPhaseJson> getValidationPhasesForUser(@PathVariable("id") Integer id) {

		log.info("Request for validationPhases in ProductionOrder with id {}", id);
		
		List<ValidationPhaseJson> ret = new ArrayList<>();
		Boolean isAdmin = false;
		
		for(ValidationPhase sf : this.productionOrderService.getProductionOrder(id, isAdmin).getValidationPhaseList()) {
			ret.add(JsonUtil.validationPhaseToValidationPhaseJson(sf));
		}
		return ret;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/productionorders/{id}/validationphases/{sid}")
	public ValidationPhaseJson getValidationPhaseForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		log.info("Request for validationPhase with id {}", sid);
		Boolean isAdmin = false;
		
		return JsonUtil.validationPhaseToValidationPhaseJson(this.validationPhaseService.getValidationPhase(id, sid, isAdmin));
	}
	
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/productionorders/{id}/validationphases")
	public Object postValidationPhaseForUser(@PathVariable("id") Integer id, @Valid @RequestBody ValidationPhaseJson input, HttpServletRequest request) {

		ValidationPhase validationPhase = this.validationPhaseService.createValidationPhaseFromJson(id, input);

		return new ResponseEntity<>(JsonUtil.validationPhaseToValidationPhaseJson(validationPhase), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/productionorders/{id}/validationphases/{sid}")
	public Object updateValidationPhaseForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @Valid @RequestBody ValidationPhaseJson input) {

		Boolean isAdmin = false;
		ValidationPhase validationPhase = this.validationPhaseService.updateValidationPhaseFromJson(id, sid, input, isAdmin);

		return new ResponseEntity<>(JsonUtil.validationPhaseToValidationPhaseJson(validationPhase), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/productionorders/{id}/validationphases/{sid}")
	public Object deleteValidationPhaseForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		this.validationPhaseService.deleteValidationPhaseById(sid); // TODO modify the associated ProductionOrder?

		return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
	}
	
}
