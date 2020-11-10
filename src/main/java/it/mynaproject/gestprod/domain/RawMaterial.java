package it.mynaproject.gestprod.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToMany;

import it.mynaproject.gestprod.model.RawMaterialJson;
import it.mynaproject.gestprod.util.ClassSerializer;

@Entity
@Table(name = "raw_materials")
public class RawMaterial extends BaseDomain {
	
	@Column(nullable=false)
	private String name;
	
	@OneToMany(mappedBy="rawMaterial")
	private List<ProductionOrder> productionOrders;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void populateRawMaterialFromInput(RawMaterialJson input) {
		this.setName(input.getName());
	}

	public String toString() {
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		return serializer.toString();
	}
}