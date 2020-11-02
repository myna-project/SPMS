package it.mynaproject.gestprod.model;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import it.mynaproject.gestprod.domain.Customer;
import it.mynaproject.gestprod.domain.Role;
import it.mynaproject.gestprod.domain.User;

public class JsonUtil {

	public static CustomerJson customerToCustomerJson(final Customer c) {

		final CustomerJson cj = new CustomerJson();
		cj.setId(c.getId());
		cj.setName(c.getName());

		return cj;
	}

	public static RoleJson roleToRoleJson(final Role r) {

		final RoleJson rj = new RoleJson();
		rj.setId(r.getId());
		rj.setName(r.getName());
		rj.setDescription(r.getDescription());
		if (r.getUsers() != null) {
			List<String> sr = new ArrayList<>();
			for (User u : r.getUsers())
				sr.add(u.getUsername());
			rj.setUsers(sr);
		}

		return rj;
	}

	public static UserJson userToUserJson(final User u, final Boolean getRoles) {

		final UserJson uj = new UserJson();
		uj.setId(u.getId());
		uj.setUsername(u.getUsername());
		uj.setName(u.getName());
		uj.setSurname(u.getSurname());
		uj.setEnabled(u.getEnabled());
		uj.setEmail(u.getEmail());
		if (u.getAvatar() != null)
			uj.setAvatar(Base64.getEncoder().encodeToString(u.getAvatar()));
		uj.setLang(u.getLang());
		uj.setStyle(u.getStyle());
		if (getRoles && (u.getRoleList() != null)) {
			List<Integer> rolesId = new ArrayList<>();
			for (Role r : u.getRoleList())
				rolesId.add(r.getId());
			uj.setRoles(rolesId);
		}

		return uj;
	}
}
