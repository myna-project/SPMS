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
import it.mynaproject.spms.domain.RawMaterial;
import it.mynaproject.spms.model.JsonUtil;
import it.mynaproject.spms.model.RawMaterialJson;
import it.mynaproject.spms.service.RawMaterialService;

@RestController
public class RawMaterialController {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RawMaterialService rawMaterialService;

	/*
	 *  -------------
	 *  ADMIN SECTION
	 *  -------------
	 *  These routes must be accessible only for ROLE_ADMIN
	 */
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/rawMaterials")
	public List<RawMaterialJson> getRawMaterials() {

		log.info("Request for rawMaterials");

		List<RawMaterialJson> rawMaterials = new ArrayList<>();

		for (RawMaterial rawMaterial : this.rawMaterialService.getRawMaterials())
			rawMaterials.add(JsonUtil.rawMaterialToRawMaterialJson(rawMaterial));

		return rawMaterials;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/rawMaterials/{id}")
	public RawMaterialJson getRawMaterial(@PathVariable("id") Integer id) {

		log.info("Request for rawMaterial with id {}", id);

		return JsonUtil.rawMaterialToRawMaterialJson(this.rawMaterialService.getRawMaterial(id));
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/admin/rawMaterials")
	public Object postRawMaterial(@Valid @RequestBody RawMaterialJson input, HttpServletRequest request) {

		RawMaterial rawMaterial = this.rawMaterialService.createRawMaterialFromJson(input);

		return new ResponseEntity<>(JsonUtil.rawMaterialToRawMaterialJson(rawMaterial), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/admin/rawMaterials/{id}")
	public Object updateRawMaterial(@PathVariable("id") Integer id, @Valid @RequestBody RawMaterialJson input) {

		RawMaterial rawMaterial = this.rawMaterialService.updateRawMaterialFromJson(id, input);

		return new ResponseEntity<>(JsonUtil.rawMaterialToRawMaterialJson(rawMaterial), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/admin/rawMaterials/{id}")
	public Object deleteRawMaterial(@PathVariable("id") Integer id) {

		this.rawMaterialService.deleteRawMaterialById(id);

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
	@GetMapping(value = "/resp/rawMaterials")
	public List<RawMaterialJson> getRawMaterialsForResp() {

		log.info("Request for rawMaterials");

		List<RawMaterialJson> rawMaterials = new ArrayList<>();

		for (RawMaterial rawMaterial : this.rawMaterialService.getRawMaterials())
			rawMaterials.add(JsonUtil.rawMaterialToRawMaterialJson(rawMaterial));

		return rawMaterials;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/resp/rawMaterials/{id}")
	public RawMaterialJson getRawMaterialForResp(@PathVariable("id") Integer id) {

		log.info("Request for rawMaterial with id {}", id);

		return JsonUtil.rawMaterialToRawMaterialJson(this.rawMaterialService.getRawMaterial(id));
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
	@GetMapping(value = "/rawMaterials")
	public List<RawMaterialJson> getRawMaterialsForUser() {

		log.info("Request for rawMaterials");

		List<RawMaterialJson> rawMaterials = new ArrayList<>();

		for (RawMaterial rawMaterial : this.rawMaterialService.getRawMaterials())
			rawMaterials.add(JsonUtil.rawMaterialToRawMaterialJson(rawMaterial));

		return rawMaterials;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/rawMaterials/{id}")
	public RawMaterialJson getRawMaterialForUser(@PathVariable("id") Integer id) {

		log.info("Request for rawMaterial with id {}", id);

		return JsonUtil.rawMaterialToRawMaterialJson(this.rawMaterialService.getRawMaterial(id));
	}
}
