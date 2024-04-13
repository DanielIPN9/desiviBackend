package mx.com.desivecore.domain.clients;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.clients.models.Client;
import mx.com.desivecore.domain.clients.ports.ClientPersistencePort;
import mx.com.desivecore.domain.clients.ports.ClientServicePort;
import mx.com.desivecore.infraestructure.configuration.exceptions.InternalError;
import mx.com.desivecore.infraestructure.configuration.exceptions.ValidationError;

@Log
@Service
public class ClientServiceImpl implements ClientServicePort {

	@Autowired
	private ClientPersistencePort clientPersistencePort;

	private ClientValidator clientValidator = new ClientValidator();

	@Override
	public ResponseModel createClient(Client client) {

		log.info("INIT createClient()");
		String validations = clientValidator.validOperativeDataToCreate(client, clientPersistencePort);
		if (!validations.isEmpty()) {
			log.warning("BAD PARAMS: " + validations);
			throw new ValidationError(validations);
		}

		Client clientCreated = clientPersistencePort.saveClient(client);
		if (clientCreated == null) {
			log.severe("ERROR ON SAVE CLIENT");
			throw new InternalError();
		}

		return new ResponseModel(clientCreated);
	}

	@Override
	public ResponseModel viewAllClient() {
		log.info("INIT viewAllClient()");
		List<Client> clientList = clientPersistencePort.viewAllClients();
		if (clientList == null)
			return new ResponseModel(new ArrayList<>());
		return new ResponseModel(clientList);
	}

	@Override
	public ResponseModel viewClientDetailById(Long clientId) {
		log.info("INIT viewClientDetailById()");
		Client client = clientPersistencePort.findClientById(clientId);
		if (client == null) {
			log.warning("DATA NOT FOUND");
			throw new ValidationError("El cliente ingresado no existe");
		}
		return new ResponseModel(client);
	}

	@Override
	public ResponseModel updateClientById(Client client) {
		log.info("INIT updateClientById()");
		String validations = clientValidator.validOperativeDataToUpdate(client, clientPersistencePort);
		if (!validations.isEmpty()) {
			log.warning("BAD PARAMS: " + validations);
			throw new ValidationError(validations);
		}

		Client clientUpdated = clientPersistencePort.saveClient(client);
		if (clientUpdated == null) {
			log.severe("ERROR ON SAVE CLIENT");
			throw new InternalError();
		}

		return new ResponseModel(clientUpdated);
	}

	@Override
	public ResponseModel changeStatusById(Long clientId, String status) {
		log.info("INIT changeStatusById()");
		boolean userStatus = status.equals("ACTIVE") ? true : false;
		boolean changeStatus = clientPersistencePort.changeStatusById(clientId, userStatus);
		if (!changeStatus)
			throw new InternalError();
		return new ResponseModel(changeStatus);
	}

}
