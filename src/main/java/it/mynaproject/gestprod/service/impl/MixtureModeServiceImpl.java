package it.mynaproject.gestprod.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.gestprod.dao.MixtureModeDao;
import it.mynaproject.gestprod.domain.MixtureMode;
import it.mynaproject.gestprod.domain.ProductionOrder;
import it.mynaproject.gestprod.exception.*;
import it.mynaproject.gestprod.model.MixtureModeJson;
import it.mynaproject.gestprod.service.MixtureModeService;

@Service
public class MixtureModeServiceImpl implements MixtureModeService {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MixtureModeDao mixtureModeDao;

	@Transactional(readOnly = true)
	@Override
	public MixtureMode getMixtureMode(Integer id) {

		MixtureMode p = this.mixtureModeDao.getMixtureMode(id);
		if (p == null)
			throw new NotFoundException(404, "MixtureMode " + id + " not found");
		return p;
	}

	@Transactional(readOnly = true)
	@Override
	public List<MixtureMode> getMixtureModes() {

		List<MixtureMode> pList = new ArrayList<MixtureMode>();
		for (MixtureMode p : this.mixtureModeDao.getMixtureModes())
			pList.add(p);

		return pList;
	}

	@Transactional
	@Override
	public void persist(MixtureMode mixtureMode) {
		this.mixtureModeDao.persist(mixtureMode);
	}

	@Transactional
	@Override
	public MixtureMode createMixtureModeFromJson(MixtureModeJson input) {

		log.info("Creating new mixtureMode: {}", input.toString());

		if(this.mixtureModeDao.getMixtureMode(input.getId()) != null) {
			throw new ConflictException(5001, "MixtureMode " + input.getName() + " already registered with id: " + input.getId());
			
		}
		MixtureMode mixtureMode = new MixtureMode();
		mixtureMode.populateMixtureModeFromInput(input);

		this.persist(mixtureMode);

		return mixtureMode;
	}

	@Transactional
	@Override
	public void update(MixtureMode mixtureMode) {
		this.mixtureModeDao.update(mixtureMode);
	}

	@Transactional
	@Override
	public MixtureMode updateMixtureModeFromJson(Integer id, MixtureModeJson input) {

		log.info("Updating mixtureMode with id: {}", id);

		for(MixtureMode e: this.mixtureModeDao.getMixtureModes()) {
			if(e.getName().equals(input.getName())) {
				throw new ConflictException(5001, "MixtureMode name " + input.getName() + " already registered with id: " + input.getId());
			}
		}
		MixtureMode mixtureMode = this.getMixtureMode(id);
		mixtureMode.populateMixtureModeFromInput(input);

		this.update(mixtureMode);

		return mixtureMode;
	}

	@Transactional
	@Override
	public void deleteMixtureModeById(Integer id) {

		log.info("Deleting mixtureMode: {}", id);
		MixtureMode c = this.mixtureModeDao.getMixtureMode(id);
		
		if (c == null) {
			throw new NotFoundException(404, "MixtureMode " + id + " not found");
		}
		
		Date now = new Date();
		if (c.getProductionOrders() != null) {
			for(ProductionOrder po : c.getProductionOrders()) {
				if(now.after(po.getDelivery_date()))
					throw new ConflictException(5101, "Cannot delete mixtureMode: " + id + ", production order already delivered.");
			}
		}
		this.mixtureModeDao.delete(c);
	}
}
