package it.mynaproject.gestprod.service;

import it.mynaproject.gestprod.domain.ValidationPhase;
import it.mynaproject.gestprod.model.ValidationPhaseJson;

public interface ValidationPhaseService {

	public ValidationPhase getValidationPhase(Integer id, Integer sid, Boolean isAdmin);
	public void persist(ValidationPhase validationPhase);
	public ValidationPhase createValidationPhaseFromJson(Integer id, ValidationPhaseJson input);
	public void update(ValidationPhase validationPhase);
	public ValidationPhase updateValidationPhaseFromJson(Integer id, Integer sid, ValidationPhaseJson input, Boolean isAdmin);
	public void deleteValidationPhaseById(Integer id);
}