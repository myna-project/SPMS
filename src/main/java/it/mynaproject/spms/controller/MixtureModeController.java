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
import it.mynaproject.spms.domain.MixtureMode;
import it.mynaproject.spms.model.JsonUtil;
import it.mynaproject.spms.model.MixtureModeJson;
import it.mynaproject.spms.service.MixtureModeService;

@RestController
public class MixtureModeController {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MixtureModeService mixtureModeService;

	/*
	 *  -------------
	 *  ADMIN SECTION
	 *  -------------
	 *  These routes must be accessible only for ROLE_ADMIN
	 */
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/mixtureModes")
	public List<MixtureModeJson> getMixtureModes() {

		log.info("Request for mixtureModes");

		List<MixtureModeJson> mixtureModes = new ArrayList<>();
		for (MixtureMode mixtureMode : this.mixtureModeService.getMixtureModes())
			mixtureModes.add(JsonUtil.mixtureModeToMixtureModeJson(mixtureMode));

		return mixtureModes;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/mixtureModes/{id}")
	public MixtureModeJson getMixtureMode(@PathVariable("id") Integer id) {

		log.info("Request for mixtureMode with id {}", id);

		return JsonUtil.mixtureModeToMixtureModeJson(this.mixtureModeService.getMixtureMode(id));
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/admin/mixtureModes")
	public Object postMixtureMode(@Valid @RequestBody MixtureModeJson input, HttpServletRequest request) {

		return new ResponseEntity<>(JsonUtil.mixtureModeToMixtureModeJson(this.mixtureModeService.createMixtureModeFromJson(input)), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/admin/mixtureModes/{id}")
	public Object updateMixtureMode(@PathVariable("id") Integer id, @Valid @RequestBody MixtureModeJson input) {

		return new ResponseEntity<>(JsonUtil.mixtureModeToMixtureModeJson(this.mixtureModeService.updateMixtureModeFromJson(id, input)), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/admin/mixtureModes/{id}")
	public Object deleteMixtureMode(@PathVariable("id") Integer id) {

		this.mixtureModeService.deleteMixtureModeById(id);

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
	@GetMapping(value = "/resp/mixtureModes")
	public List<MixtureModeJson> getMixtureModesForResp() {

		log.info("Request for mixtureModes");

		List<MixtureModeJson> mixtureModes = new ArrayList<>();
		for (MixtureMode mixtureMode : this.mixtureModeService.getMixtureModes())
			mixtureModes.add(JsonUtil.mixtureModeToMixtureModeJson(mixtureMode));

		return mixtureModes;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/resp/mixtureModes/{id}")
	public MixtureModeJson getMixtureModeForResp(@PathVariable("id") Integer id) {

		log.info("Request for mixtureMode with id {}", id);

		return JsonUtil.mixtureModeToMixtureModeJson(this.mixtureModeService.getMixtureMode(id));
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
	@GetMapping(value = "/mixtureModes")
	public List<MixtureModeJson> getMixtureModesForUser() {

		log.info("Request for mixtureModes");

		List<MixtureModeJson> mixtureModes = new ArrayList<>();
		for (MixtureMode mixtureMode : this.mixtureModeService.getMixtureModes())
			mixtureModes.add(JsonUtil.mixtureModeToMixtureModeJson(mixtureMode));

		return mixtureModes;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/mixtureModes/{id}")
	public MixtureModeJson getMixtureModeForUser(@PathVariable("id") Integer id) {

		log.info("Request for mixtureMode with id {}", id);

		return JsonUtil.mixtureModeToMixtureModeJson(this.mixtureModeService.getMixtureMode(id));
	}
}
