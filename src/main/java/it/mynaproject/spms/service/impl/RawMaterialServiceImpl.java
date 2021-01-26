package it.mynaproject.spms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.spms.dao.RawMaterialDao;
import it.mynaproject.spms.domain.ProductionOrder;
import it.mynaproject.spms.domain.RawMaterial;
import it.mynaproject.spms.exception.ConflictException;
import it.mynaproject.spms.exception.NotFoundException;
import it.mynaproject.spms.model.RawMaterialJson;
import it.mynaproject.spms.service.RawMaterialService;

@Service
public class RawMaterialServiceImpl implements RawMaterialService {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RawMaterialDao rawMaterialDao;

	@Transactional(readOnly = true)
	@Override
	public RawMaterial getRawMaterial(Integer id) {

		RawMaterial p = this.rawMaterialDao.getRawMaterial(id);
		if (p == null)
			throw new NotFoundException(404, "RawMaterial " + id + " not found");
		return p;
	}

	@Transactional(readOnly = true)
	@Override
	public List<RawMaterial> getRawMaterials() {

		List<RawMaterial> pList = new ArrayList<RawMaterial>();
		for (RawMaterial p : this.rawMaterialDao.getRawMaterials())
			pList.add(p);

		return pList;
	}

	@Transactional
	@Override
	public void persist(RawMaterial rawMaterial) {
		this.rawMaterialDao.persist(rawMaterial);
	}

	@Transactional
	@Override
	public RawMaterial createRawMaterialFromJson(RawMaterialJson input) {

		log.info("Creating new rawMaterial: {}", input.toString());

		RawMaterial c = this.rawMaterialDao.checkRawMaterialExists(input.getName(), null);
		if(c != null) {
			throw new ConflictException(3001, "RawMaterial " + input.getName() + " already registered with id: " + c.getId());
		}
		
		RawMaterial rawMaterial = new RawMaterial();
		rawMaterial.populateRawMaterialFromInput(input);

		this.persist(rawMaterial);

		return rawMaterial;
	}

	@Transactional
	@Override
	public void update(RawMaterial rawMaterial) {
		this.rawMaterialDao.update(rawMaterial);
	}

	@Transactional
	@Override
	public RawMaterial updateRawMaterialFromJson(Integer id, RawMaterialJson input) {

		log.info("Updating rawMaterial with id: {}", id);
		
		RawMaterial c = this.rawMaterialDao.checkRawMaterialExists(input.getName(), id);
		if(c != null) {
			throw new ConflictException(3001, "RawMaterial " + input.getName() + " already registered with id: " + c.getId());
		}

		RawMaterial rawMaterial = this.getRawMaterial(id);
		rawMaterial.populateRawMaterialFromInput(input);

		this.update(rawMaterial);

		return rawMaterial;
	}

	@Transactional
	@Override
	public void deleteRawMaterialById(Integer id) {

		log.info("Deleting rawMaterial: {}", id);
		RawMaterial c = this.rawMaterialDao.getRawMaterial(id);
		
		if (c == null) {
			throw new NotFoundException(404, "RawMaterial " + id + " not found");
		}
		
		Date now = new Date();
		if (c.getProductionOrders() != null) {
			for(ProductionOrder po : c.getProductionOrders()) {
				if(now.after(po.getDelivery_date()))
					throw new ConflictException(3101, "Cannot delete rawMaterial: " + id + ", production order already delivered.");
			}
		}
		this.rawMaterialDao.delete(c);
	}
}