package it.mynaproject.spms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@GetMapping(value = "/token")
	public Object getToken() {

		log.info("Request for token");
		return new ResponseEntity<>(null , new HttpHeaders(), HttpStatus.OK);
	}
}
