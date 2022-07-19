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
import it.mynaproject.spms.domain.SettingPhase;
import it.mynaproject.spms.model.JsonUtil;
import it.mynaproject.spms.model.SettingPhaseJson;
import it.mynaproject.spms.service.ProductionOrderService;
import it.mynaproject.spms.service.SettingPhaseService;

@RestController
public class SettingPhaseController {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SettingPhaseService settingPhaseService;

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
	@GetMapping(value = "/admin/productionOrders/{id}/settingPhases")
	public List<SettingPhaseJson> getSettingPhases(@PathVariable("id") Integer id) {

		log.info("Request for settingPhases in ProductionOrder with id {}", id);

		List<SettingPhaseJson> ret = new ArrayList<>();
		for (SettingPhase sf : this.productionOrderService.getProductionOrder(id).getSettingPhaseList())
			ret.add(JsonUtil.settingPhaseToSettingPhaseJson(sf, true));

		return ret;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/productionOrders/{id}/settingPhases/{sid}")
	public SettingPhaseJson getSettingPhase(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		log.info("Request for settingPhase with id {}", sid);

		return JsonUtil.settingPhaseToSettingPhaseJson(this.settingPhaseService.getSettingPhase(id, sid), true);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/admin/productionOrders/{id}/settingPhases")
	public Object postSettingPhase(@PathVariable("id") Integer id, @Valid @RequestBody SettingPhaseJson input, HttpServletRequest request) {

		return new ResponseEntity<>(JsonUtil.settingPhaseToSettingPhaseJson(this.settingPhaseService.createSettingPhaseFromJson(id, input), true), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/admin/productionOrders/{id}/settingPhases/{sid}")
	public Object updateSettingPhase(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @Valid @RequestBody SettingPhaseJson input) {

		return new ResponseEntity<>(JsonUtil.settingPhaseToSettingPhaseJson(this.settingPhaseService.updateSettingPhaseFromJson(id, sid, input), true), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/admin/productionOrders/{id}/settingPhases/{sid}")
	public Object deleteSettingPhase(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		this.settingPhaseService.deleteSettingPhaseById(id, sid);

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
	@GetMapping(value = "/resp/productionOrders/{id}/settingPhases")
	public List<SettingPhaseJson> getSettingPhasesForResp(@PathVariable("id") Integer id) {

		log.info("Request for settingPhases in ProductionOrder with id {}", id);

		List<SettingPhaseJson> ret = new ArrayList<>();
		for (SettingPhase sf : this.productionOrderService.getProductionOrder(id).getSettingPhaseList())
			ret.add(JsonUtil.settingPhaseToSettingPhaseJson(sf, true));

		return ret;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/resp/productionOrders/{id}/settingPhases/{sid}")
	public SettingPhaseJson getSettingPhaseForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		log.info("Request for settingPhase with id {}", sid);

		return JsonUtil.settingPhaseToSettingPhaseJson(this.settingPhaseService.getSettingPhase(id, sid), true);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/resp/productionOrders/{id}/settingPhases")
	public Object postSettingPhaseForResp(@PathVariable("id") Integer id, @Valid @RequestBody SettingPhaseJson input, HttpServletRequest request) {

		return new ResponseEntity<>(JsonUtil.settingPhaseToSettingPhaseJson(this.settingPhaseService.createSettingPhaseFromJson(id, input), true), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/resp/productionOrders/{id}/settingPhases/{sid}")
	public Object updateSettingPhaseForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @Valid @RequestBody SettingPhaseJson input) {

		return new ResponseEntity<>(JsonUtil.settingPhaseToSettingPhaseJson(this.settingPhaseService.updateSettingPhaseFromJson(id, sid, input), true), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/resp/productionOrders/{id}/settingPhases/{sid}")
	public Object deleteSettingPhaseForResp(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		this.settingPhaseService.deleteSettingPhaseById(id, sid);

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
	@GetMapping(value = "/productionOrders/{id}/settingPhases")
	public List<SettingPhaseJson> getSettingPhasesForUser(@PathVariable("id") Integer id) {

		log.info("Request for settingPhases in ProductionOrder with id {}", id);

		List<SettingPhaseJson> ret = new ArrayList<>();
		for (SettingPhase sf : this.productionOrderService.getProductionOrder(id).getSettingPhaseList())
			ret.add(JsonUtil.settingPhaseToSettingPhaseJson(sf, true));

		return ret;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/productionOrders/{id}/settingPhases/{sid}")
	public SettingPhaseJson getSettingPhaseForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		log.info("Request for settingPhase with id {}", sid);

		return JsonUtil.settingPhaseToSettingPhaseJson(this.settingPhaseService.getSettingPhase(id, sid), true);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/productionOrders/{id}/settingPhases")
	public Object postSettingPhaseForUser(@PathVariable("id") Integer id, @Valid @RequestBody SettingPhaseJson input, HttpServletRequest request) {

		return new ResponseEntity<>(JsonUtil.settingPhaseToSettingPhaseJson(this.settingPhaseService.createSettingPhaseFromJson(id, input), true), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/productionOrders/{id}/settingPhases/{sid}")
	public Object updateSettingPhaseForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid, @Valid @RequestBody SettingPhaseJson input) {

		return new ResponseEntity<>(JsonUtil.settingPhaseToSettingPhaseJson(this.settingPhaseService.updateSettingPhaseFromJson(id, sid, input), true), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/productionOrders/{id}/settingPhases/{sid}")
	public Object deleteSettingPhaseForUser(@PathVariable("id") Integer id, @PathVariable("sid") Integer sid) {

		this.settingPhaseService.deleteSettingPhaseById(id, sid);

		return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
	}
}
