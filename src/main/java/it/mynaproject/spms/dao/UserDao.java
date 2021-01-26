package it.mynaproject.spms.dao;

import java.util.List;

import it.mynaproject.spms.domain.Role;
import it.mynaproject.spms.domain.User;

public interface UserDao {
	
	public void persist(User user);
	public void update(User user);
	public void delete(User user);
	public User getUser(Integer id);
	public List<User> getUsers();
	public List<Role> getRoles(Integer id);
	public User getUserByUsername(String username);
	public Boolean checkEmailExists(String email, Integer id);
}
