package it.mynaproject.spms.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToMany;

import it.mynaproject.spms.model.MixtureModeJson;
import it.mynaproject.spms.util.ClassSerializer;

@Entity
@Table(name = "mixture_modes")
public class MixtureMode extends BaseDomain {
	
	@Column(nullable=false)
	private String name;
	
	@OneToMany(mappedBy="mixtureMode", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
	private List<ProductionOrder> productionOrders;
	
	public void populateMixtureModeFromInput(MixtureModeJson input) {
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

	public List<ProductionOrder> getProductionOrders() {
		return productionOrders;
	}

	public void setProductionOrders(List<ProductionOrder> productionOrders) {
		this.productionOrders = productionOrders;
	}
	
}
