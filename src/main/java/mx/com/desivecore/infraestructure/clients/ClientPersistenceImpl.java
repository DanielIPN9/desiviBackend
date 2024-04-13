package mx.com.desivecore.infraestructure.clients;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.clients.models.Client;
import mx.com.desivecore.domain.clients.ports.ClientPersistencePort;
import mx.com.desivecore.infraestructure.clients.converters.ClientConverter;
import mx.com.desivecore.infraestructure.clients.entities.ClientEntity;
import mx.com.desivecore.infraestructure.clients.repositories.ClientRepository;

@Log
@Service
public class ClientPersistenceImpl implements ClientPersistencePort {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ClientConverter clientConverter;

	@Override
	public Client saveClient(Client client) {
		try {
			log.info("INIT saveClient()");
			ClientEntity clientEntity = clientConverter.clientToClientEntity(client);
			clientEntity = clientRepository.save(clientEntity);
			return clientConverter.clientEntityToClient(clientEntity);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<Client> viewAllClients() {
		try {
			log.info("INIT viewAllClients()");
			List<ClientEntity> clientEntityList = clientRepository.findAllByState(true);
			return clientConverter.clientEntityListToClientList(clientEntityList);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public Client findClientById(Long clientId) {
		try {
			log.info("INIT findClientById()");
			Optional<ClientEntity> clientOptional = clientRepository.findById(clientId);
			if (clientOptional.isPresent())
				return clientConverter.clientEntityToClient(clientOptional.get());
			return null;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public Client findClientByRfc(String rfc) {
		try {
			log.info("INIT findClientByRfc()");
			Optional<ClientEntity> clientOptional = clientRepository.findByRfc(rfc);
			if (clientOptional.isPresent())
				return clientConverter.clientEntityToClient(clientOptional.get());
			return null;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public Client findClientByRfcAndIdNot(String rfc, Long clientId) {
		try {
			log.info("INIT findClientByRfcAndIdNot()");
			Optional<ClientEntity> clientOptional = clientRepository.findByRfcAndIdNot(rfc, clientId);
			if (clientOptional.isPresent())
				return clientConverter.clientEntityToClient(clientOptional.get());
			return null;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public Client findClientByEmail(String email) {
		try {
			log.info("INIT findClientByEmail()");
			Optional<ClientEntity> clientOptional = clientRepository.findByEmail(email);
			if (clientOptional.isPresent())
				return clientConverter.clientEntityToClient(clientOptional.get());
			return null;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public Client findClientByEmailAndIdNot(String email, Long clientId) {
		try {
			log.info("INIT findClientByEmailAndIdNot()");
			Optional<ClientEntity> clientOptional = clientRepository.findByEmailAndIdNot(email, clientId);
			if (clientOptional.isPresent())
				return clientConverter.clientEntityToClient(clientOptional.get());
			return null;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public boolean changeStatusById(Long clientId, boolean status) {
		try {
			log.info("INIT changeStatusById()");
			int updatedRow = clientRepository.enableById(clientId, status);
			if (updatedRow <= 0)
				return false;
			return true;
		} catch (Exception e) {
			log.info("EXCEPTION: " + e.getMessage());
			return false;
		}
	}

}
