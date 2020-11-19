package it.mynaproject.gestprod.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToMany;

import it.mynaproject.gestprod.model.PackagingJson;
import it.mynaproject.gestprod.util.ClassSerializer;

@Entity
@Table(name = "packaging")
public class Packaging extends BaseDomain {
	
	@Column(nullable=false)
	private String packaging_mode;
	
	@OneToMany(mappedBy="packaging", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
	private List<ProductionOrder> productionOrders;
	
	public void populatePackagingFromInput(PackagingJson input) {
		this.setPackaging_mode(input.getPackaging_mode());
	}

	public String toString() {
		ClassSerializer serializer = new ClassSerializer();
		serializer.setObj(this);
		return serializer.toString();
	}
	
/** GETTERS and SETTERS **/
		
	public String getPackaging_mode() {
		return packaging_mode;
	}

	public void setPackaging_mode(String packaging_mode) {
		this.packaging_mode = packaging_mode;
	}

	public List<ProductionOrder> getProductionOrders() {
		return productionOrders;
	}

	public void setProductionOrders(List<ProductionOrder> productionOrders) {
		this.productionOrders = productionOrders;
	}
	
}
