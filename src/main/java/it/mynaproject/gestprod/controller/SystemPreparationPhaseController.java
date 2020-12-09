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
import it.mynaproject.gestprod.domain.SystemPreparationPhase;
import it.mynaproject.gestprod.model.JsonUtil;
import it.mynaproject.gestprod.model.SystemPreparationPhaseJson;
import it.mynaproject.gestprod.service.ProductionOrderService;
import it.mynaproject.gestprod.service.SystemPreparationPhaseService;

@RestController
public class SystemPreparationPhaseController {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	private SystemPreparationPhaseService systemPreparationPhaseService;
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
	@GetMapping(value = "/admin/productionOrders/{id}/systemPreparationPhases")
	public List<SystemPreparationPhaseJson> getSystemPreparationPhases(@PathVariable("id") Integer id) {

		log.info("Request for systemPreparationPhases in ProductionOrder with id {}", id);
		
		List<SystemPreparationPhaseJson> ret = new ArrayList<>();
		
		for(SystemPreparationPhase sf : this.productionOrderService.getProductionOrder(id).getSystemPreparationPhaseList()) {
			ret.add(JsonUtil.systemPreparationPhaseToSystemPreparationPhaseJson(sf));
		}
		return ret;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/productionOrders/{id}/systemPreparationPhases/{sid}")
	public SystemPreparationPhaseJson getSystemPreparationPhase(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		log.info("Request for systemPreparationPhase with id {}", sid);
		
		return JsonUtil.systemPreparationPhaseToSystemPreparationPhaseJson(this.systemPreparationPhaseService.getSystemPreparationPhase(id, sid));
	}
	
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/admin/productionOrders/{id}/systemPreparationPhases")
	public Object postSystemPreparationPhase(@PathVariable("id") Integer id, @Valid @RequestBody SystemPreparationPhaseJson input, HttpServletRequest request) {

		SystemPreparationPhase systemPreparationPhase = this.systemPreparationPhaseService.createSystemPreparationPhaseFromJson(id, input);

		return new ResponseEntity<>(JsonUtil.systemPreparationPhaseToSystemPreparationPhaseJson(systemPreparationPhase), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/admin/productionOrders/{id}/systemPreparationPhases/{sid}")
	public Object updateSystemPreparationPhase(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @Valid @RequestBody SystemPreparationPhaseJson input) {

		SystemPreparationPhase systemPreparationPhase = this.systemPreparationPhaseService.updateSystemPreparationPhaseFromJson(id, sid, input);

		return new ResponseEntity<>(JsonUtil.systemPreparationPhaseToSystemPreparationPhaseJson(systemPreparationPhase), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/admin/productionOrders/{id}/systemPreparationPhases/{sid}")
	public Object deleteSystemPreparationPhase(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		this.systemPreparationPhaseService.deleteSystemPreparationPhaseById(sid);

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
	@GetMapping(value = "/resp/productionOrders/{id}/systemPreparationPhases")
	public List<SystemPreparationPhaseJson> getSystemPreparationPhasesForResp(@PathVariable("id") Integer id) {

		log.info("Request for systemPreparationPhases in ProductionOrder with id {}", id);
		
		List<SystemPreparationPhaseJson> ret = new ArrayList<>();
		
		for(SystemPreparationPhase sf : this.productionOrderService.getProductionOrder(id).getSystemPreparationPhaseList()) {
			ret.add(JsonUtil.systemPreparationPhaseToSystemPreparationPhaseJson(sf));
		}
		return ret;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/resp/productionOrders/{id}/systemPreparationPhases/{sid}")
	public SystemPreparationPhaseJson getSystemPreparationPhaseForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		log.info("Request for systemPreparationPhase with id {}", sid);
		
		return JsonUtil.systemPreparationPhaseToSystemPreparationPhaseJson(this.systemPreparationPhaseService.getSystemPreparationPhase(id, sid));
	}
	
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/resp/productionOrders/{id}/systemPreparationPhases")
	public Object postSystemPreparationPhaseForResp(@PathVariable("id") Integer id, @Valid @RequestBody SystemPreparationPhaseJson input, HttpServletRequest request) {

		SystemPreparationPhase systemPreparationPhase = this.systemPreparationPhaseService.createSystemPreparationPhaseFromJson(id, input);

		return new ResponseEntity<>(JsonUtil.systemPreparationPhaseToSystemPreparationPhaseJson(systemPreparationPhase), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/resp/productionOrders/{id}/systemPreparationPhases/{sid}")
	public Object updateSystemPreparationPhaseForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @Valid @RequestBody SystemPreparationPhaseJson input) {

		SystemPreparationPhase systemPreparationPhase = this.systemPreparationPhaseService.updateSystemPreparationPhaseFromJson(id, sid, input);

		return new ResponseEntity<>(JsonUtil.systemPreparationPhaseToSystemPreparationPhaseJson(systemPreparationPhase), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/resp/productionOrders/{id}/systemPreparationPhases/{sid}")
	public Object deleteSystemPreparationPhaseForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		this.systemPreparationPhaseService.deleteSystemPreparationPhaseById(sid);

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
	@GetMapping(value = "/productionOrders/{id}/systemPreparationPhases")
	public List<SystemPreparationPhaseJson> getSystemPreparationPhasesForUser(@PathVariable("id") Integer id) {

		log.info("Request for systemPreparationPhases in ProductionOrder with id {}", id);
		
		List<SystemPreparationPhaseJson> ret = new ArrayList<>();
		
		for(SystemPreparationPhase sf : this.productionOrderService.getProductionOrder(id).getSystemPreparationPhaseList()) {
			ret.add(JsonUtil.systemPreparationPhaseToSystemPreparationPhaseJson(sf));
		}
		return ret;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/productionOrders/{id}/systemPreparationPhases/{sid}")
	public SystemPreparationPhaseJson getSystemPreparationPhaseForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		log.info("Request for systemPreparationPhase with id {}", sid);
		
		return JsonUtil.systemPreparationPhaseToSystemPreparationPhaseJson(this.systemPreparationPhaseService.getSystemPreparationPhase(id, sid));
	}
	
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/productionOrders/{id}/systemPreparationPhases")
	public Object postSystemPreparationPhaseForUser(@PathVariable("id") Integer id, @Valid @RequestBody SystemPreparationPhaseJson input, HttpServletRequest request) {

		SystemPreparationPhase systemPreparationPhase = this.systemPreparationPhaseService.createSystemPreparationPhaseFromJson(id, input);

		return new ResponseEntity<>(JsonUtil.systemPreparationPhaseToSystemPreparationPhaseJson(systemPreparationPhase), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/productionOrders/{id}/systemPreparationPhases/{sid}")
	public Object updateSystemPreparationPhaseForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @Valid @RequestBody SystemPreparationPhaseJson input) {

		SystemPreparationPhase systemPreparationPhase = this.systemPreparationPhaseService.updateSystemPreparationPhaseFromJson(id, sid, input);

		return new ResponseEntity<>(JsonUtil.systemPreparationPhaseToSystemPreparationPhaseJson(systemPreparationPhase), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/productionOrders/{id}/systemPreparationPhases/{sid}")
	public Object deleteSystemPreparationPhaseForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		this.systemPreparationPhaseService.deleteSystemPreparationPhaseById(sid);

		return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
	}
	
}
