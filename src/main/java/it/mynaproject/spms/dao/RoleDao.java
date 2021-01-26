package it.mynaproject.spms.dao;

import java.util.List;

import it.mynaproject.spms.domain.Role;
import it.mynaproject.spms.domain.User;

public interface RoleDao {

	public void persist(Role role);
	public void update(Role role);
	public void delete(Role role);
	public Role getRole(Integer id);
	public List<Role> getRoles();
	public Boolean checkRoleExists(String name, Integer id);
	public List<User> getUsers(Integer id);
}
