package it.mynaproject.gestprod.service;

import java.util.List;

import it.mynaproject.gestprod.domain.Role;
import it.mynaproject.gestprod.domain.User;
import it.mynaproject.gestprod.model.UserJson;

public interface UserService {

	public User getUser(Integer id, Boolean isAdmin, String username);
	public List<User> getUsers();
	public void persist(User user);
	public User createUserFromInput(UserJson input, Boolean isAdmin, String username);
	public void update(User user, Boolean changeUser);
	public User updateUserFromInput(Integer id, UserJson input, Boolean isAdmin, String username);
	public void deleteUserById(Integer id, Boolean isAdmin, String username);
	public User getUser(String username);
	public List<Role> getRoles(Integer id);
	public Boolean passwordsMatch(String oldPsw,String newPsw);
	public Boolean checkNameForUser(String name);
	public Boolean checkNameForUser(String name, Integer id);
}
