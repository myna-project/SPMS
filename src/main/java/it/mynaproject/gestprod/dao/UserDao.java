package it.mynaproject.gestprod.dao;

import java.util.List;

import it.mynaproject.gestprod.domain.Role;
import it.mynaproject.gestprod.domain.User;

public interface UserDao {
	
	public void persist(User user);
	public void update(User user);
	public void delete(User user);
	public User getUser(Integer id);
	public List<User> getUsers();
	public List<Role> getRoles(Integer id);
	public User getUser(String username);
	public Boolean checkEmailExists(String email, Integer id);
}
