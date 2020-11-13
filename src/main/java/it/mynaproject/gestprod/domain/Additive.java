package it.mynaproject.gestprod.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

import it.mynaproject.gestprod.model.AdditiveJson;
import it.mynaproject.gestprod.util.ClassSerializer;

@Entity
@Table(name = "additives")
public class Additive extends BaseDomain {
	
	@Column(nullable=false)
	private String name;
	
	@OneToMany(mappedBy = "additive", cascade = CascadeType.ALL)
	private List<AdditiveProductionOrder> additiveProductionOrderList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<AdditiveProductionOrder> getAdditiveProductionOrderList() {
		return additiveProductionOrderList;
	}

	public void setAdditiveProductionOrderList(List<AdditiveProductionOrder> additiveProductionOrderList) {
		this.additiveProductionOrderList = additiveProductionOrderList;
	}
	
	public void populateAdditiveFromInput(AdditiveJson input) {
		this.setName(input.getName());
	}

	public String toString() {
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		return serializer.toString();
	}
}
