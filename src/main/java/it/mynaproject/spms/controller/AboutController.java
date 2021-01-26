package it.mynaproject.spms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.mynaproject.spms.model.AboutJson;
import it.mynaproject.spms.service.AboutService;

@RestController
public class AboutController {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AboutService aboutService;

	/*
	 *  -------------
	 *  ADMIN SECTION
	 *  -------------
	 *  These routes must be accessible only for ROLE_ADMIN
	 */
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/version")
	public AboutJson getVersionForAdmin() {

		log.info("Request for version and buildtime");

		return this.aboutService.getVersionAndBuildtime();
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
	@GetMapping(value = "/version")
	public AboutJson getVersionForUser() {

		log.info("Request for version and buildtime");

		return this.aboutService.getVersionAndBuildtime();
	}
}
