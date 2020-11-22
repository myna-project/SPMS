package it.mynaproject.gestprod.service;

import java.util.List;

import it.mynaproject.gestprod.domain.ProductionOrder;
import it.mynaproject.gestprod.model.ProductionOrderJson;

public interface ProductionOrderService {

	public ProductionOrder getProductionOrder(Integer id, Boolean isAdmin);
	public List<ProductionOrder> getProductionOrders(Boolean isAdmin);
	public void persist(ProductionOrder productionOrder);
	public ProductionOrder createProductionOrderFromJson(ProductionOrderJson input);
	public void update(ProductionOrder productionOrder);
	public ProductionOrder updateProductionOrderFromJson(Integer id, ProductionOrderJson input, Boolean isAdmin);
	public void deleteProductionOrderById(Integer id);
}
