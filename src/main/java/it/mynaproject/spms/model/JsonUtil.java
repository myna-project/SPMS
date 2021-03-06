package it.mynaproject.spms.model;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import it.mynaproject.spms.domain.*;

public class JsonUtil {

	public static AdditiveJson additiveToAdditiveJson(final Additive d) {

		final AdditiveJson dj = new AdditiveJson();
		dj.setId(d.getId());
		dj.setName(d.getName());

		return dj;
	}

	public static AdditiveProductionOrderJson additiveProductionOrderToAdditiveProductionOrderJson(final AdditiveProductionOrder d) {

		final AdditiveProductionOrderJson dj = new AdditiveProductionOrderJson();
		dj.setId(d.getId());
		dj.setAdditive(additiveToAdditiveJson(d.getAdditive()));
		dj.setWeight_additive(d.getWeight_additive());

		return dj;
	}

	public static CustomerJson customerToCustomerJson(final Customer d) {

		final CustomerJson dj = new CustomerJson();
		dj.setId(d.getId());
		dj.setName(d.getName());

		return dj;
	}

	public static MixtureModeJson mixtureModeToMixtureModeJson(final MixtureMode d) {

		final MixtureModeJson dj = new MixtureModeJson();
		dj.setId(d.getId());
		dj.setName(d.getName());

		return dj;
	}

	public static PackagingJson packagingToPackagingJson(final Packaging d) {

		final PackagingJson dj = new PackagingJson();
		dj.setId(d.getId());
		dj.setPackaging_mode(d.getPackaging_mode());

		return dj;
	}

	public static RawMaterialJson rawMaterialToRawMaterialJson(final RawMaterial d) {

		final RawMaterialJson dj = new RawMaterialJson();
		dj.setId(d.getId());
		dj.setName(d.getName());

		return dj;
	}

	public static ProductionOrderJson productionOrderToProductionOrderJson(final ProductionOrder d, Boolean dumpPhases) {

		final ProductionOrderJson dj = new ProductionOrderJson();
		dj.setCompleted(d.getCompleted());
		dj.setId(d.getId());
		dj.setDdt_date(d.getDdt_date());
		dj.setDdt_number(d.getDdt_number());
		dj.setDelivery_date(d.getDelivery_date());
		dj.setDensity_raw_material(d.getDensity_raw_material());
		dj.setDry_residue(d.getDry_residue());
		dj.setExpected_mixture_temperature(d.getExpected_mixture_temperature());
		dj.setExpected_quantity_finished_product(d.getExpected_quantity_finished_product());
		dj.setProduction_number_lot(d.getProduction_number_lot());
		dj.setProduction_order_code(d.getProduction_order_code());
		dj.setProduction_order_date(d.getProduction_order_date());
		dj.setTons_raw_material(d.getTons_raw_material());
		dj.setWeight_raw_material(d.getWeight_raw_material());
		if (d.getCustomer() != null)
			dj.setCustomer(customerToCustomerJson(d.getCustomer()));
		if (d.getMixtureMode() != null)
			dj.setExpected_mixture_mode(mixtureModeToMixtureModeJson(d.getMixtureMode()));
		if (d.getPackaging() != null)
			dj.setPackaging(packagingToPackagingJson(d.getPackaging()));
		if (d.getRawMaterial() != null)
			dj.setRaw_material(rawMaterialToRawMaterialJson(d.getRawMaterial()));
		if (d.getAdditiveProductionOrderList() != null) {
			List<AdditiveProductionOrderJson> al = new ArrayList<>();
			for (AdditiveProductionOrder ap : d.getAdditiveProductionOrderList()) {
				al.add(additiveProductionOrderToAdditiveProductionOrderJson(ap));
			}
			dj.setAdditives(al);
		}

		// if requested, dump all production phases (as their respective json model)
		if (dumpPhases) {
			if (d.getSettingPhaseList() != null) {
				List<SettingPhaseJson> spl = new ArrayList<>();
				for (SettingPhase sp : d.getSettingPhaseList()) {
					spl.add(settingPhaseToSettingPhaseJson(sp, false));
				}
				dj.setSetting_phases(spl);
			}
			if (d.getSystemPreparationPhaseList() != null) {
				List<SystemPreparationPhaseJson> sppl = new ArrayList<>();
				for (SystemPreparationPhase spp : d.getSystemPreparationPhaseList()) {
					sppl.add(systemPreparationPhaseToSystemPreparationPhaseJson(spp, false));
				}
				dj.setSystem_preparation_phases(sppl);
			}
			if (d.getCleaningPhaseList() != null) {
				List<CleaningPhaseJson> cpl = new ArrayList<>();
				for (CleaningPhase cp : d.getCleaningPhaseList()) {
					cpl.add(cleaningPhaseToCleaningPhaseJson(cp, false));
				}
				dj.setCleaning_phases(cpl);
			}
			if (d.getWorkingPhaseList() != null) {
				List<WorkingPhaseJson> wpl = new ArrayList<>();
				for (WorkingPhase wp : d.getWorkingPhaseList()) {
					wpl.add(workingPhaseToWorkingPhaseJson(wp, false, true));
				}
				dj.setWorking_phases(wpl);
			}
			if (d.getValidationPhaseList() != null) {
				List<ValidationPhaseJson> vpl = new ArrayList<>();
				for (ValidationPhase vp : d.getValidationPhaseList()) {
					vpl.add(validationPhaseToValidationPhaseJson(vp, false));
				}
				dj.setValidation_phases(vpl);
			}
		}

		return dj;
	}

	public static RoleJson roleToRoleJson(final Role d) {

		final RoleJson dj = new RoleJson();
		dj.setId(d.getId());
		dj.setName(d.getName());
		dj.setDescription(d.getDescription());
		if (d.getUsers() != null) {
			List<String> sr = new ArrayList<>();
			for (User u : d.getUsers())
				sr.add(u.getUsername());
			dj.setUsers(sr);
		}

		return dj;
	}

	public static UserJson userToUserJson(final User d, final Boolean getRoles) {

		final UserJson dj = new UserJson();
		dj.setId(d.getId());
		dj.setUsername(d.getUsername());
		dj.setName(d.getName());
		dj.setSurname(d.getSurname());
		dj.setEnabled(d.getEnabled());
		dj.setEmail(d.getEmail());
		if (d.getAvatar() != null)
			dj.setAvatar(Base64.getEncoder().encodeToString(d.getAvatar()));
		dj.setLang(d.getLang());
		dj.setStyle(d.getStyle());
		if (getRoles && (d.getRoleList() != null)) {
			List<Integer> rolesId = new ArrayList<>();
			for (Role r : d.getRoleList())
				rolesId.add(r.getId());
			dj.setRoles(rolesId);
		}

		return dj;
	}

	public static SettingPhaseJson settingPhaseToSettingPhaseJson(final SettingPhase d, boolean loadProductionOrder) {

		final SettingPhaseJson dj = new SettingPhaseJson();
		loadPhaseJson(dj, d, loadProductionOrder);
		if (d.getEffective_mixture_mode() != null) {
			dj.setEffective_mixture_mode(mixtureModeToMixtureModeJson(d.getEffective_mixture_mode()));
		    dj.setEffective_mixture_temperature(d.getEffective_mixture_temperature());
		}

		return dj;
	}

	public static SystemPreparationPhaseJson systemPreparationPhaseToSystemPreparationPhaseJson(final SystemPreparationPhase d, boolean loadProductionOrder) {

		final SystemPreparationPhaseJson dj = new SystemPreparationPhaseJson();
		loadPhaseJson(dj, d, loadProductionOrder);

		return dj;
	}

	public static CleaningPhaseJson cleaningPhaseToCleaningPhaseJson(final CleaningPhase d, boolean loadProductionOrder) {

		final CleaningPhaseJson dj = new CleaningPhaseJson();
		loadPhaseJson(dj, d, loadProductionOrder);

		return dj;
	}

	public static ValidationPhaseJson validationPhaseToValidationPhaseJson(final ValidationPhase d, boolean loadProductionOrder) {

		final ValidationPhaseJson dj = new ValidationPhaseJson();
		loadPhaseJson(dj, d, loadProductionOrder);

		dj.setHumidity_finished_product(d.getHumidity_finished_product());
		dj.setDensity_finished_product(d.getDensity_finished_product());
		dj.setPackaging_state(d.getPackaging_state());
		dj.setSieve_quantity(d.getSieve_quantity());
		dj.setChimney_quantity(d.getChimney_quantity());
		dj.setTower_entry_temperature(d.getTower_entry_temperature());
		dj.setTower_intern_temperature(d.getTower_intern_temperature());
		dj.setCyclon_entry_temperature(d.getCyclon_entry_temperature());
		dj.setNote(d.getNote());

		return dj;
	}

	public static WorkingPhaseJson workingPhaseToWorkingPhaseJson(final WorkingPhase d, boolean loadProductionOrder, Boolean loadMeasures) {

		final WorkingPhaseJson dj = new WorkingPhaseJson();
		loadPhaseJson(dj, d, loadProductionOrder);

		if (loadMeasures) {
			if (d.getWorkingPhaseMeasureList() != null) {
				for (WorkingPhaseMeasure wm : d.getWorkingPhaseMeasureList()) {
					final WorkingPhaseMeasureJson s = new WorkingPhaseMeasureJson();
					loadWorkingPhaseMeasureJson(s, wm);
					if (dj.getMeasures() == null) {
						List<WorkingPhaseMeasureJson> ml = new ArrayList<>();
						dj.setMeasures(ml);
					}
					dj.getMeasures().add(s);
				}
			}
		}

		return dj;
	}

	public static WorkingPhaseMeasureJson workingPhaseMeasureToWorkingPhaseMeasureJson(final WorkingPhaseMeasure d) {

		final WorkingPhaseMeasureJson dj = new WorkingPhaseMeasureJson();
		loadWorkingPhaseMeasureJson(dj, d);

		return dj;
	}

	private static void loadWorkingPhaseMeasureJson(WorkingPhaseMeasureJson dj, WorkingPhaseMeasure d) {

		dj.setId(d.getId());
		dj.setWorkingPhase(workingPhaseToWorkingPhaseJson(d.getWorkingPhase(), false, false));
		dj.setTime(d.getTime());
		dj.setFinished_product_quantity(d.getFinished_product_quantity());
	}

	private static void loadPhaseJson(PhaseJson dj, final Phase d, boolean loadProductionOrder) {

		dj.setId(d.getId());
		if (loadProductionOrder)
			dj.setProductionOrder(productionOrderToProductionOrderJson(d.getProductionOrder(), false));
		dj.setStart_time(d.getStart_time());
		dj.setEnd_time(d.getEnd_time());
	}
}