package mx.com.desivecore.domain.clients;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.clients.models.Client;
import mx.com.desivecore.domain.clients.ports.ClientPersistencePort;

@Log
public class ClientValidator {

	public String validOperativeDataToCreate(Client client, ClientPersistencePort clientPersistencePort) {
		log.info("INIT validOperativeDataToCreate()");
		String validations = "";

		if (client == null)
			return "Datos invalidos";

		validations = validRequiredFields(client, validations);

		Client clientSearch = null;

		if (client.getEmail() != null) {
			clientSearch = clientPersistencePort.findClientByEmail(client.getEmail());
			validations += clientSearch != null ? "-El correo ingresado ya esta registrado a un cliente" : "";
		}

		if (client.getRfc() != null) {
			clientSearch = clientPersistencePort.findClientByRfc(client.getRfc());
			validations += clientSearch != null ? "El RFC ingresado ya esta registrado a un cliente" : "";
		}
		return validations;
	}

	public String validOperativeDataToUpdate(Client client, ClientPersistencePort clientPersistencePort) {
		log.info("INIT validOperativeDataToUpdate()");
		String validations = "";

		if (client == null)
			return "Datos invalidos";

		validations = validRequiredFields(client, validations);

		Client clientSearch = null;

		if (client.getEmail() != null) {
			clientSearch = clientPersistencePort.findClientByEmailAndIdNot(client.getEmail(), client.getClientId());
			validations += clientSearch != null ? "-El correo ingresado ya esta registrado a un cliente" : "";
		}

		if (client.getRfc() != null) {
			clientSearch = clientPersistencePort.findClientByRfcAndIdNot(client.getRfc(), client.getClientId());
			validations += clientSearch != null ? "El RFC ingresado ya esta registrado a un cliente" : "";
		}
		return validations;
	}

	private String validRequiredFields(Client client, String validations) {
		validations += validString("Nombre/Razón Social", client.getBusinessName());
		validations += validString("RFC", client.getRfc());
		validations += validString("Nombre de contacto", client.getContactName());
		validations += validString("Correo", client.getEmail());

		validations += validString("Calle", client.getStreet());
		validations += validString("Número exterior", client.getExternalNumber());
		validations += validString("Delegación/Municipio", client.getMunicipality());
		validations += validString("Colonia", client.getColony());
		validations += validString("CP", client.getCp());
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
