package it.mynaproject.spms.service;

import java.util.List;

import it.mynaproject.spms.domain.AdditiveProductionOrder;
import it.mynaproject.spms.model.AdditiveProductionOrderJson;

public interface AdditiveProductionOrderService {

	public AdditiveProductionOrder getAdditiveProductionOrder(Integer id);
	public List<AdditiveProductionOrder> getAdditiveProductionOrders();
	public void persist(AdditiveProductionOrder additiveProductionOrder);
	public AdditiveProductionOrder createAdditiveProductionOrderFromJson(Integer pid, AdditiveProductionOrderJson input);
	public void update(AdditiveProductionOrder additiveProductionOrder);
//	public AdditiveProductionOrder updateAdditiveProductionOrderFromJson(Integer pid, Integer id, AdditiveProductionOrderJson input);
	public void deleteAdditiveProductionOrderById(Integer id);
}
