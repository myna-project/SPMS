package it.mynaproject.gestprod.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.mynaproject.gestprod.util.ClassSerializer;

@JsonInclude(Include.NON_EMPTY)
public class UserJson {

	private Integer id;

	private String username;

	private String name;

	private String surname;

	private String oldPassword;

	private String password;

	private Boolean enabled;

	private String email;

	private String lang;

	private String avatar;

	private String style;

	private List<Integer> rolesId = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
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

	public void setEnabled(Boolean b) {
		this.enabled = b;
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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public List<Integer> getRoles() {
		return rolesId;
	}

	public void setRoles(List<Integer> list) {
		this.rolesId = list;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserJson [username=");
		builder.append(username);
		builder.append(", enabled=");
		builder.append(enabled);
		builder.append(", email=");
		builder.append(email);
		builder.append(", lang=");
		builder.append(lang);
		builder.append(", style=");
		builder.append(style);
		builder.append(", roles=");
		builder.append(rolesId);
		builder.append("]");
		return builder.toString();
	}

//  NOT SECURE: could serialize user password 
//	public String toString() {
//		ClassSerializer serializer = new ClassSerializer();
//		serializer.setObj(this);
//		return serializer.toString();
//	}
}
