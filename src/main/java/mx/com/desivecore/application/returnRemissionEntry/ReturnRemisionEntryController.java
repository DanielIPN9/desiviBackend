package mx.com.desivecore.application.returnRemissionEntry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntry;
import mx.com.desivecore.domain.returnRemissionEntry.models.ReturnRESearchParams;
import mx.com.desivecore.domain.returnRemissionEntry.ports.ReturnREServicePort;

@Log
@RestController
@RequestMapping("/return-remission-entry")
public class ReturnRemisionEntryController {

	@Autowired
	private ReturnREServicePort returnREServicePort;

	@PostMapping("/search-re/{folio}")
	public ResponseEntity<?> searchRemissionEntrySummaryByFolio(@PathVariable String folio) {
		log.info("INIT searchRemissionEntrySummaryByFolio()");
		log.info(String.format("PARAMS: [folio: %s]", folio));
		ResponseModel response = returnREServicePort.searchRemissionEntrySummaryByFolio(folio);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<?> generateReturnRemissionEntry(@RequestBody RemissionEntry remissionEntry) {
		log.info("INIT searchRemissionEntrySummaryByFolio()");
		log.info(String.format("PARAMS: [%s]", remissionEntry.toString()));
		ResponseModel response = returnREServicePort.generateReturnRemissionEntry(remissionEntry);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-detail/{returnREId}")
	public ResponseEntity<?> viewDetailById(@PathVariable Long returnREId) {
		log.info("INIT viewDetailById()");
		log.info(String.format("PARAMS: [returnREId: %s]", returnREId.toString()));
		ResponseModel response = returnREServicePort.viewDetailByReturnREId(returnREId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/document/{returnREId}")
	public ResponseEntity<?> generateReturnREDocumentById(@PathVariable Long returnREId) {
		log.info("INIT generateReturnREDocumentById()");
		log.info(String.format("PARAMS: [returnREId: %s]", returnREId.toString()));
		ResponseModel response = returnREServicePort.generateReturnREDocumentById(returnREId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-all")
	public ResponseEntity<?> viewAllReturnRemissionEntry() {
		log.info("INIT viewAllReturnRemissionEntry()");
		ResponseModel response = returnREServicePort.viewAllReturnRemissionEntry();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/search")
	public ResponseEntity<?> searchReturnRemissionEntryByParams(
			@RequestBody ReturnRESearchParams returnRESearchParams) {
		log.info("INIT searchReturnRemissionEntryByParams()");
		log.info(String.format("PARAMS: [%s]", returnRESearchParams.toString()));
		ResponseModel response = returnREServicePort.searchReturnRemissionEntryByParams(returnRESearchParams);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
