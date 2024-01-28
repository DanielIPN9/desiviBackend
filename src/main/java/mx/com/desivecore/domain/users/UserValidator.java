package mx.com.desivecore.domain.users;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.users.models.UserModel;
import mx.com.desivecore.domain.users.ports.UserPersistencePort;

@Log
public class UserValidator {

	public String validOperativeDataToCreate(UserModel user, UserPersistencePort userPersistencePort) {
		log.info("INIT validOperativeDataToCreate()");
		String validations = "";
		validations = validRequiredFields(user, validations, false);
		if (validations.isEmpty()) {
			UserModel userSearch = userPersistencePort.findUserByEmail(user.getEmail(), true);
			validations += userSearch != null ? "- El correo ingresado ya existe" : "";
		}
		return validations;
	}

	public String validOperativeDataToUpdate(UserModel user, UserPersistencePort userPersistencePort) {
		log.info("INIT validOperativeDataToCreate()");
		String validations = "";
		validations = validRequiredFields(user, validations, true);
		if (validations.isEmpty()) {
			UserModel userSearch = userPersistencePort.findUserByEmailAndIdNot(user.getEmail(), user.getUserId());
			validations += userSearch != null ? "- El correo ingresado ya existe" : "";
		}
		return validations;
	}

	private String validString(String fieldName, String value) {
		String validations = "";
		validations = value == null ? "- El campo " + fieldName + " es requerido" : validations;
		validations = value != null ? value.isEmpty() ? "- El campo " + fieldName + " es requerido" : validations
				: validations;
		return validations;
	}

	private String validRequiredFields(UserModel user, String validations, boolean excludePassword) {
		validations += validString("Nombre", user.getName());
		validations += validString("Primer apellido", user.getFirstSurname());
		validations += validString("Segundo apellido", user.getSecondSurname());
		validations += validString("email", user.getEmail());
		if (!excludePassword)
			validations += validString("Contraseña", user.getPassword());
		return validations;
	}

}
