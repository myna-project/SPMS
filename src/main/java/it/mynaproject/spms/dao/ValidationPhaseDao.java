package it.mynaproject.spms.dao;

import java.util.List;

import it.mynaproject.spms.domain.ValidationPhase;

public interface ValidationPhaseDao {

	public void persist(ValidationPhase validationPhase);
	public void update(ValidationPhase validationPhase);
	public void delete(ValidationPhase validationPhase);
	public ValidationPhase getValidationPhase(Integer id);
	public List<ValidationPhase> getValidationPhases();
}
