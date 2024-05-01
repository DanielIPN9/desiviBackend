package mx.com.desivecore.application.remissionEntry;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntry;
import mx.com.desivecore.domain.remissionEntry.models.RemissionSearchParams;
import mx.com.desivecore.domain.remissionEntry.ports.RemissionEntryServicePort;

@Log
@RestController
@RequestMapping("/remission-entry")
public class RemissionEntryController {

	@Autowired
	private RemissionEntryServicePort remissionEntryServicePort;

	@PostMapping("/create")
	public ResponseEntity<?> createRemissionEntry(@RequestBody RemissionEntry remissionEntry) {
		log.info("INIT createRemissionEntry()");
		log.info(String.format("PARAMS: [%s]", remissionEntry.toString()));
		ResponseModel response = remissionEntryServicePort.createRemissionEntry(remissionEntry);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-detail/{remissionEntryId}")
	public ResponseEntity<?> viewRemissionById(@PathVariable Long remissionEntryId) {
		log.info("INIT viewRemissionById()");
		log.info(String.format("PARAMS: [%s]", remissionEntryId.toString()));
		ResponseModel response = remissionEntryServicePort.viewRemissionById(remissionEntryId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/cancel/{remissionEntryId}")
	public ResponseEntity<?> cancelRemissionById(@PathVariable Long remissionEntryId) {
		log.info("INIT viewRemissionById()");
		log.info(String.format("PARAMS: [%s]", remissionEntryId.toString()));
		ResponseModel response = remissionEntryServicePort.cancelRemissionById(remissionEntryId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-all")
	public ResponseEntity<?> viewAllByUser() {
		log.info("INIT viewAllByUser()");
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String emailUser = loggedInUser.getName();
		ResponseModel response = remissionEntryServicePort.viewAllByUserLogin(emailUser);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/search")
	public ResponseEntity<?> searchRemissionEntryByParams(@RequestBody RemissionSearchParams remissionSearchParams) {
		log.info("INIT searchRemissionEntryByParams()");
		log.info(String.format("PARAMS: [%s]", remissionSearchParams.toString()));
		ResponseModel response = remissionEntryServicePort.searchRemissionEntryByParams(remissionSearchParams);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateRemissionEntry(@RequestBody RemissionEntry remissionEntry) {
		log.info("INIT updateRemissionEntry()");
		log.info(String.format("PARAMS: [%s]", remissionEntry.toString()));
		ResponseModel response = remissionEntryServicePort.updateRemissionEntry(remissionEntry);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-history/{remissionEntryId}")
	public ResponseEntity<?> viewRemissionHistoryById(@PathVariable Long remissionEntryId) {
		log.info("INIT viewRemissionHistoryById()");
		log.info(String.format("PARAMS: [%s]", remissionEntryId.toString()));
		ResponseModel response = remissionEntryServicePort.viewRemissionHistoryById(remissionEntryId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/generate/document/{remissionEntryId}")
	public ResponseEntity<?> generateRemissionDocumentById(@PathVariable Long remissionEntryId) {
		log.info("INIT generateRemissionDocumentById()");
		log.info(String.format("PARAMS: [%s]", remissionEntryId.toString()));
		ResponseModel response = remissionEntryServicePort.generateRemissionDocumentById(remissionEntryId);
		byte[] reporte = (byte[]) response.getData();
		String encodedString = Base64.getEncoder().encodeToString(reporte);
		return new ResponseEntity<>(new ResponseModel(encodedString), HttpStatus.OK);
	}

	@GetMapping("/view-all/sipplier")
	public ResponseEntity<?> viewSupplierActiveList() {
		log.info("INIT viewSupplierActiveList()");
		ResponseModel response = remissionEntryServicePort.viewSupplierActiveList();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-all/branch")
	public ResponseEntity<?> viewBranchActiveList() {
		log.info("INIT viewBranchActiveList()");
		ResponseModel response = remissionEntryServicePort.viewBranchActiveList();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-all/product")
	public ResponseEntity<?> viewProductActiveList() {
		log.info("INIT viewProductActiveList()");
		ResponseModel response = remissionEntryServicePort.viewProductActiveList();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
