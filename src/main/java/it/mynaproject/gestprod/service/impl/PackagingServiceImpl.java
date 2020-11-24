package it.mynaproject.gestprod.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.gestprod.dao.PackagingDao;
import it.mynaproject.gestprod.domain.Packaging;
import it.mynaproject.gestprod.domain.ProductionOrder;
import it.mynaproject.gestprod.exception.*;
import it.mynaproject.gestprod.model.PackagingJson;
import it.mynaproject.gestprod.service.PackagingService;

@Service
public class PackagingServiceImpl implements PackagingService {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PackagingDao packagingDao;

	@Transactional(readOnly = true)
	@Override
	public Packaging getPackaging(Integer id, Boolean isAdmin) {

		Packaging p = this.packagingDao.getPackaging(id);
		if (p == null)
			throw new NotFoundException(404, "Packaging " + id + " not found");
		return p;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Packaging> getPackagings(Boolean isAdmin) {

		List<Packaging> pList = new ArrayList<Packaging>();
		for (Packaging p : this.packagingDao.getPackagings())
			pList.add(p);

		return pList;
	}

	@Transactional
	@Override
	public void persist(Packaging packaging) {
		this.packagingDao.persist(packaging);
	}

	@Transactional
	@Override
	public Packaging createPackagingFromJson(PackagingJson input) {

		log.info("Creating new packaging: {}", input.toString());

		if(this.packagingDao.getPackaging(input.getId()) != null) {
			throw new ConflictException(6001, "Packaging " + input.getPackaging_mode() + " already registered with id: " + input.getId());
			
		}
		Packaging packaging = new Packaging();
		packaging.populatePackagingFromInput(input);

		this.persist(packaging);

		return packaging;
	}

	@Transactional
	@Override
	public void update(Packaging packaging) {
		this.packagingDao.update(packaging);
	}

	@Transactional
	@Override
	public Packaging updatePackagingFromJson(Integer id, PackagingJson input, Boolean isAdmin) {

		log.info("Updating packaging with id: {}", id);

		// TODO non sarebbe meglio avere un accesso lineare ai packaging gia` presenti?
		for(Packaging e: this.packagingDao.getPackagings()) {
			if(e.getPackaging_mode().equals(input.getPackaging_mode())) {
				throw new ConflictException(6001, "Packaging mode " + input.getPackaging_mode() + " already registered with id: " + input.getId());
			}
		}
		Packaging packaging = this.getPackaging(id, isAdmin);
		packaging.populatePackagingFromInput(input);

		this.update(packaging);

		return packaging;
	}

	@Transactional
	@Override
	public void deletePackagingById(Integer id) {

		log.info("Deleting packaging: {}", id);
		Packaging c = this.packagingDao.getPackaging(id);
		
		if (c == null) {
			throw new NotFoundException(404, "Packaging " + id + " not found");
		}
		
		Date now = new Date();
		if (c.getProductionOrders() != null) {
			for(ProductionOrder po : c.getProductionOrders()) {
				if(now.after(po.getDelivery_date()))
					throw new ConflictException(6101, "Cannot delete packaging: " + id + ", production order already delivered.");
			}
		}
		this.packagingDao.delete(c);
	}
}