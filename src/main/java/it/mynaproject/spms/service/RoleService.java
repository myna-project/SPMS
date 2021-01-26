package it.mynaproject.spms.service;

import java.util.List;

import it.mynaproject.spms.domain.Role;
import it.mynaproject.spms.domain.User;
import it.mynaproject.spms.model.RoleJson;

public interface RoleService {

	public Role getRole(Integer id);
	public List<Role> getRoles();
	public void persist(Role role);
	public Role createRoleFromJson(RoleJson input);
	public void update(Role role);
	public Role updateRoleFromJson(Integer id, RoleJson input);
	public void deleteRoleById(Integer id);
	public List<User> getUsersByRoleId(Integer id);
}
