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
import it.mynaproject.gestprod.domain.WorkingPhaseUser;
import it.mynaproject.gestprod.model.JsonUtil;
import it.mynaproject.gestprod.model.WorkingPhaseUserJson;
import it.mynaproject.gestprod.service.ProductionOrderService;
import it.mynaproject.gestprod.service.WorkingPhaseUserService;

@RestController
public class WorkingPhaseUserController {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	private WorkingPhaseUserService workingPhaseUserService;
	
	/*
	 *  -------------
	 *  ADMIN SECTION
	 *  -------------
	 *  These routes must be accessible only for ROLE_ADMIN
	 */
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/productionorders/{id}/workingphases/{sid}/shifts")
	public List<WorkingPhaseUserJson> getWorkingPhaseUsers(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		log.info("Request for workingPhaseUsers in ProductionOrder with id {}", id);
		
		List<WorkingPhaseUserJson> ret = new ArrayList<>();
		Boolean isAdmin = true;
		
		for(WorkingPhaseUser sf : this.workingPhaseUserService.getWorkingPhaseUsers(id, sid, isAdmin)) {
			ret.add(JsonUtil.workingPhaseUserToWorkingPhaseUserJson(sf));
		}
		return ret;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/productionorders/{id}/workingphases/{sid}/shifts/{tid}")
	public WorkingPhaseUserJson getWorkingPhaseUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @PathVariable("tid") Integer tid) {

		log.info("Request for workingPhaseUser with id {}", sid);
		Boolean isAdmin = true;
		
		return JsonUtil.workingPhaseUserToWorkingPhaseUserJson(this.workingPhaseUserService.getWorkingPhaseUser(id, sid, tid, isAdmin));
	}
	
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/admin/productionorders/{id}/workingphases/{sid}/shifts")
	public Object postWorkingPhaseUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @Valid @RequestBody WorkingPhaseUserJson input, HttpServletRequest request) {

		WorkingPhaseUser workingPhaseUser = this.workingPhaseUserService.createWorkingPhaseUserFromJson(id, sid, input);

		return new ResponseEntity<>(JsonUtil.workingPhaseUserToWorkingPhaseUserJson(workingPhaseUser), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/admin/productionorders/{id}/workingphases/{sid}/shifts/{tid}")
	public Object updateWorkingPhaseUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @PathVariable("tid") Integer tid, @Valid @RequestBody WorkingPhaseUserJson input) {

		WorkingPhaseUser workingPhaseUser = this.workingPhaseUserService.updateWorkingPhaseUserFromJson(id, sid, tid, input, true);

		return new ResponseEntity<>(JsonUtil.workingPhaseUserToWorkingPhaseUserJson(workingPhaseUser), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/admin/productionorders/{id}/workingphases/{sid}/shifts/{tid}")
	public Object deleteWorkingPhaseUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @PathVariable("tid") Integer tid) {

		this.workingPhaseUserService.deleteWorkingPhaseUserById(tid); // TODO modify the associated ProductionOrder?

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
	@GetMapping(value = "/resp/productionorders/{id}/workingphases/{sid}/shifts")
	public List<WorkingPhaseUserJson> getWorkingPhaseUsersForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		log.info("Request for workingPhaseUsers in ProductionOrder with id {}", id);
		
		List<WorkingPhaseUserJson> ret = new ArrayList<>();
		Boolean isAdmin = false;
		
		for(WorkingPhaseUser sf : this.workingPhaseUserService.getWorkingPhaseUsers(id, sid, isAdmin)) {
			ret.add(JsonUtil.workingPhaseUserToWorkingPhaseUserJson(sf));
		}
		return ret;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/resp/productionorders/{id}/workingphases/{sid}/shifts/{tid}")
	public WorkingPhaseUserJson getWorkingPhaseUserForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @PathVariable("tid") Integer tid) {

		log.info("Request for workingPhaseUser with id {}", sid);
		Boolean isAdmin = false;
		
		return JsonUtil.workingPhaseUserToWorkingPhaseUserJson(this.workingPhaseUserService.getWorkingPhaseUser(id, sid, tid, isAdmin));
	}
	
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/resp/productionorders/{id}/workingphases/{sid}/shifts")
	public Object postWorkingPhaseUserForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @Valid @RequestBody WorkingPhaseUserJson input, HttpServletRequest request) {

		WorkingPhaseUser workingPhaseUser = this.workingPhaseUserService.createWorkingPhaseUserFromJson(id, sid, input);

		return new ResponseEntity<>(JsonUtil.workingPhaseUserToWorkingPhaseUserJson(workingPhaseUser), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/resp/productionorders/{id}/workingphases/{sid}/shifts/{tid}")
	public Object updateWorkingPhaseUserForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @PathVariable("tid") Integer tid, @Valid @RequestBody WorkingPhaseUserJson input) {

		WorkingPhaseUser workingPhaseUser = this.workingPhaseUserService.updateWorkingPhaseUserFromJson(id, sid, tid, input, true);

		return new ResponseEntity<>(JsonUtil.workingPhaseUserToWorkingPhaseUserJson(workingPhaseUser), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/resp/productionorders/{id}/workingphases/{sid}/shifts/{tid}")
	public Object deleteWorkingPhaseUserForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @PathVariable("tid") Integer tid) {

		this.workingPhaseUserService.deleteWorkingPhaseUserById(tid); // TODO modify the associated ProductionOrder?

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
	@GetMapping(value = "/productionorders/{id}/workingphases/{sid}/shifts")
	public List<WorkingPhaseUserJson> getWorkingPhaseUsersForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		log.info("Request for workingPhaseUsers in ProductionOrder with id {}", id);
		
		List<WorkingPhaseUserJson> ret = new ArrayList<>();
		Boolean isAdmin = false;
		
		for(WorkingPhaseUser sf : this.workingPhaseUserService.getWorkingPhaseUsers(id, sid, isAdmin)) {
			ret.add(JsonUtil.workingPhaseUserToWorkingPhaseUserJson(sf));
		}
		return ret;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/productionorders/{id}/workingphases/{sid}/shifts/{tid}")
	public WorkingPhaseUserJson getWorkingPhaseUserForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @PathVariable("tid") Integer tid) {

		log.info("Request for workingPhaseUser with id {}", sid);
		Boolean isAdmin = false;
		
		return JsonUtil.workingPhaseUserToWorkingPhaseUserJson(this.workingPhaseUserService.getWorkingPhaseUser(id, sid, tid, isAdmin));
	}
	
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/productionorders/{id}/workingphases/{sid}/shifts")
	public Object postWorkingPhaseUserForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @Valid @RequestBody WorkingPhaseUserJson input, HttpServletRequest request) {

		WorkingPhaseUser workingPhaseUser = this.workingPhaseUserService.createWorkingPhaseUserFromJson(id, sid, input);

		return new ResponseEntity<>(JsonUtil.workingPhaseUserToWorkingPhaseUserJson(workingPhaseUser), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/productionorders/{id}/workingphases/{sid}/shifts/{tid}")
	public Object updateWorkingPhaseUserForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @PathVariable("tid") Integer tid, @Valid @RequestBody WorkingPhaseUserJson input) {

		WorkingPhaseUser workingPhaseUser = this.workingPhaseUserService.updateWorkingPhaseUserFromJson(id, sid, tid, input, true);

		return new ResponseEntity<>(JsonUtil.workingPhaseUserToWorkingPhaseUserJson(workingPhaseUser), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/productionorders/{id}/workingphases/{sid}/shifts/{tid}")
	public Object deleteWorkingPhaseUserForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @PathVariable("tid") Integer tid) {

		this.workingPhaseUserService.deleteWorkingPhaseUserById(tid); // TODO modify the associated ProductionOrder?

		return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
	}
	
}