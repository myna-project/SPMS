package it.mynaproject.spms.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class BaseDaoImpl {

	@PersistenceContext
	protected EntityManager em;
}
