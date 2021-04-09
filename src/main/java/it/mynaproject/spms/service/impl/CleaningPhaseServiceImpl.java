package it.mynaproject.spms.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.mynaproject.spms.dao.CleaningPhaseDao;
import it.mynaproject.spms.domain.CleaningPhase;
import it.mynaproject.spms.domain.ProductionOrder;
import it.mynaproject.spms.exception.NotFoundException;
import it.mynaproject.spms.model.CleaningPhaseJson;
import it.mynaproject.spms.service.CleaningPhaseService;
import it.mynaproject.spms.service.ProductionOrderService;
import it.mynaproject.spms.service.UserService;

@Service
public class CleaningPhaseServiceImpl implements CleaningPhaseService {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CleaningPhaseDao cleaningPhaseDao;

	@Autowired
	private UserService userService;

	@Autowired
	private ProductionOrderService productionOrderService;

	@Transactional(readOnly = true)
	@Override
	public CleaningPhase getCleaningPhase(Integer id, Integer sid) {

		CleaningPhase cleaningPhase = null;

		ProductionOrder p = this.productionOrderService.getProductionOrder(id);
		for(CleaningPhase sf : p.getCleaningPhaseList()) {
			if (sf.getId() == sid) {
				cleaningPhase = sf;
				break;
			}
		}

		if (cleaningPhase == null)
			throw new NotFoundException(404, "CleaningPhase " + sid + " not found");

		return cleaningPhase;
	}

	@Transactional
	@Override
	public void persist(CleaningPhase cleaningPhase) {
		this.cleaningPhaseDao.persist(cleaningPhase);
	}

	@Transactional
	@Override
	public CleaningPhase createCleaningPhaseFromJson(Integer id, CleaningPhaseJson input) {

		log.info("Creating new cleaningPhase: {}", input.toString());

		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		ProductionOrder po = this.productionOrderService.getProductionOrder(id);

		CleaningPhase cleaningPhase = new CleaningPhase();
		cleaningPhase.populateCleaningPhaseFromInput(input, po, this.userService.getUserByUsername(user.getUsername()));

		this.persist(cleaningPhase);

		return cleaningPhase;
	}

	@Transactional
	@Override
	public void update(CleaningPhase cleaningPhase) {
		this.cleaningPhaseDao.update(cleaningPhase);
	}
	
	@Transactional
	@Override
	public CleaningPhase updateCleaningPhaseFromJson(Integer id, Integer sid, CleaningPhaseJson input) {

		log.info("Updating cleaningPhase with id: {}", id);

		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		CleaningPhase cleaningPhase = this.getCleaningPhase(id, sid);
		cleaningPhase.populateCleaningPhaseFromInput(input, this.productionOrderService.getProductionOrder(input.getProductionOrder().getId()), this.userService.getUserByUsername(user.getUsername()));

		this.update(cleaningPhase);

		return cleaningPhase;
	}

	@Transactional
	@Override
	public void deleteCleaningPhaseById(Integer id, Integer sid) {

		log.info("Deleting cleaningPhase: {}", id);

		this.cleaningPhaseDao.delete(this.getCleaningPhase(id, sid));
	}
}