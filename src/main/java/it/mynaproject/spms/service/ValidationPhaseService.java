package it.mynaproject.spms.service;

import it.mynaproject.spms.domain.ValidationPhase;
import it.mynaproject.spms.model.ValidationPhaseJson;

public interface ValidationPhaseService {

	public ValidationPhase getValidationPhase(Integer id, Integer sid);
	public void persist(ValidationPhase validationPhase);
	public ValidationPhase createValidationPhaseFromJson(Integer id, ValidationPhaseJson input);
	public void update(ValidationPhase validationPhase);
	public ValidationPhase updateValidationPhaseFromJson(Integer id, Integer sid, ValidationPhaseJson input);
	public void deleteValidationPhaseById(Integer id, Integer sid);
}