package it.mynaproject.spms.domain;

import java.util.Base64;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import it.mynaproject.spms.model.UserJson;

@Entity
@Table(name = "users")
public class User extends BaseDomain {

	@Column(nullable=false,unique=true)
	private String username;

	@Column(nullable=false)
	private String name;

	@Column(nullable=false)
	private String surname;

	@Column(nullable=false)
	private String password;

	@Column(nullable=false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean enabled;

	@Column(nullable=false,unique=true)
	private String email;

	@Column
	private String lang;

	@Column
	private byte[] avatar;

	@Column
	private String style;

	@ManyToMany(cascade = CascadeType.MERGE, fetch=FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") })
	private List<Role> roleList;
	
	public void populateUserFromInput(UserJson input, List<Role> roles) {

		this.setUsername(input.getUsername());
		this.setName(input.getName());
		this.setSurname(input.getSurname());
		this.setEmail(input.getEmail());
		this.setEnabled((input.getEnabled() != null) ? input.getEnabled() : false);
		this.setRoleList(roles);

		if (input.getPassword() != null)
			this.setPassword(input.getPassword());
		if (input.getAvatar() != null)
			this.setAvatar(Base64.getDecoder().decode(input.getAvatar()));
		this.setStyle(input.getStyle());
		this.setLang(input.getLang());
	}
	
/** GETTERS and SETTERS **/
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public byte[] getAvatar() {
		return avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
}
