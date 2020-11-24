package it.mynaproject.gestprod.service;

import java.util.List;

import it.mynaproject.gestprod.domain.AdditiveProductionOrder;
import it.mynaproject.gestprod.model.AdditiveProductionOrderJson;

public interface AdditiveProductionOrderService {

	public AdditiveProductionOrder getAdditiveProductionOrder(Integer additiveId, Integer productionOrderId, Boolean isAdmin);
	public List<AdditiveProductionOrder> getAdditiveProductionOrders(Boolean isAdmin);
	public void persist(AdditiveProductionOrder additiveProductionOrder);
	public AdditiveProductionOrder createAdditiveProductionOrderFromJson(AdditiveProductionOrderJson input);
	public void update(AdditiveProductionOrder additiveProductionOrder);
	public AdditiveProductionOrder updateAdditiveProductionOrderFromJson(Integer additiveId, Integer productionOrderId, AdditiveProductionOrderJson input, Boolean isAdmin);
	public void deleteAdditiveProductionOrderById(Integer additiveId, Integer productionOrderId);
}
