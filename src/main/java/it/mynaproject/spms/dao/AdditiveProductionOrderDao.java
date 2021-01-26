package it.mynaproject.spms.dao;

import java.util.List;

import it.mynaproject.spms.domain.AdditiveProductionOrder;

public interface AdditiveProductionOrderDao {

	public void persist(AdditiveProductionOrder additiveProductionOrder);
	public void update(AdditiveProductionOrder additiveProductionOrder);
	public void delete(AdditiveProductionOrder additiveProductionOrder);
	public AdditiveProductionOrder getAdditiveProductionOrder(Integer id);
	public List<AdditiveProductionOrder> getAdditiveProductionOrders();
}