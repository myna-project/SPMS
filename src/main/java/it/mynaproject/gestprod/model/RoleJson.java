package it.mynaproject.gestprod.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.mynaproject.gestprod.util.ClassSerializer;

@JsonInclude(Include.NON_EMPTY)
public class RoleJson {

	private Integer id;

	private String name;

	private String description;

	private List<String> userList = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getUsers() {
		return userList;
	}

	public void setUsers(List<String> userList) {
		this.userList = userList;
	}
	
	public String toString() {
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		return serializer.toString();
	}

// --- original implementation ---
//	public String toString() {
//		StringBuilder builder = new StringBuilder();
//		builder.append("RoleJson [name=");
//		builder.append(name);
//		builder.append(", description=");
//		builder.append(description);
//		builder.append(", users=");
//		builder.append(userList);
//		builder.append("]");
//		return builder.toString();
//	}
}
