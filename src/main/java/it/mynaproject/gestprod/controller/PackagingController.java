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
import it.mynaproject.gestprod.domain.Packaging;
import it.mynaproject.gestprod.model.JsonUtil;
import it.mynaproject.gestprod.model.PackagingJson;
import it.mynaproject.gestprod.service.PackagingService;

@RestController
public class PackagingController {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PackagingService packagingService;

	/*
	 *  -------------
	 *  ADMIN SECTION
	 *  -------------
	 *  These routes must be accessible only for ROLE_ADMIN
	 */
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/packagings")
	public List<PackagingJson> getPackagings() {

		log.info("Request for packagings");

		List<PackagingJson> packagings = new ArrayList<>();

		for (Packaging packaging : this.packagingService.getPackagings())
			packagings.add(JsonUtil.packagingToPackagingJson(packaging));

		return packagings;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/packagings/{id}")
	public PackagingJson getPackaging(@PathVariable("id") Integer id) {

		log.info("Request for packaging with id {}", id);

		return JsonUtil.packagingToPackagingJson(this.packagingService.getPackaging(id));
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/admin/packagings")
	public Object postPackaging(@Valid @RequestBody PackagingJson input, HttpServletRequest request) {

		Packaging packaging = this.packagingService.createPackagingFromJson(input);

		return new ResponseEntity<>(JsonUtil.packagingToPackagingJson(packaging), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/admin/packagings/{id}")
	public Object updatePackaging(@PathVariable("id") Integer id, @Valid @RequestBody PackagingJson input) {

		Packaging packaging = this.packagingService.updatePackagingFromJson(id, input);

		return new ResponseEntity<>(JsonUtil.packagingToPackagingJson(packaging), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/admin/packagings/{id}")
	public Object deletePackaging(@PathVariable("id") Integer id) {

		this.packagingService.deletePackagingById(id);

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
	@GetMapping(value = "/resp/packagings")
	public List<PackagingJson> getPackagingsForResp() {

		log.info("Request for packagings");

		List<PackagingJson> packagings = new ArrayList<>();

		for (Packaging packaging : this.packagingService.getPackagings())
			packagings.add(JsonUtil.packagingToPackagingJson(packaging));

		return packagings;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/resp/packagings/{id}")
	public PackagingJson getPackagingForResp(@PathVariable("id") Integer id) {

		log.info("Request for packaging with id {}", id);

		return JsonUtil.packagingToPackagingJson(this.packagingService.getPackaging(id));
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
	@GetMapping(value = "/packagings")
	public List<PackagingJson> getPackagingsForUser() {

		log.info("Request for packagings");

		List<PackagingJson> packagings = new ArrayList<>();

		for (Packaging packaging : this.packagingService.getPackagings())
			packagings.add(JsonUtil.packagingToPackagingJson(packaging));

		return packagings;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/packagings/{id}")
	public PackagingJson getPackagingForUser(@PathVariable("id") Integer id) {

		log.info("Request for packaging with id {}", id);

		return JsonUtil.packagingToPackagingJson(this.packagingService.getPackaging(id));
	}
}