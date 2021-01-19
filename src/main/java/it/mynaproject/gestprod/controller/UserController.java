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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.mynaproject.gestprod.domain.User;
import it.mynaproject.gestprod.model.JsonUtil;
import it.mynaproject.gestprod.model.UserJson;
import it.mynaproject.gestprod.service.UserService;

@RestController
public class UserController {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;

	/*
	 *  -------------
	 *  ADMIN SECTION
	 *  -------------
	 *  These routes must be accessible only for ROLE_SUPERADMIN
	 */
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/users")
	public List<UserJson> getUsers(@RequestParam(required = false) String username) {

		log.info("Request for users with username: {}", username);

		List<UserJson> ujList = new ArrayList<>();
		for (User user : this.userService.getUsers()) {
			if ((username == null) ||((username != null) && (user.getUsername().equals(username)))) {
				UserJson uj = JsonUtil.userToUserJson(user, true);
				ujList.add(uj);
			}
		}

		return ujList;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/users/{id}")
	public UserJson getUser(@PathVariable("id") Integer id) {

		log.info("Getting user with id {}", id);

		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return JsonUtil.userToUserJson(this.userService.getUser(id, user.getUsername(), true), true);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PostMapping(value = "/admin/users")
	public Object postUser(@Valid @RequestBody UserJson input, HttpServletRequest request) {

		org.springframework.security.core.userdetails.User authUser = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		User user = this.userService.createUserFromInput(input, authUser.getUsername());

		return new ResponseEntity<>(JsonUtil.userToUserJson(user, true), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/admin/users/{id}")
	public Object updateUser(@PathVariable("id") Integer id, @Valid @RequestBody UserJson input) {

		org.springframework.security.core.userdetails.User authUser = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		User user = this.userService.updateUserFromInput(id, input, authUser.getUsername(), true);

		return new ResponseEntity<>(JsonUtil.userToUserJson(user, true), new HttpHeaders(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping(value = "/admin/users/{id}")
	public Object deleteUser(@PathVariable("id") Integer id) {

		org.springframework.security.core.userdetails.User authUser = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		this.userService.deleteUserById(id, authUser.getUsername(), true);

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
	@GetMapping(value = "/resp/users")
	public List<UserJson> getUsersForResp(@RequestParam(required = false) String username) {

		log.info("Request for users with username: {}", username);

		List<UserJson> ujList = new ArrayList<>();
		for (User user : this.userService.getUsers()) {
			if ((username == null) ||((username != null) && (user.getUsername().equals(username)))) {
				UserJson uj = JsonUtil.userToUserJson(user, true);
				ujList.add(uj);
			}
		}

		return ujList;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/resp/users/{id}")
	public Object updateProfileForResp(@PathVariable("id") Integer id, @Valid @RequestBody UserJson input) {

		org.springframework.security.core.userdetails.User authUser = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		User user = this.userService.updateUserFromInput(id, input, authUser.getUsername(), false);

		return new ResponseEntity<>(JsonUtil.userToUserJson(user, true), new HttpHeaders(), HttpStatus.OK);
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
	@GetMapping(value = "/users")
	public List<UserJson> getUsersForUser(@RequestParam(required = false) String username) {

		log.info("Request for users with username: {}", username);

		List<UserJson> ujList = new ArrayList<>();
		for (User user : this.userService.getUsers()) {
			if ((username == null) ||((username != null) && (user.getUsername().equals(username)))) {
				UserJson uj = JsonUtil.userToUserJson(user, true);
				ujList.add(uj);
			}
		}

		return ujList;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@PutMapping(value = "/users/{id}")
	public Object updateProfileForUser(@PathVariable("id") Integer id, @Valid @RequestBody UserJson input) {

		org.springframework.security.core.userdetails.User authUser = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		User user = this.userService.updateUserFromInput(id, input, authUser.getUsername(), false);

		return new ResponseEntity<>(JsonUtil.userToUserJson(user, true), new HttpHeaders(), HttpStatus.OK);
	}
}
