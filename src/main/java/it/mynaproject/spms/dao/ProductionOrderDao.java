package it.mynaproject.spms.dao;

import java.util.List;

import org.springframework.lang.Nullable;

import it.mynaproject.spms.domain.ProductionOrder;

public interface ProductionOrderDao {

	public void persist(ProductionOrder productionOrder);
	public void update(ProductionOrder productionOrder);
	public void delete(ProductionOrder productionOrder);
	public ProductionOrder getProductionOrder(Integer id);
	public ProductionOrder checkProductionOrderExists(String name, @Nullable Integer id);
	public List<ProductionOrder> getProductionOrders();
}
