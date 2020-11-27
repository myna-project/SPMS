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
import it.mynaproject.gestprod.domain.SettingPhase;
import it.mynaproject.gestprod.model.JsonUtil;
import it.mynaproject.gestprod.model.SettingPhaseJson;
import it.mynaproject.gestprod.service.ProductionOrderService;
import it.mynaproject.gestprod.service.SettingPhaseService;

@RestController
public class SettingPhaseController {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SettingPhaseService settingPhaseService;
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
	@GetMapping(value = "/admin/productionorders/{id}/settingphases")
	public List<SettingPhaseJson> getSettingPhases(@PathVariable("id") Integer id) {

		log.info("Request for settingPhases in ProductionOrder with id {}", id);
		
		List<SettingPhaseJson> ret = new ArrayList<>();
		Boolean isAdmin = true;
		
		for(SettingPhase sf : this.productionOrderService.getProductionOrder(id, isAdmin).getSettingPhaseList()) {
			ret.add(JsonUtil.settingPhaseToSettingPhaseJson(sf));
		}
		return ret;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/productionorders/{id}/settingphases/{sid}")
	public SettingPhaseJson getSettingPhase(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		log.info("Request for settingPhase with id {}", sid);
		Boolean isAdmin = true;
		
		return JsonUtil.settingPhaseToSettingPhaseJson(this.settingPhaseService.getSettingPhase(id, sid, isAdmin));
	}
	
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/admin/productionorders/{id}/settingphases")
	public Object postSettingPhase(@PathVariable("id") Integer id, @Valid @RequestBody SettingPhaseJson input, HttpServletRequest request) {

		SettingPhase settingPhase = this.settingPhaseService.createSettingPhaseFromJson(id, input);

		return new ResponseEntity<>(JsonUtil.settingPhaseToSettingPhaseJson(settingPhase), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/admin/productionorders/{id}/settingphases/{sid}")
	public Object updateSettingPhase(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @Valid @RequestBody SettingPhaseJson input) {

		SettingPhase settingPhase = this.settingPhaseService.updateSettingPhaseFromJson(id, sid, input, true);

		return new ResponseEntity<>(JsonUtil.settingPhaseToSettingPhaseJson(settingPhase), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/admin/productionorders/{id}/settingphases/{sid}")
	public Object deleteSettingPhase(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		this.settingPhaseService.deleteSettingPhaseById(sid); // TODO modify the associated ProductionOrder?

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
	@GetMapping(value = "/resp/productionorders/{id}/settingphases")
	public List<SettingPhaseJson> getSettingPhasesForResp(@PathVariable("id") Integer id) {

		log.info("Request for settingPhases in ProductionOrder with id {}", id);
		
		List<SettingPhaseJson> ret = new ArrayList<>();
		Boolean isAdmin = false;
		
		for(SettingPhase sf : this.productionOrderService.getProductionOrder(id, isAdmin).getSettingPhaseList()) {
			ret.add(JsonUtil.settingPhaseToSettingPhaseJson(sf));
		}
		return ret;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/resp/productionorders/{id}/settingphases/{sid}")
	public SettingPhaseJson getSettingPhaseForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		log.info("Request for settingPhase with id {}", sid);
		Boolean isAdmin = false;
		
		return JsonUtil.settingPhaseToSettingPhaseJson(this.settingPhaseService.getSettingPhase(id, sid, isAdmin));
	}
	
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/resp/productionorders/{id}/settingphases")
	public Object postSettingPhaseForResp(@PathVariable("id") Integer id, @Valid @RequestBody SettingPhaseJson input, HttpServletRequest request) {

		SettingPhase settingPhase = this.settingPhaseService.createSettingPhaseFromJson(id, input);

		return new ResponseEntity<>(JsonUtil.settingPhaseToSettingPhaseJson(settingPhase), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/resp/productionorders/{id}/settingphases/{sid}")
	public Object updateSettingPhaseForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @Valid @RequestBody SettingPhaseJson input) {

		Boolean isAdmin = false;
		SettingPhase settingPhase = this.settingPhaseService.updateSettingPhaseFromJson(id, sid, input, isAdmin);

		return new ResponseEntity<>(JsonUtil.settingPhaseToSettingPhaseJson(settingPhase), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/resp/productionorders/{id}/settingphases/{sid}")
	public Object deleteSettingPhaseForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		this.settingPhaseService.deleteSettingPhaseById(sid); // TODO modify the associated ProductionOrder?

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
	@GetMapping(value = "/productionorders/{id}/settingphases")
	public List<SettingPhaseJson> getSettingPhasesForUser(@PathVariable("id") Integer id) {

		log.info("Request for settingPhases in ProductionOrder with id {}", id);
		
		List<SettingPhaseJson> ret = new ArrayList<>();
		Boolean isAdmin = false;
		
		for(SettingPhase sf : this.productionOrderService.getProductionOrder(id, isAdmin).getSettingPhaseList()) {
			ret.add(JsonUtil.settingPhaseToSettingPhaseJson(sf));
		}
		return ret;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/productionorders/{id}/settingphases/{sid}")
	public SettingPhaseJson getSettingPhaseForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		log.info("Request for settingPhase with id {}", sid);
		Boolean isAdmin = false;
		
		return JsonUtil.settingPhaseToSettingPhaseJson(this.settingPhaseService.getSettingPhase(id, sid, isAdmin));
	}
	
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/productionorders/{id}/settingphases")
	public Object postSettingPhaseForUser(@PathVariable("id") Integer id, @Valid @RequestBody SettingPhaseJson input, HttpServletRequest request) {

		SettingPhase settingPhase = this.settingPhaseService.createSettingPhaseFromJson(id, input);

		return new ResponseEntity<>(JsonUtil.settingPhaseToSettingPhaseJson(settingPhase), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/productionorders/{id}/settingphases/{sid}")
	public Object updateSettingPhaseForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @Valid @RequestBody SettingPhaseJson input) {

		Boolean isAdmin = false;
		SettingPhase settingPhase = this.settingPhaseService.updateSettingPhaseFromJson(id, sid, input, isAdmin);

		return new ResponseEntity<>(JsonUtil.settingPhaseToSettingPhaseJson(settingPhase), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/productionorders/{id}/settingphases/{sid}")
	public Object deleteSettingPhaseForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		this.settingPhaseService.deleteSettingPhaseById(sid); // TODO modify the associated ProductionOrder?

		return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
	}
	
}