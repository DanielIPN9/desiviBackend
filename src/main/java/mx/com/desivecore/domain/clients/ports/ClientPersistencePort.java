package mx.com.desivecore.domain.clients.ports;

import java.util.List;

import mx.com.desivecore.domain.clients.models.Client;

public interface ClientPersistencePort {

	Client saveClient(Client client);

	List<Client> viewAllClients();

	Client findClientById(Long clientId);

	Client findClientByRfc(String rfc);

	Client findClientByRfcAndIdNot(String rfc, Long clientId);

	Client findClientByEmail(String email);

	Client findClientByEmailAndIdNot(String email, Long clientId);

}
