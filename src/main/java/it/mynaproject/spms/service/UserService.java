package it.mynaproject.spms.service;

import java.util.List;

import it.mynaproject.spms.domain.Role;
import it.mynaproject.spms.domain.User;
import it.mynaproject.spms.model.UserJson;

public interface UserService {

	public User getUser(Integer id, String username, Boolean isAdmin);
	public List<User> getUsers();
	public void persist(User user);
	public User createUserFromInput(UserJson input, String username);
	public void update(User user, Boolean changeUser);
	public User updateUserFromInput(Integer id, UserJson input, String username, Boolean isAdmin);
	public void deleteUserById(Integer id, String username, Boolean isAdmin);
	public User getUserByUsername(String username);
	public List<Role> getRoles(Integer id);
}
