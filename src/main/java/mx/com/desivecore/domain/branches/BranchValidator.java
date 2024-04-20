package mx.com.desivecore.domain.branches;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.domain.branches.models.BranchPhone;
import mx.com.desivecore.domain.branches.ports.BranchPersistencePort;

@Log
public class BranchValidator {

	public String validOperativeDataTCreate(Branch branch, BranchPersistencePort branchPersistencePort) {
		log.info("INIT validOperativeDataTCreate()");
		String validations = "";

		if (branch == null)
			return "Datos inválidos";

		validations = validRequiredFields(branch, validations);
		validations = validPhoneNumberList(branch, validations);

		Branch branchSearch = branchPersistencePort.findBranchByName(branch.getName());
		validations += branchSearch != null ? "El nombre ingresado ya existe" : "";

		return validations;
	}

	private String validPhoneNumberList(Branch branch, String validations) {
		if (branch.getPhones() != null) {
			for (BranchPhone branchPhone : branch.getPhones()) {

				validations += branchPhone.getPhone() == null ? " -Número invalido"  : "";
				validations += branchPhone.getPhone() != null
						? validNumberString(branchPhone.getPhone()) ? "" : " -El télefono no puede contener caracteres"
						: "";

				validations += branchPhone.getExtension() != null ? !branchPhone.getExtension().isEmpty()
						? validNumberString(branchPhone.getExtension()) ? "" : " -Extensión invalida"
						: "" : "";

			}
		}
		return validations;
	}

	public String validOperativeDataToUpdate(Branch branch, BranchPersistencePort branchPersistencePort) {
		log.info("INIT validOperativeDataToUpdate()");
		String validations = "";

		if (branch == null)
			return "Datos inválidos";

		validations = validRequiredFields(branch, validations);
		validations = validPhoneNumberList(branch, validations);

		Branch branchSearch = branchPersistencePort.findBranchByNameAndIdNot(branch.getName(), branch.getBranchId());
		validations += branchSearch != null ? " -El nombre ingresado ya existe" : "";

		return validations;
	}

	private String validRequiredFields(Branch branch, String validations) {
		validations += validString("Nombre", branch.getName());
		validations += validString("Calle", branch.getStreet());
		validations += validString("Número Exterior", branch.getExternalNumber());
		validations += validString("Municipio/Delegación", branch.getMunicipality());
		validations += validString("Colonia", branch.getColony());
		validations += validString("Código Postal", branch.getCp());
		validations += validString("Estado", branch.getState());
		return validations;
	}

	private String validString(String fieldName, String value) {
		String validations = "";
		validations = value == null ? "- El campo " + fieldName + " es requerido" : validations;
		validations = value != null ? value.isEmpty() ? "- El campo " + fieldName + " es requerido" : validations
				: validations;
		return validations;
	}

	private boolean validNumberString(String number) {
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher matcher = pattern.matcher(number);
		return matcher.matches();
	}
}
