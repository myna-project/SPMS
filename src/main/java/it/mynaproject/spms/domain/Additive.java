package it.mynaproject.spms.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToMany;

import it.mynaproject.spms.model.AdditiveJson;
import it.mynaproject.spms.util.ClassSerializer;

@Entity
@Table(name = "additives")
public class Additive extends BaseDomain {
	
	@Column(nullable=false)
	private String name;
	
	@OneToMany(mappedBy="additive")
	private List<AdditiveProductionOrder> additiveProductionOrderList;

	public void populateAdditiveFromInput(AdditiveJson input) {
		this.setName(input.getName());
	}

	public String toString() {
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		return serializer.toString();
	}
	
/** GETTERS and SETTERS **/
		
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
	
}
