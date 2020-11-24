package it.mynaproject.gestprod.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.gestprod.dao.ProductionOrderDao;
import it.mynaproject.gestprod.domain.AdditiveProductionOrder;
import it.mynaproject.gestprod.domain.Customer;
import it.mynaproject.gestprod.domain.MixtureMode;
import it.mynaproject.gestprod.domain.Packaging;
import it.mynaproject.gestprod.domain.ProductionOrder;
import it.mynaproject.gestprod.domain.RawMaterial;
import it.mynaproject.gestprod.exception.*;
import it.mynaproject.gestprod.model.ProductionOrderJson;
import it.mynaproject.gestprod.service.AdditiveProductionOrderService;
import it.mynaproject.gestprod.service.CustomerService;
import it.mynaproject.gestprod.service.MixtureModeService;
import it.mynaproject.gestprod.service.PackagingService;
import it.mynaproject.gestprod.service.ProductionOrderService;
import it.mynaproject.gestprod.service.RawMaterialService;

@Service
public class ProductionOrderServiceImpl implements ProductionOrderService { // TODO: fasi di produzione, gestire ID additiveProductionOrder

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProductionOrderDao productionOrderDao;
	private CustomerService customerService;
	private RawMaterialService rawMaterialService;
	private MixtureModeService mixtureModeService;
	private PackagingService packagingService;
	private AdditiveProductionOrderService apoService;

	@Transactional(readOnly = true)
	@Override
	public ProductionOrder getProductionOrder(Integer id, Boolean isAdmin) {

		ProductionOrder p = this.productionOrderDao.getProductionOrder(id);
		if (p == null)
			throw new NotFoundException(404, "ProductionOrder " + id + " not found");
		return p;
	}

	@Transactional(readOnly = true)
	@Override
	public List<ProductionOrder> getProductionOrders(Boolean isAdmin) {

		List<ProductionOrder> pList = new ArrayList<ProductionOrder>();
		for (ProductionOrder p : this.productionOrderDao.getProductionOrders())
			pList.add(p);

		return pList;
	}

	@Transactional
	@Override
	public void persist(ProductionOrder productionOrder) {
		this.productionOrderDao.persist(productionOrder);
	}

	@Transactional
	@Override
	public ProductionOrder createProductionOrderFromJson(ProductionOrderJson input) {

		log.info("Creating new productionOrder: {}", input.toString());

		if(this.productionOrderDao.getProductionOrder(input.getId()) != null) {
			throw new ConflictException(7001, "ProductionOrder " + input.getProduction_order_code() + " already registered with id: " + input.getId());
		}
		
		Boolean isAdmin = false; // TODO fix
		Customer c = this.customerService.getCustomer(input.getCustomer_id(), isAdmin);
		RawMaterial rm = this.rawMaterialService.getRawMaterial(input.getRaw_material_id(), isAdmin);
		MixtureMode mm = this.mixtureModeService.getMixtureMode(input.getExpected_mixture_mode_id(), isAdmin);
		Packaging pp = this.packagingService.getPackaging(input.getPackaging_id(), isAdmin);
		List<AdditiveProductionOrder> apol = new ArrayList<>();
		
		for(Integer aid : input.getAdditivesId()) {
			apol.add(this.apoService.getAdditiveProductionOrder(aid, input.getId(), isAdmin));
		}
		
		ProductionOrder productionOrder = new ProductionOrder();
		productionOrder.populateProductionOrderFromInput(input, c, mm, pp, rm, apol);

		this.persist(productionOrder);

		return productionOrder;
	}

	@Transactional
	@Override
	public void update(ProductionOrder productionOrder) {
		this.productionOrderDao.update(productionOrder);
	}

	@Transactional
	@Override
	public ProductionOrder updateProductionOrderFromJson(Integer id, ProductionOrderJson input, Boolean isAdmin) {

		log.info("Updating productionOrder with id: {}", id);

		// TODO non sarebbe meglio avere un accesso lineare ai productionOrder gia` presenti?
		for(ProductionOrder e: this.productionOrderDao.getProductionOrders()) {
			if(e.getId().equals(input.getId())) {
				throw new ConflictException(7001, "ProductionOrder name " + input.getProduction_order_code() + " already registered with id: " + input.getId());
			}
		}
		
		Customer c = this.customerService.getCustomer(input.getCustomer_id(), isAdmin);
		RawMaterial rm = this.rawMaterialService.getRawMaterial(input.getRaw_material_id(), isAdmin);
		MixtureMode mm = this.mixtureModeService.getMixtureMode(input.getExpected_mixture_mode_id(), isAdmin);
		Packaging pp = this.packagingService.getPackaging(input.getPackaging_id(), isAdmin);
		List<AdditiveProductionOrder> apol = new ArrayList<>();
		
		// TODO vedi fix su AdditiveProductionOrder
		for(Integer aid : input.getAdditivesId()) {
			apol.add(this.apoService.getAdditiveProductionOrder(aid, input.getId(), isAdmin));
		}
		
		ProductionOrder productionOrder = new ProductionOrder();
		productionOrder.populateProductionOrderFromInput(input, c, mm, pp, rm, apol);

		this.update(productionOrder);

		return productionOrder;
	}

	@Transactional
	@Override
	public void deleteProductionOrderById(Integer id) {

		log.info("Deleting productionOrder: {}", id);
		ProductionOrder c = this.productionOrderDao.getProductionOrder(id);
		
		if (c == null) {
			throw new NotFoundException(404, "ProductionOrder " + id + " not found");
		}
		
//		Date now = new Date();
//		if (c.getProductionOrders() != null) {
//			for(ProductionOrder po : c.getProductionOrders()) {
//				if(now.after(po.getDelivery_date()))
//					throw new ConflictException(7101, "Cannot delete productionOrder: " + id + ", production order already delivered.");
//			}
//		}
		this.productionOrderDao.delete(c);
	}
}
