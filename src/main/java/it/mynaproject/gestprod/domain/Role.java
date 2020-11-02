package it.mynaproject.gestprod.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import it.mynaproject.gestprod.model.RoleJson;

@Entity
@Table(name = "roles")
public class Role extends BaseDomain implements GrantedAuthority {

	private static final long serialVersionUID = -5704123566643000391L;

	@Column(nullable=false,unique=true)
	private String name;

	private String description;

	@ManyToMany(mappedBy = "roleList")
	private List<User> userList;

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

	public List<User> getUsers() {
		return this.userList;
	}

	public void setUsers(List<User> users) {
		this.userList = users;
	}

	@Override
	public String getAuthority() {
		return this.name;
	}
	
	public void populateRoleFromInput(RoleJson input) {

		this.setDescription(input.getDescription());
		this.setName(input.getName());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Role [name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	}
}
