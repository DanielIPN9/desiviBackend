package mx.com.desivecore.domain.security;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.security.models.Rol;
import mx.com.desivecore.domain.security.ports.SecurityPersistencePort;

@Log
public class RoleValidator {

	public String validOperativeDataToCreate(Rol rol, SecurityPersistencePort securityPersistencePort) {
		log.info("INIT validOperativeDataToCreate()");
		String validations = "";
		validations = validRequiredField(rol, validations);
		return validations;
	}

	public String validOperativeDataToUpdate(Rol rol, SecurityPersistencePort securityPersistencePort) {
		log.info("INIT validOperativeDataToUpdate()");
		String validations = "";
		validations = validRequiredField(rol, validations);
		return validations;
	}
	
	private String validRequiredField(Rol rol, String validations) {
		validations += validString("Nombre", rol.getName());
		validations += validString("Descripción", rol.getDescription());
		validations += rol.getPermissions() == null ? "- Seleccione un permiso" : "";
		validations += rol.getPermissions() != null ? rol.getPermissions().isEmpty() ? "- Seleccione un permiso" : ""
				: "";
		return validations;
	}

	private String validString(String fieldName, String value) {
		String validations = "";
		validations = value == null ? "- El campo " + fieldName + " es requerido" : validations;
		validations = value != null ? value.isEmpty() ? "- El campo " + fieldName + " es requerido" : validations
				: validations;
		return validations;
	}

}
