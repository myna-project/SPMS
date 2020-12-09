package it.mynaproject.gestprod.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import it.mynaproject.gestprod.domain.WorkingPhaseMeasure;
import it.mynaproject.gestprod.model.JsonUtil;
import it.mynaproject.gestprod.model.WorkingPhaseMeasureJson;
import it.mynaproject.gestprod.service.WorkingPhaseMeasureService;

@RestController
public class WorkingPhaseMeasureController {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	private WorkingPhaseMeasureService workingPhaseMeasureService;
	
	/*
	 *  -------------
	 *  ADMIN SECTION
	 *  -------------
	 *  These routes must be accessible only for ROLE_ADMIN
	 */
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/productionOrders/{id}/workingPhases/{sid}/measures")
	public List<WorkingPhaseMeasureJson> getWorkingPhaseMeasures(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		log.info("Request for workingPhaseMeasures in ProductionOrder with id {}", id);
		
		List<WorkingPhaseMeasureJson> ret = new ArrayList<>();
		
		for(WorkingPhaseMeasure sf : this.workingPhaseMeasureService.getWorkingPhaseMeasures(id, sid)) {
			ret.add(JsonUtil.workingPhaseMeasureToWorkingPhaseMeasureJson(sf));
		}
		return ret;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/productionOrders/{id}/workingPhases/{sid}/measures/{tid}")
	public WorkingPhaseMeasureJson getWorkingPhaseMeasure(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @PathVariable("tid") Integer tid) {

		log.info("Request for workingPhaseMeasure with id {}", sid);
		
		return JsonUtil.workingPhaseMeasureToWorkingPhaseMeasureJson(this.workingPhaseMeasureService.getWorkingPhaseMeasure(id, sid, tid));
	}
	
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/admin/productionOrders/{id}/workingPhases/{sid}/measures")
	public Object postWorkingPhaseMeasure(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @Valid @RequestBody WorkingPhaseMeasureJson input, HttpServletRequest request) {

		WorkingPhaseMeasure workingPhaseMeasure = this.workingPhaseMeasureService.createWorkingPhaseMeasureFromJson(id, sid, input);

		return new ResponseEntity<>(JsonUtil.workingPhaseMeasureToWorkingPhaseMeasureJson(workingPhaseMeasure), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/admin/productionOrders/{id}/workingPhases/{sid}/measures/{tid}")
	public Object updateWorkingPhaseMeasure(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @PathVariable("tid") Integer tid, @Valid @RequestBody WorkingPhaseMeasureJson input) {

		WorkingPhaseMeasure workingPhaseMeasure = this.workingPhaseMeasureService.updateWorkingPhaseMeasureFromJson(id, sid, tid, input);

		return new ResponseEntity<>(JsonUtil.workingPhaseMeasureToWorkingPhaseMeasureJson(workingPhaseMeasure), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/admin/productionOrders/{id}/workingPhases/{sid}/measures/{tid}")
	public Object deleteWorkingPhaseMeasure(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @PathVariable("tid") Integer tid) {

		this.workingPhaseMeasureService.deleteWorkingPhaseMeasureById(tid); 

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
	@GetMapping(value = "/resp/productionOrders/{id}/workingPhases/{sid}/measures")
	public List<WorkingPhaseMeasureJson> getWorkingPhaseMeasuresForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		log.info("Request for workingPhaseMeasures in ProductionOrder with id {}", id);
		
		List<WorkingPhaseMeasureJson> ret = new ArrayList<>();
		
		for(WorkingPhaseMeasure sf : this.workingPhaseMeasureService.getWorkingPhaseMeasures(id, sid)) {
			ret.add(JsonUtil.workingPhaseMeasureToWorkingPhaseMeasureJson(sf));
		}
		return ret;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/resp/productionOrders/{id}/workingPhases/{sid}/measures/{tid}")
	public WorkingPhaseMeasureJson getWorkingPhaseMeasureForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @PathVariable("tid") Integer tid) {

		log.info("Request for workingPhaseMeasure with id {}", sid);
		
		return JsonUtil.workingPhaseMeasureToWorkingPhaseMeasureJson(this.workingPhaseMeasureService.getWorkingPhaseMeasure(id, sid, tid));
	}
	
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/resp/productionOrders/{id}/workingPhases/{sid}/measures")
	public Object postWorkingPhaseMeasureForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @Valid @RequestBody WorkingPhaseMeasureJson input, HttpServletRequest request) {

		WorkingPhaseMeasure workingPhaseMeasure = this.workingPhaseMeasureService.createWorkingPhaseMeasureFromJson(id, sid, input);

		return new ResponseEntity<>(JsonUtil.workingPhaseMeasureToWorkingPhaseMeasureJson(workingPhaseMeasure), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/resp/productionOrders/{id}/workingPhases/{sid}/measures/{tid}")
	public Object updateWorkingPhaseMeasureForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @PathVariable("tid") Integer tid, @Valid @RequestBody WorkingPhaseMeasureJson input) {

		WorkingPhaseMeasure workingPhaseMeasure = this.workingPhaseMeasureService.updateWorkingPhaseMeasureFromJson(id, sid, tid, input);

		return new ResponseEntity<>(JsonUtil.workingPhaseMeasureToWorkingPhaseMeasureJson(workingPhaseMeasure), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/resp/productionOrders/{id}/workingPhases/{sid}/measures/{tid}")
	public Object deleteWorkingPhaseMeasureForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @PathVariable("tid") Integer tid) {

		this.workingPhaseMeasureService.deleteWorkingPhaseMeasureById(tid); 

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
	@GetMapping(value = "/productionOrders/{id}/workingPhases/{sid}/measures")
	public List<WorkingPhaseMeasureJson> getWorkingPhaseMeasuresForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		log.info("Request for workingPhaseMeasures in ProductionOrder with id {}", id);
		
		List<WorkingPhaseMeasureJson> ret = new ArrayList<>();
		
		for(WorkingPhaseMeasure sf : this.workingPhaseMeasureService.getWorkingPhaseMeasures(id, sid)) {
			ret.add(JsonUtil.workingPhaseMeasureToWorkingPhaseMeasureJson(sf));
		}
		return ret;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/productionOrders/{id}/workingPhases/{sid}/measures/{tid}")
	public WorkingPhaseMeasureJson getWorkingPhaseMeasureForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @PathVariable("tid") Integer tid) {

		log.info("Request for workingPhaseMeasure with id {}", sid);
		
		return JsonUtil.workingPhaseMeasureToWorkingPhaseMeasureJson(this.workingPhaseMeasureService.getWorkingPhaseMeasure(id, sid, tid));
	}
	
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/productionOrders/{id}/workingPhases/{sid}/measures")
	public Object postWorkingPhaseMeasureForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @Valid @RequestBody WorkingPhaseMeasureJson input, HttpServletRequest request) {

		WorkingPhaseMeasure workingPhaseMeasure = this.workingPhaseMeasureService.createWorkingPhaseMeasureFromJson(id, sid, input);

		return new ResponseEntity<>(JsonUtil.workingPhaseMeasureToWorkingPhaseMeasureJson(workingPhaseMeasure), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/productionOrders/{id}/workingPhases/{sid}/measures/{tid}")
	public Object updateWorkingPhaseMeasureForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @PathVariable("tid") Integer tid, @Valid @RequestBody WorkingPhaseMeasureJson input) {

		WorkingPhaseMeasure workingPhaseMeasure = this.workingPhaseMeasureService.updateWorkingPhaseMeasureFromJson(id, sid, tid, input);

		return new ResponseEntity<>(JsonUtil.workingPhaseMeasureToWorkingPhaseMeasureJson(workingPhaseMeasure), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/productionOrders/{id}/workingPhases/{sid}/measures/{tid}")
	public Object deleteWorkingPhaseMeasureForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @PathVariable("tid") Integer tid) {

		this.workingPhaseMeasureService.deleteWorkingPhaseMeasureById(tid);

		return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
	}
	
}
