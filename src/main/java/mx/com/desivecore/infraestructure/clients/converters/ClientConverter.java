package mx.com.desivecore.infraestructure.clients.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.clients.models.Client;
import mx.com.desivecore.infraestructure.clients.entities.ClientEntity;

@Component
public class ClientConverter {

	public ClientEntity clientToClientEntity(Client client) {

		ClientEntity clientEntity = new ClientEntity();

		clientEntity.setClientId(client.getClientId());
		clientEntity.setBusinessName(client.getBusinessName());
		clientEntity.setRfc(client.getRfc());
		clientEntity.setContactName(client.getContactName());
		clientEntity.setContactNumber(client.getContactNumber());
		clientEntity.setEmail(client.getEmail());
		clientEntity.setStreet(client.getStreet());
		clientEntity.setExternalNumber(client.getExternalNumber());
		clientEntity.setInternalNumber(client.getInternalNumber());
		clientEntity.setMunicipality(client.getMunicipality());
		clientEntity.setColony(client.getColony());
		clientEntity.setCp(client.getCp());
		clientEntity.setState(client.getState());
		return clientEntity;
	}

	public Client clientEntityToClient(ClientEntity clientEntity) {

		Client client = new Client();

		client.setClientId(clientEntity.getClientId());
		client.setBusinessName(clientEntity.getBusinessName());
		client.setRfc(clientEntity.getRfc());
		client.setContactName(clientEntity.getContactName());
		client.setContactNumber(clientEntity.getContactNumber());
		client.setEmail(clientEntity.getEmail());
		client.setStreet(clientEntity.getStreet());
		client.setExternalNumber(clientEntity.getExternalNumber());
		client.setInternalNumber(clientEntity.getInternalNumber());
		client.setMunicipality(clientEntity.getMunicipality());
		client.setColony(clientEntity.getColony());
		client.setCp(clientEntity.getCp());
		client.setState(clientEntity.getState());

		return client;
	}

	public List<ClientEntity> clientListToClientEntityList(List<Client> clientList) {
		List<ClientEntity> clientEntityList = new ArrayList<>();
		for (Client client : clientList) {
			clientEntityList.add(clientToClientEntity(client));
		}
		return clientEntityList;
	}

	public List<Client> clientEntityListToClientList(List<ClientEntity> clientEntityList) {
		List<Client> clientList = new ArrayList<>();
		for (ClientEntity clientEntity : clientEntityList) {
			clientList.add(clientEntityToClient(clientEntity));
		}
		return clientList;
	}
}
