package it.mynaproject.gestprod.dao;

import java.util.List;

import it.mynaproject.gestprod.domain.ProductionOrder;

public interface ProductionOrderDao {

	public void persist(ProductionOrder productionOrder);
	public void update(ProductionOrder productionOrder);
	public void delete(ProductionOrder productionOrder);
	public ProductionOrder getProductionOrder(Integer id);
	public List<ProductionOrder> getProductionOrders();
}
