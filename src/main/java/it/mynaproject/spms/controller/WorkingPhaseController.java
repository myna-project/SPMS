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
import it.mynaproject.spms.domain.WorkingPhase;
import it.mynaproject.spms.model.JsonUtil;
import it.mynaproject.spms.model.WorkingPhaseJson;
import it.mynaproject.spms.service.ProductionOrderService;
import it.mynaproject.spms.service.WorkingPhaseService;

@RestController
public class WorkingPhaseController {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WorkingPhaseService workingPhaseService;

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
	@GetMapping(value = "/admin/productionOrders/{id}/workingPhases")
	public List<WorkingPhaseJson> getWorkingPhases(@PathVariable("id") Integer id) {

		log.info("Request for workingPhases in ProductionOrder with id {}", id);

		List<WorkingPhaseJson> ret = new ArrayList<>();
		for (WorkingPhase sf : this.productionOrderService.getProductionOrder(id).getWorkingPhaseList()) {
			ret.add(JsonUtil.workingPhaseToWorkingPhaseJson(sf, true, true));
		}

		return ret;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/productionOrders/{id}/workingPhases/{sid}")
	public WorkingPhaseJson getWorkingPhase(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		log.info("Request for workingPhase with id {}", sid);

		return JsonUtil.workingPhaseToWorkingPhaseJson(this.workingPhaseService.getWorkingPhase(id, sid), true, true);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/admin/productionOrders/{id}/workingPhases")
	public Object postWorkingPhase(@PathVariable("id") Integer id, @Valid @RequestBody WorkingPhaseJson input, HttpServletRequest request) {

		return new ResponseEntity<>(JsonUtil.workingPhaseToWorkingPhaseJson(this.workingPhaseService.createWorkingPhaseFromJson(id, input), true, true), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/admin/productionOrders/{id}/workingPhases/{sid}")
	public Object updateWorkingPhase(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @Valid @RequestBody WorkingPhaseJson input) {

		return new ResponseEntity<>(JsonUtil.workingPhaseToWorkingPhaseJson(this.workingPhaseService.updateWorkingPhaseFromJson(id, sid, input), true, true), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/admin/productionOrders/{id}/workingPhases/{sid}")
	public Object deleteWorkingPhase(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		this.workingPhaseService.deleteWorkingPhaseById(id, sid);

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
	@GetMapping(value = "/resp/productionOrders/{id}/workingPhases")
	public List<WorkingPhaseJson> getWorkingPhasesForResp(@PathVariable("id") Integer id) {

		log.info("Request for workingPhases in ProductionOrder with id {}", id);

		List<WorkingPhaseJson> ret = new ArrayList<>();
		for (WorkingPhase sf : this.productionOrderService.getProductionOrder(id).getWorkingPhaseList()) {
			ret.add(JsonUtil.workingPhaseToWorkingPhaseJson(sf, true, true));
		}

		return ret;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/resp/productionOrders/{id}/workingPhases/{sid}")
	public WorkingPhaseJson getWorkingPhaseForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		log.info("Request for workingPhase with id {}", sid);

		return JsonUtil.workingPhaseToWorkingPhaseJson(this.workingPhaseService.getWorkingPhase(id, sid), true, true);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/resp/productionOrders/{id}/workingPhases")
	public Object postWorkingPhaseForResp(@PathVariable("id") Integer id, @Valid @RequestBody WorkingPhaseJson input, HttpServletRequest request) {

		return new ResponseEntity<>(JsonUtil.workingPhaseToWorkingPhaseJson(this.workingPhaseService.createWorkingPhaseFromJson(id, input), true, true), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/resp/productionOrders/{id}/workingPhases/{sid}")
	public Object updateWorkingPhaseForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @Valid @RequestBody WorkingPhaseJson input) {

		return new ResponseEntity<>(JsonUtil.workingPhaseToWorkingPhaseJson(this.workingPhaseService.updateWorkingPhaseFromJson(id, sid, input), true, true), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/resp/productionOrders/{id}/workingPhases/{sid}")
	public Object deleteWorkingPhaseForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		this.workingPhaseService.deleteWorkingPhaseById(id, sid);

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
	@GetMapping(value = "/productionOrders/{id}/workingPhases")
	public List<WorkingPhaseJson> getWorkingPhasesForUser(@PathVariable("id") Integer id) {

		log.info("Request for workingPhases in ProductionOrder with id {}", id);
		
		List<WorkingPhaseJson> ret = new ArrayList<>();
		for (WorkingPhase sf : this.productionOrderService.getProductionOrder(id).getWorkingPhaseList()) {
			ret.add(JsonUtil.workingPhaseToWorkingPhaseJson(sf, true, true));
		}

		return ret;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/productionOrders/{id}/workingPhases/{sid}")
	public WorkingPhaseJson getWorkingPhaseForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		log.info("Request for workingPhase with id {}", sid);

		return JsonUtil.workingPhaseToWorkingPhaseJson(this.workingPhaseService.getWorkingPhase(id, sid), true, true);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/productionOrders/{id}/workingPhases")
	public Object postWorkingPhaseForUser(@PathVariable("id") Integer id, @Valid @RequestBody WorkingPhaseJson input, HttpServletRequest request) {

		return new ResponseEntity<>(JsonUtil.workingPhaseToWorkingPhaseJson(this.workingPhaseService.createWorkingPhaseFromJson(id, input), true, true), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/productionOrders/{id}/workingPhases/{sid}")
	public Object updateWorkingPhaseForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @Valid @RequestBody WorkingPhaseJson input) {

		return new ResponseEntity<>(JsonUtil.workingPhaseToWorkingPhaseJson(this.workingPhaseService.updateWorkingPhaseFromJson(id, sid, input), true, true), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/productionOrders/{id}/workingPhases/{sid}")
	public Object deleteWorkingPhaseForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		this.workingPhaseService.deleteWorkingPhaseById(id, sid);

		return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
	}
}