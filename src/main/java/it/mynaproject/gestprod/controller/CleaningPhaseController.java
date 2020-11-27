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
import it.mynaproject.gestprod.domain.CleaningPhase;
import it.mynaproject.gestprod.model.JsonUtil;
import it.mynaproject.gestprod.model.CleaningPhaseJson;
import it.mynaproject.gestprod.service.ProductionOrderService;
import it.mynaproject.gestprod.service.CleaningPhaseService;

@RestController
public class CleaningPhaseController {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	private CleaningPhaseService cleaningPhaseService;
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
	@GetMapping(value = "/admin/productionorders/{id}/cleaningphases")
	public List<CleaningPhaseJson> getCleaningPhases(@PathVariable("id") Integer id) {

		log.info("Request for cleaningPhases in ProductionOrder with id {}", id);
		
		List<CleaningPhaseJson> ret = new ArrayList<>();
		Boolean isAdmin = true;
		
		for(CleaningPhase sf : this.productionOrderService.getProductionOrder(id, isAdmin).getCleaningPhaseList()) {
			ret.add(JsonUtil.cleaningPhaseToCleaningPhaseJson(sf));
		}
		return ret;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/productionorders/{id}/cleaningphases/{sid}")
	public CleaningPhaseJson getCleaningPhase(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		log.info("Request for cleaningPhase with id {}", sid);
		Boolean isAdmin = true;
		
		return JsonUtil.cleaningPhaseToCleaningPhaseJson(this.cleaningPhaseService.getCleaningPhase(id, sid, isAdmin));
	}
	
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/admin/productionorders/{id}/cleaningphases")
	public Object postCleaningPhase(@PathVariable("id") Integer id, @Valid @RequestBody CleaningPhaseJson input, HttpServletRequest request) {

		CleaningPhase cleaningPhase = this.cleaningPhaseService.createCleaningPhaseFromJson(id, input);

		return new ResponseEntity<>(JsonUtil.cleaningPhaseToCleaningPhaseJson(cleaningPhase), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/admin/productionorders/{id}/cleaningphases/{sid}")
	public Object updateCleaningPhase(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @Valid @RequestBody CleaningPhaseJson input) {

		CleaningPhase cleaningPhase = this.cleaningPhaseService.updateCleaningPhaseFromJson(id, sid, input, true);

		return new ResponseEntity<>(JsonUtil.cleaningPhaseToCleaningPhaseJson(cleaningPhase), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/admin/productionorders/{id}/cleaningphases/{sid}")
	public Object deleteCleaningPhase(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		this.cleaningPhaseService.deleteCleaningPhaseById(sid); // TODO modify the associated ProductionOrder?

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
	@GetMapping(value = "/resp/productionorders/{id}/cleaningphases")
	public List<CleaningPhaseJson> getCleaningPhasesForResp(@PathVariable("id") Integer id) {

		log.info("Request for cleaningPhases in ProductionOrder with id {}", id);
		
		List<CleaningPhaseJson> ret = new ArrayList<>();
		Boolean isAdmin = false;
		
		for(CleaningPhase sf : this.productionOrderService.getProductionOrder(id, isAdmin).getCleaningPhaseList()) {
			ret.add(JsonUtil.cleaningPhaseToCleaningPhaseJson(sf));
		}
		return ret;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/resp/productionorders/{id}/cleaningphases/{sid}")
	public CleaningPhaseJson getCleaningPhaseForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		log.info("Request for cleaningPhase with id {}", sid);
		Boolean isAdmin = false;
		
		return JsonUtil.cleaningPhaseToCleaningPhaseJson(this.cleaningPhaseService.getCleaningPhase(id, sid, isAdmin));
	}
	
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/resp/productionorders/{id}/cleaningphases")
	public Object postCleaningPhaseForResp(@PathVariable("id") Integer id, @Valid @RequestBody CleaningPhaseJson input, HttpServletRequest request) {

		CleaningPhase cleaningPhase = this.cleaningPhaseService.createCleaningPhaseFromJson(id, input);

		return new ResponseEntity<>(JsonUtil.cleaningPhaseToCleaningPhaseJson(cleaningPhase), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/resp/productionorders/{id}/cleaningphases/{sid}")
	public Object updateCleaningPhaseForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @Valid @RequestBody CleaningPhaseJson input) {

		Boolean isAdmin = false;
		CleaningPhase cleaningPhase = this.cleaningPhaseService.updateCleaningPhaseFromJson(id, sid, input, isAdmin);

		return new ResponseEntity<>(JsonUtil.cleaningPhaseToCleaningPhaseJson(cleaningPhase), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/resp/productionorders/{id}/cleaningphases/{sid}")
	public Object deleteCleaningPhaseForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		this.cleaningPhaseService.deleteCleaningPhaseById(sid); // TODO modify the associated ProductionOrder?

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
	@GetMapping(value = "/productionorders/{id}/cleaningphases")
	public List<CleaningPhaseJson> getCleaningPhasesForUser(@PathVariable("id") Integer id) {

		log.info("Request for cleaningPhases in ProductionOrder with id {}", id);
		
		List<CleaningPhaseJson> ret = new ArrayList<>();
		Boolean isAdmin = false;
		
		for(CleaningPhase sf : this.productionOrderService.getProductionOrder(id, isAdmin).getCleaningPhaseList()) {
			ret.add(JsonUtil.cleaningPhaseToCleaningPhaseJson(sf));
		}
		return ret;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/productionorders/{id}/cleaningphases/{sid}")
	public CleaningPhaseJson getCleaningPhaseForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		log.info("Request for cleaningPhase with id {}", sid);
		Boolean isAdmin = false;
		
		return JsonUtil.cleaningPhaseToCleaningPhaseJson(this.cleaningPhaseService.getCleaningPhase(id, sid, isAdmin));
	}
	
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/productionorders/{id}/cleaningphases")
	public Object postCleaningPhaseForUser(@PathVariable("id") Integer id, @Valid @RequestBody CleaningPhaseJson input, HttpServletRequest request) {

		CleaningPhase cleaningPhase = this.cleaningPhaseService.createCleaningPhaseFromJson(id, input);

		return new ResponseEntity<>(JsonUtil.cleaningPhaseToCleaningPhaseJson(cleaningPhase), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/productionorders/{id}/cleaningphases/{sid}")
	public Object updateCleaningPhaseForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @Valid @RequestBody CleaningPhaseJson input) {

		Boolean isAdmin = false;
		CleaningPhase cleaningPhase = this.cleaningPhaseService.updateCleaningPhaseFromJson(id, sid, input, isAdmin);

		return new ResponseEntity<>(JsonUtil.cleaningPhaseToCleaningPhaseJson(cleaningPhase), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/productionorders/{id}/cleaningphases/{sid}")
	public Object deleteCleaningPhaseForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		this.cleaningPhaseService.deleteCleaningPhaseById(sid); // TODO modify the associated ProductionOrder?

		return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
	}
	
}