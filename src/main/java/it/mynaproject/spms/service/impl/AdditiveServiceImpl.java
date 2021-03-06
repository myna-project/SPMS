package it.mynaproject.spms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.spms.dao.AdditiveDao;
import it.mynaproject.spms.domain.Additive;
import it.mynaproject.spms.domain.AdditiveProductionOrder;
import it.mynaproject.spms.exception.ConflictException;
import it.mynaproject.spms.exception.NotFoundException;
import it.mynaproject.spms.model.AdditiveJson;
import it.mynaproject.spms.service.AdditiveService;

@Service
public class AdditiveServiceImpl implements AdditiveService {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AdditiveDao additiveDao;

	@Transactional(readOnly = true)
	@Override
	public Additive getAdditive(Integer id) {

		Additive p = this.additiveDao.getAdditive(id);
		if (p == null)
			throw new NotFoundException(404, "Additive " + id + " not found");
		return p;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Additive> getAdditives() {

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

		Additive c = this.additiveDao.checkAdditiveExists(input.getName(), null);
		if(c != null) {
			throw new ConflictException(4001, "Additive " + input.getName() + " already registered with id: " + c.getId());
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
	public Additive updateAdditiveFromJson(Integer id, AdditiveJson input) {

		log.info("Updating additive with id: {}", id);

		Additive c = this.additiveDao.checkAdditiveExists(input.getName(), id);
		if(c != null) {
			throw new ConflictException(4001, "Additive " + input.getName() + " already registered with id: " + c.getId());
		}
		
		Additive additive = this.getAdditive(id);
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