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
import it.mynaproject.spms.domain.Additive;
import it.mynaproject.spms.model.AdditiveJson;
import it.mynaproject.spms.model.JsonUtil;
import it.mynaproject.spms.service.AdditiveService;

@RestController
public class AdditiveController {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AdditiveService additiveService;

	/*
	 *  -------------
	 *  ADMIN SECTION
	 *  -------------
	 *  These routes must be accessible only for ROLE_ADMIN
	 */
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/additives")
	public List<AdditiveJson> getAdditives() {

		log.info("Request for additives");

		List<AdditiveJson> additives = new ArrayList<>();
		for (Additive additive : this.additiveService.getAdditives())
			additives.add(JsonUtil.additiveToAdditiveJson(additive));

		return additives;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/additives/{id}")
	public AdditiveJson getAdditive(@PathVariable("id") Integer id) {

		log.info("Request for additive with id {}", id);

		return JsonUtil.additiveToAdditiveJson(this.additiveService.getAdditive(id));
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/admin/additives")
	public Object postAdditive(@Valid @RequestBody AdditiveJson input, HttpServletRequest request) {

		return new ResponseEntity<>(JsonUtil.additiveToAdditiveJson(this.additiveService.createAdditiveFromJson(input)), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/admin/additives/{id}")
	public Object updateAdditive(@PathVariable("id") Integer id, @Valid @RequestBody AdditiveJson input) {

		return new ResponseEntity<>(JsonUtil.additiveToAdditiveJson(this.additiveService.updateAdditiveFromJson(id, input)), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/admin/additives/{id}")
	public Object deleteAdditive(@PathVariable("id") Integer id) {

		this.additiveService.deleteAdditiveById(id);

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
	@GetMapping(value = "/resp/additives")
	public List<AdditiveJson> getAdditivesForResp() {

		log.info("Request for additives");

		List<AdditiveJson> additives = new ArrayList<>();
		for (Additive additive : this.additiveService.getAdditives())
			additives.add(JsonUtil.additiveToAdditiveJson(additive));

		return additives;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/resp/additives/{id}")
	public AdditiveJson getAdditiveForResp(@PathVariable("id") Integer id) {

		log.info("Request for additive with id {}", id);

		return JsonUtil.additiveToAdditiveJson(this.additiveService.getAdditive(id));
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
	@GetMapping(value = "/additives")
	public List<AdditiveJson> getAdditivesForUser() {

		log.info("Request for additives");

		List<AdditiveJson> additives = new ArrayList<>();
		for (Additive additive : this.additiveService.getAdditives())
			additives.add(JsonUtil.additiveToAdditiveJson(additive));

		return additives;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/additives/{id}")
	public AdditiveJson getAdditiveForUser(@PathVariable("id") Integer id) {

		log.info("Request for additive with id {}", id);

		return JsonUtil.additiveToAdditiveJson(this.additiveService.getAdditive(id));
	}
}
