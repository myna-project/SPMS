package it.mynaproject.spms.model;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import it.mynaproject.spms.util.ClassSerializer;

@JsonInclude(Include.NON_EMPTY)
public class PackagingJson {
	
	private Integer id;
	
	@NotBlank
	private String packaging_mode;

	public String toString() {
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		return serializer.toString();
	}
	
/** GETTERS and SETTERS **/
		
	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getPackaging_mode() {
		return packaging_mode;
	}

	public void setPackaging_mode(String packaging_mode) {
		this.packaging_mode = packaging_mode;
	}
}
