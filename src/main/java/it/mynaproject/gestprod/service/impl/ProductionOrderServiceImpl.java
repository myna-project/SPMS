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
import it.mynaproject.gestprod.dao.SettingPhaseDao;
import it.mynaproject.gestprod.dao.SystemPreparationPhaseDao;
import it.mynaproject.gestprod.dao.AdditiveProductionOrderDao;
import it.mynaproject.gestprod.dao.CleaningPhaseDao;
import it.mynaproject.gestprod.dao.WorkingPhaseDao;
import it.mynaproject.gestprod.dao.ValidationPhaseDao;
import it.mynaproject.gestprod.domain.AdditiveProductionOrder;
import it.mynaproject.gestprod.domain.Customer;
import it.mynaproject.gestprod.domain.MixtureMode;
import it.mynaproject.gestprod.domain.Packaging;
import it.mynaproject.gestprod.domain.ProductionOrder;
import it.mynaproject.gestprod.domain.RawMaterial;
import it.mynaproject.gestprod.domain.SettingPhase;
import it.mynaproject.gestprod.domain.SystemPreparationPhase;
import it.mynaproject.gestprod.domain.CleaningPhase;
import it.mynaproject.gestprod.domain.WorkingPhase;
import it.mynaproject.gestprod.domain.ValidationPhase;
import it.mynaproject.gestprod.exception.*;
import it.mynaproject.gestprod.model.AdditiveProductionOrderJson;
import it.mynaproject.gestprod.model.JsonUtil;
import it.mynaproject.gestprod.model.ProductionOrderJson;
import it.mynaproject.gestprod.service.AdditiveProductionOrderService;
import it.mynaproject.gestprod.service.CustomerService;
import it.mynaproject.gestprod.service.MixtureModeService;
import it.mynaproject.gestprod.service.PackagingService;
import it.mynaproject.gestprod.service.ProductionOrderService;
import it.mynaproject.gestprod.service.RawMaterialService;

@Service
public class ProductionOrderServiceImpl implements ProductionOrderService {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProductionOrderDao productionOrderDao;
	@Autowired
	private AdditiveProductionOrderDao apoDao;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private RawMaterialService rawMaterialService;
	@Autowired
	private MixtureModeService mixtureModeService;
	@Autowired
	private PackagingService packagingService;
	@Autowired
	private AdditiveProductionOrderService apoService;
	
	@Transactional(readOnly = true)
	@Override
	public ProductionOrder getProductionOrder(Integer id) {

		ProductionOrder p = this.productionOrderDao.getProductionOrder(id);
		if (p == null)
			throw new NotFoundException(404, "ProductionOrder " + id + " not found");
		return p;
	}

	@Transactional(readOnly = true)
	@Override
	public List<ProductionOrder> getProductionOrders() {

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

		ProductionOrder p = this.productionOrderDao.checkProductionOrderExists(input.getProduction_order_code(), null);
		if(p != null) {
			throw new ConflictException(7001, "ProductionOrder " + input.getProduction_order_code() + " already registered with id: " + p.getId());
		}
		
		Customer c = this.customerService.getCustomer(input.getCustomer().getId());
		RawMaterial rm = this.rawMaterialService.getRawMaterial(input.getRaw_material().getId());
		MixtureMode mm = this.mixtureModeService.getMixtureMode(input.getExpected_mixture_mode().getId());
		Packaging pp = this.packagingService.getPackaging(input.getPackaging().getId());
		List<AdditiveProductionOrder> apol = new ArrayList<>();
		
		ProductionOrder productionOrder = new ProductionOrder();
		productionOrder.populateProductionOrderFromInput(input, c, mm, pp, rm);
		
		this.persist(productionOrder);
		
		// additives are already registered, APOs are not: create them here
		for(AdditiveProductionOrderJson apoj : input.getAdditives()) {
			AdditiveProductionOrder apo = this.apoService.createAdditiveProductionOrderFromJson(productionOrder.getId(), apoj);
			apol.add(apo);
		}
		productionOrder.setAdditiveProductionOrderList(apol);
		
		this.update(productionOrder);

		return productionOrder;
	}

	@Transactional
	@Override
	public void update(ProductionOrder productionOrder) {
		this.productionOrderDao.update(productionOrder);
	}

	@Transactional
	@Override
	public ProductionOrder updateProductionOrderFromJson(Integer id, ProductionOrderJson input) {

		log.info("Updating productionOrder with id: {}", id);

		ProductionOrder p = this.productionOrderDao.checkProductionOrderExists(input.getProduction_order_code(), id);
		if(p != null) {
			throw new ConflictException(7001, "ProductionOrder " + input.getProduction_order_code() + " already registered with id: " + p.getId());
		}
		
		Customer c = this.customerService.getCustomer(input.getCustomer().getId());
		RawMaterial rm = this.rawMaterialService.getRawMaterial(input.getRaw_material().getId());
		MixtureMode mm = this.mixtureModeService.getMixtureMode(input.getExpected_mixture_mode().getId());
		Packaging pp = this.packagingService.getPackaging(input.getPackaging().getId());
		List<AdditiveProductionOrder> apol = new ArrayList<>();
		
		ProductionOrder productionOrder = new ProductionOrder();
		productionOrder.populateProductionOrderFromInput(input, c, mm, pp, rm);
		
		this.update(productionOrder);
		
		// additives are already registered, APOs are not: create them here
		for(AdditiveProductionOrderJson apoj : input.getAdditives()) {
			AdditiveProductionOrder apo = this.apoService.createAdditiveProductionOrderFromJson(productionOrder.getId(), apoj);
			apol.add(apo);
		}
		if(!apol.isEmpty()) productionOrder.setAdditiveProductionOrderList(apol);
		
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
		
		this.productionOrderDao.delete(c);
	}
	
}
