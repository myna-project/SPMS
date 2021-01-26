package it.mynaproject.gestprod.service.impl;

import java.util.ArrayList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.gestprod.dao.ProductionOrderDao;
import it.mynaproject.gestprod.domain.AdditiveProductionOrder;
import it.mynaproject.gestprod.domain.ProductionOrder;
import it.mynaproject.gestprod.exception.ConflictException;
import it.mynaproject.gestprod.exception.NotFoundException;
import it.mynaproject.gestprod.model.AdditiveProductionOrderJson;
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
		if (p != null)
			throw new ConflictException(7001, "ProductionOrder " + input.getProduction_order_code() + " already registered with id: " + p.getId());

		ProductionOrder productionOrder = new ProductionOrder();
		productionOrder.populateProductionOrderFromInput(input, (input.getCustomer() != null) ? this.customerService.getCustomer(input.getCustomer().getId()) : null, (input.getExpected_mixture_mode() != null) ? this.mixtureModeService.getMixtureMode(input.getExpected_mixture_mode().getId()) : null, (input.getPackaging() != null) ? this.packagingService.getPackaging(input.getPackaging().getId()) : null, (input.getRaw_material() != null) ? this.rawMaterialService.getRawMaterial(input.getRaw_material().getId()) : null);
		
		this.persist(productionOrder);

		List<AdditiveProductionOrder> apol = new ArrayList<>();
		if (input.getAdditives() != null) {
			for (AdditiveProductionOrderJson apoj : input.getAdditives()) {
				AdditiveProductionOrder apo = this.apoService.createAdditiveProductionOrderFromJson(productionOrder.getId(), apoj);
				apol.add(apo);
			}
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

		ProductionOrder productionOrder = this.getProductionOrder(id);

		ProductionOrder p = this.productionOrderDao.checkProductionOrderExists(input.getProduction_order_code(), id);
		if (p != null)
			throw new ConflictException(7001, "ProductionOrder " + input.getProduction_order_code() + " already registered with id: " + p.getId());

		productionOrder.populateProductionOrderFromInput(input, (input.getCustomer() != null) ? this.customerService.getCustomer(input.getCustomer().getId()) : null, (input.getExpected_mixture_mode() != null) ? this.mixtureModeService.getMixtureMode(input.getExpected_mixture_mode().getId()) : null, (input.getPackaging() != null) ? this.packagingService.getPackaging(input.getPackaging().getId()) : null, (input.getRaw_material() != null) ? this.rawMaterialService.getRawMaterial(input.getRaw_material().getId()) : null);

		this.update(productionOrder);

		List<AdditiveProductionOrder> apol = new ArrayList<>();
		if (input.getAdditives() != null) {
			for (AdditiveProductionOrderJson apoj : input.getAdditives()) {
				AdditiveProductionOrder apo = this.apoService.createAdditiveProductionOrderFromJson(productionOrder.getId(), apoj);
				apol.add(apo);
			}
		}
		if (!apol.isEmpty())
			productionOrder.setAdditiveProductionOrderList(apol);
		
		this.update(productionOrder);

		return productionOrder;
	}

	@Transactional
	@Override
	public void deleteProductionOrderById(Integer id) {

		log.info("Deleting productionOrder: {}", id);

		this.productionOrderDao.delete(this.getProductionOrder(id));
	}
	
}
