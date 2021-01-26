package it.mynaproject.spms.service;

import java.util.List;

import it.mynaproject.spms.domain.ProductionOrder;
import it.mynaproject.spms.model.ProductionOrderJson;

public interface ProductionOrderService {

	public ProductionOrder getProductionOrder(Integer id);
	public List<ProductionOrder> getProductionOrders();
	public void persist(ProductionOrder productionOrder);
	public ProductionOrder createProductionOrderFromJson(ProductionOrderJson input);
	public void update(ProductionOrder productionOrder);
	public ProductionOrder updateProductionOrderFromJson(Integer id, ProductionOrderJson input);
	public void deleteProductionOrderById(Integer id);
}
