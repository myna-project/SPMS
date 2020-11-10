package it.mynaproject.gestprod.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import it.mynaproject.gestprod.model.MixtureModeJson;
import it.mynaproject.gestprod.util.ClassSerializer;

@Entity
@Table(name = "mixture_modes")
public class MixtureMode extends BaseDomain {
	
	@Column(nullable=false)
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void populateMixtureModeFromInput(MixtureModeJson input) {
		this.setName(input.getName());
	}

	public String toString() {
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		return serializer.toString();
	}
}
