package mx.com.desivecore.application.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.clients.models.Client;
import mx.com.desivecore.domain.clients.ports.ClientServicePort;

@Log
@RestController
@RequestMapping("/client")
public class ClientController {

	@Autowired
	private ClientServicePort clientServicePort;

	@PostMapping("/create")
	public ResponseEntity<?> createClient(@RequestBody Client client) {
		log.info("INIT createClient()");
		log.info(String.format("PARAMS: [%s]", client.toString()));
		ResponseModel response = clientServicePort.createClient(client);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-all")
	public ResponseEntity<?> viewAllClient() {
		log.info("INIT viewAllClient()");
		ResponseModel response = clientServicePort.viewAllClient();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-detail/{clientId}")
	public ResponseEntity<?> viewClientDetailById(@PathVariable Long clientId) {
		log.info("INIT viewClientDetailById()");
		log.info(String.format("PARAMS: [clientId: %s]", clientId.toString()));
		ResponseModel response = clientServicePort.viewClientDetailById(clientId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateClientById(@RequestBody Client client) {
		log.info("INIT updateClientById()");
		log.info(String.format("PARAMS: [%s]", client.toString()));
		ResponseModel response = clientServicePort.updateClientById(client);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
