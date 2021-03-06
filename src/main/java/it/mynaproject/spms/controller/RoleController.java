package it.mynaproject.spms.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.mynaproject.spms.domain.Role;
import it.mynaproject.spms.model.JsonUtil;
import it.mynaproject.spms.model.RoleJson;
import it.mynaproject.spms.service.RoleService;

@RestController
public class RoleController {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RoleService roleService;

	/*
	 *  -------------
	 *  ADMIN SECTION
	 *  -------------
	 *  These routes must be accessible only for ROLE_ADMIN
	 */
	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/roles")
	public List<RoleJson> getRoles() {

		log.info("Request for roles");

		List<RoleJson> roles = new ArrayList<>();
		for (Role role : this.roleService.getRoles())
			roles.add(JsonUtil.roleToRoleJson(role));

		return roles;
	}

	@ApiResponses({
		@ApiResponse(code = 400, message = "Bad Request")
	})
	@GetMapping(value = "/admin/roles/{id}")
	public RoleJson getRole(@PathVariable("id") Integer id) {

		log.info("Request for role with id {}", id);

		return JsonUtil.roleToRoleJson(this.roleService.getRole(id));
	}
}
