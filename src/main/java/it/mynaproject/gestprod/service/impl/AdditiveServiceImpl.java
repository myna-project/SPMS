package it.mynaproject.gestprod.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.gestprod.dao.AdditiveDao;
import it.mynaproject.gestprod.domain.Additive;
import it.mynaproject.gestprod.domain.AdditiveProductionOrder;
import it.mynaproject.gestprod.exception.*;
import it.mynaproject.gestprod.model.AdditiveJson;
import it.mynaproject.gestprod.service.AdditiveService;

@Service
public class AdditiveServiceImpl implements AdditiveService {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AdditiveDao additiveDao;

	@Transactional(readOnly = true)
	@Override
	public Additive getAdditive(Integer id, Boolean isAdmin) {

		Additive p = this.additiveDao.getAdditive(id);
		if (p == null)
			throw new NotFoundException(404, "Additive " + id + " not found");
		return p;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Additive> getAdditives(Boolean isAdmin) {

		List<Additive> pList = new ArrayList<Additive>();
		for (Additive p : this.additiveDao.getAdditives())
			pList.add(p);

		return pList;
	}

	@Transactional
	@Override
	public void persist(Additive additive) {
		this.additiveDao.persist(additive);
	}

	@Transactional
	@Override
	public Additive createAdditiveFromJson(AdditiveJson input) {

		log.info("Creating new additive: {}", input.toString());

		if(this.additiveDao.getAdditive(input.getId()) != null) {
			throw new ConflictException(4001, "Additive " + input.getName() + " already registered with id: " + input.getId());
			
		}
		Additive additive = new Additive();
		additive.populateAdditiveFromInput(input);

		this.persist(additive);

		return additive;
	}

	@Transactional
	@Override
	public void update(Additive additive) {
		this.additiveDao.update(additive);
	}

	@Transactional
	@Override
	public Additive updateAdditiveFromJson(Integer id, AdditiveJson input, Boolean isAdmin) {

		log.info("Updating additive with id: {}", id);

		// TODO non sarebbe meglio avere un accesso lineare ai additive gia` presenti?
		if(this.additiveDao.getAdditives() != null) {
			for(Additive e: this.additiveDao.getAdditives()) {
				if(e.getName().equals(input.getName())) {
					throw new ConflictException(4001, "Additive name " + input.getName() + " already registered with id: " + input.getId());
				}
			}
		}
		Additive additive = this.getAdditive(id, isAdmin);
		additive.populateAdditiveFromInput(input);

		this.update(additive);

		return additive;
	}

	@Transactional
	@Override
	public void deleteAdditiveById(Integer id) {

		log.info("Deleting additive: {}", id);
		Additive c = this.additiveDao.getAdditive(id);
		
		if (c == null) {
			throw new NotFoundException(404, "Additive " + id + " not found");
		}
		
		Date now = new Date();
		if (c.getAdditiveProductionOrderList() != null) {
			for(AdditiveProductionOrder apo : c.getAdditiveProductionOrderList()) {
				if(now.after(apo.getProductionOrder().getDelivery_date()))
					throw new ConflictException(4101, "Cannot delete additive: " + id + ", production order already delivered.");
			}
		}
		this.additiveDao.delete(c);
	}
}