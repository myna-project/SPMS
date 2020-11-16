package it.mynaproject.gestprod.model;

import java.util.ArrayList;

import java.util.Base64;
import java.util.List;

import it.mynaproject.gestprod.domain.*;

public class JsonUtil {
	
	public static AdditiveJson additiveToAdditiveJson(final Additive d) {

		final AdditiveJson dj = new AdditiveJson();
		dj.setId(d.getId());
		dj.setName(d.getName());

		return dj;
	}
	
	public static AdditiveProductionOrderJson additiveProductionOrderToAdditiveProductionOrderJson(final AdditiveProductionOrder d) {

		final AdditiveProductionOrderJson dj = new AdditiveProductionOrderJson();
		dj.setAdditive_id(d.getAdditive().getId());
		dj.setProduction_order_code_id(d.getProductionOrder().getId());
		dj.setWeight_additive(d.getWeight_additive());

		return dj;
	}
	
	public static CustomerJson customerToCustomerJson(final Customer d) {

		final CustomerJson dj = new CustomerJson();
		dj.setId(d.getId());
		dj.setName(d.getName());

		return dj;
	}

	
	public static MixtureModeJson mixtureToMixtureModeJson(final MixtureMode d) {

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
	
	public static ProductionOrderJson rawMaterialToProductionOrderJson(final ProductionOrder d) {

		final ProductionOrderJson dj = new ProductionOrderJson();
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
		
		// many-to-one relationships
		dj.setCustomer_id(d.getCustomer().getId());
		dj.setExpected_mixture_mode_id(d.getMixtureMode().getId());
		dj.setPackaging_id(d.getPackaging().getId());
		dj.setRaw_material_id(d.getRawMaterial().getId());
		if(d.getAdditiveProductionOrderList() != null) {
			List<Integer> al = new ArrayList<>();
			for(AdditiveProductionOrder ap: d.getAdditiveProductionOrderList()) {
				al.add(ap.getAdditive().getId());
			}
			dj.setAdditivesId(al);
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
	
/** Production Phases **/
	
	// to be used on all classes except WorkingPhase (because of shifts)
	private static void loadPhaseJson(PhaseJson dj, final Phase d) {
		dj.setId(d.getId());
		dj.setProduction_order_id(d.getProductionOrder().getId());
		dj.setStart_time(d.getStart_time());
		dj.setEnd_time(dj.getEnd_time());
	}
	
	public static SettingPhaseJson settingPhaseToSettingPhaseJson(final SettingPhase d) {
		
		final SettingPhaseJson dj = new SettingPhaseJson();
		loadPhaseJson(dj,d);
		dj.setEffective_mixture_mode_id(d.getEffective_mixture_mode_id());
		dj.setEffective_mixture_temperature(d.getEffective_mixture_temperature());
		
		return dj;
	}
	
	public static SystemPreparationPhaseJson systemPreparationPhaseToSystemPreparationPhaseJson(final SystemPreparationPhase d) {
		
		final SystemPreparationPhaseJson dj = new SystemPreparationPhaseJson();
		loadPhaseJson(dj,d);
		
		return dj;
	}
	
	public static CleaningPhaseJson cleaningPhaseToCleaningPhaseJson(final CleaningPhase d) {
		
		final CleaningPhaseJson dj = new CleaningPhaseJson();
		loadPhaseJson(dj,d);
		
		return dj;
	}
	
	public static ValidationPhaseJson cleaningPhaseToValidationPhaseJson(final ValidationPhase d) {
		
		final ValidationPhaseJson dj = new ValidationPhaseJson();
		loadPhaseJson(dj,d);
		
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
	
	// handle a single shift
	private static void loadWorkingPhaseUserJson(WorkingPhaseUserJson dj, WorkingPhaseUser d) {
		dj.setWorking_phase_id(d.getWorkingPhase().getId());
		dj.setUser_id(d.getUser().getId());
		dj.setStart_time(d.getStart_time());
		dj.setEnd_time(d.getEnd_time());
	}
	
	// WorkingPhase composed of list of shifts
	public static WorkingPhaseJson workingPhaseToWoringPhaseJson(final WorkingPhase d) {
		final WorkingPhaseJson dj = new WorkingPhaseJson();
		dj.setId(d.getId());
		dj.setProduction_order_id(d.getProductionOrder().getId());
		
		if(d.getWorkingPhaseUserList() != null) {
			List<WorkingPhaseUserJson> wsl = new ArrayList<>();
			for(WorkingPhaseUser wu : d.getWorkingPhaseUserList()) {
				final WorkingPhaseUserJson s = new WorkingPhaseUserJson();
				loadWorkingPhaseUserJson(s, wu);
			}
		}
		
		return dj;
	}
}