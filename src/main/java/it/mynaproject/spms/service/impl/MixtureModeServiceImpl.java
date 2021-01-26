package it.mynaproject.spms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.spms.dao.MixtureModeDao;
import it.mynaproject.spms.domain.MixtureMode;
import it.mynaproject.spms.domain.ProductionOrder;
import it.mynaproject.spms.exception.ConflictException;
import it.mynaproject.spms.exception.NotFoundException;
import it.mynaproject.spms.model.MixtureModeJson;
import it.mynaproject.spms.service.MixtureModeService;

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

		MixtureMode c = this.mixtureModeDao.checkMixtureModeExists(input.getName(), null);
		if(c != null) {
			throw new ConflictException(5001, "MixtureMode " + input.getName() + " already registered with id: " + c.getId());
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

		MixtureMode c = this.mixtureModeDao.checkMixtureModeExists(input.getName(), id);
		if(c != null) {
			throw new ConflictException(5001, "MixtureMode " + input.getName() + " already registered with id: " + c.getId());
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
