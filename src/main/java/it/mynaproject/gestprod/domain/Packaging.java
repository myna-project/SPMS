package it.mynaproject.gestprod.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import it.mynaproject.gestprod.model.PackagingJson;
import it.mynaproject.gestprod.util.ClassSerializer;

@Entity
@Table(name = "packaging")
public class Packaging extends BaseDomain {
	
	@Column(nullable=false)
	private String packaging_mode;
	
	public String getPackaging_mode() {
		return packaging_mode;
	}

	public void setPackaging_mode(String packaging_mode) {
		this.packaging_mode = packaging_mode;
	}
	
	public void populatePackagingFromInput(PackagingJson input) {
		this.setPackaging_mode(input.getPackaging_mode());
	}

	public String toString() {
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		return serializer.toString();
	}
}
