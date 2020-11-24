package it.mynaproject.gestprod.dao;

import java.util.List;

import it.mynaproject.gestprod.domain.AdditiveProductionOrder;

public interface AdditiveProductionOrderDao {

	public void persist(AdditiveProductionOrder additiveProductionOrder);
	public void update(AdditiveProductionOrder additiveProductionOrder);
	public void delete(AdditiveProductionOrder additiveProductionOrder);
	public AdditiveProductionOrder getAdditiveProductionOrder(Integer additiveId, Integer productionOrderId); // TODO come gestire gli id in tabelle con id composti? (multiple @id fields), magari un metodo getID che li combini?
	public List<AdditiveProductionOrder> getAdditiveProductionOrders();
}