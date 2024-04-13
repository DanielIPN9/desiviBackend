package mx.com.desivecore.domain.clients.ports;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.clients.models.Client;

public interface ClientServicePort {

	ResponseModel createClient(Client client);

	ResponseModel viewAllClient();

	ResponseModel viewClientDetailById(Long clientId);

	ResponseModel updateClientById(Client client);
	
	ResponseModel changeStatusById(Long clientId, String status);

}
