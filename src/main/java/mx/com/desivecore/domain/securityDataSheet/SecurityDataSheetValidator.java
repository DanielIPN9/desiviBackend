package mx.com.desivecore.domain.securityDataSheet;

import java.util.List;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.securityDataSheet.models.ChemicalIdentification;
import mx.com.desivecore.domain.securityDataSheet.models.ChemicalSpecification;
import mx.com.desivecore.domain.securityDataSheet.models.SecurityDataSheet;
import mx.com.desivecore.domain.securityDataSheet.ports.SecurityDataSheetPersistencePort;

@Log
public class SecurityDataSheetValidator {

	public String validOperativeDataToCreate(SecurityDataSheetPersistencePort securityDataSheetPersistencePort,
			SecurityDataSheet securityDataSheet) {
		log.info("validOperativeDataToCreate()");
		String validations = "";
		validations = validSecctionMaterialSecurity(securityDataSheet, validations);
		validations = validRequiredFields(securityDataSheet, validations);
		validations += securityDataSheet.getChIdentifications() == null
				? "-Debe ingresar un valor para la lista de Identificación"
				: validOperativeDataChemicalIdentification(securityDataSheet.getChIdentifications());
		validations += securityDataSheet.getChSpecifications() == null
				? " -Debe ingresar un valor para la lista de Especificaciones Fisico/Quimicas"
				: validOperativeDataChemicalSpecification(securityDataSheet.getChSpecifications());
		if (validations.isEmpty()) {
			SecurityDataSheet securityDataSheetSearh = securityDataSheetPersistencePort
					.viewSecurityDataSheetByProductId(securityDataSheet.getProduct().getProductId());
			validations += securityDataSheetSearh != null ? " -Ya existe un registro para el producto seleccionado"
					: "";
		}
		return validations;
	}

	public String validOperativeDataToUpdate(SecurityDataSheetPersistencePort securityDataSheetPersistencePort,
			SecurityDataSheet securityDataSheet) {
		log.info("validOperativeDataToUpdate()");
		String validations = "";
		validations = validSecctionMaterialSecurity(securityDataSheet, validations);
		validations = validRequiredFields(securityDataSheet, validations);
		validations += securityDataSheet.getChIdentifications() == null
				? "-Debe ingresar un valor para la lista de Identificación"
				: validOperativeDataChemicalIdentification(securityDataSheet.getChIdentifications());
		validations += securityDataSheet.getChSpecifications() == null
				? " -Debe ingresar un valor para la lista de Especificaciones Fisico/Quimicas"
				: validOperativeDataChemicalSpecification(securityDataSheet.getChSpecifications());
		if (validations.isEmpty()) {
			SecurityDataSheet securityDataSheetSearh = securityDataSheetPersistencePort
					.viewSecurityDataSheetByProductIdAndSecDataSheetIdNot(securityDataSheet.getSecurityDataSheetId(),
							securityDataSheet.getProduct().getProductId());
			validations += securityDataSheetSearh != null ? " -Ya existe un registro para el producto seleccionado"
					: "";
		}
		return validations;
	}

	private String validOperativeDataChemicalIdentification(List<ChemicalIdentification> chIdentifications) {
		int invalidLines = 0;
		if (chIdentifications.size() == 0)
			return "-Debe ingresar un valor para la lista de Identificación";
		for (ChemicalIdentification chemicalIdentification : chIdentifications) {
			if ((chemicalIdentification.getName() == null || chemicalIdentification.getName().isEmpty())
					&& chemicalIdentification.getValue() == null || chemicalIdentification.getValue().isEmpty())
				invalidLines++;
		}
		return invalidLines > 0 ? " -Debe ingresar un nombre y valor distintos de valores vacios" : "";
	}

	private String validOperativeDataChemicalSpecification(List<ChemicalSpecification> chSpecifications) {
		int invalidLines = 0;
		if (chSpecifications.size() == 0)
			return "-Debe ingresar un valor para la lista de Identificación";
		for (ChemicalSpecification chemicalSpecification : chSpecifications) {
			if ((chemicalSpecification.getName() == null || chemicalSpecification.getName().isEmpty())
					&& chemicalSpecification.getValue() == null || chemicalSpecification.getValue().isEmpty())
				invalidLines++;
		}
		return invalidLines > 0 ? " -Debe ingresar un nombre y valor distintos de valores vacios" : "";
	}

	private String validRequiredFields(SecurityDataSheet securityDataSheet, String validations) {
		validations += securityDataSheet.getProduct() == null ? " -Producto es requerido" : "";
		validations += validString("Nombre Comercial", securityDataSheet.getTradeName());
		validations += validString("Familia Química", securityDataSheet.getChemicalFamily());
		validations += validString("Formula Química", securityDataSheet.getChemicalFormula());
		validations += validString("Sinónimo/s", securityDataSheet.getSynonym());
		validations += validString("Peso Molecular", securityDataSheet.getMolecularWeight());
		validations += validString("Descripción", securityDataSheet.getDescription());
		validations += validString("Aplicación", securityDataSheet.getApplication());
		validations += validString("Uso Comercial", securityDataSheet.getCommercialUse());
		return validations;
	}

	private String validSecctionMaterialSecurity(SecurityDataSheet securityDataSheet, String validations) {
		validations += securityDataSheet.getFlammability() == null ? " -Valor Inflamabilidad es requerido" : "";
		validations += securityDataSheet.getFlammability() != null
				? securityDataSheet.getFlammability() < 0 || securityDataSheet.getFlammability() > 4
						? " -Valor flamabilidad no puede ser menor a cero o mayor a 4"
						: ""
				: "";

		validations += securityDataSheet.getReactivity() == null ? " -Valor Reactividad es requerido" : "";
		validations += securityDataSheet.getReactivity() != null
				? securityDataSheet.getReactivity() < 0 || securityDataSheet.getReactivity() > 4
						? " -Valor Reactividad no puede ser menor a cero o mayor a 4"
						: ""
				: "";

		validations += securityDataSheet.getHealth() == null ? " -Valor Salud es requerido" : "";
		validations += securityDataSheet.getHealth() != null
				? securityDataSheet.getHealth() < 0 || securityDataSheet.getHealth() > 4
						? " -Valor Salud no puede ser menor a cero o mayor a 4"
						: ""
				: "";

		validations += securityDataSheet.getSpecialHazard() == null ? " -Valor Riesgo Especial es requerido" : "";
		validations += securityDataSheet.getSpecialHazard() != null
				? securityDataSheet.getSpecialHazard() < 0 || securityDataSheet.getSpecialHazard() > 4
						? " -Valor Riesgo Especial no puede ser menor a cero o mayor a 4"
						: ""
				: "";
		return validations;
	}

	private String validString(String fieldName, String value) {
		String validations = "";
		validations = value == null ? "-El campo " + fieldName + " es requerido" : validations;
		validations = value != null ? value.isEmpty() ? "-El campo " + fieldName + " es requerido" : validations
				: validations;
		return validations;
	}
}
