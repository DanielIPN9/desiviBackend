package mx.com.desivecore.application.returnRemissionOutput;

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
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutput;
import mx.com.desivecore.domain.returnRemissionOutput.models.ReturnROSearchParams;
import mx.com.desivecore.domain.returnRemissionOutput.ports.ReturnROServicePort;

@Log
@RestController
@RequestMapping("/return-remission-output")
public class ReturnRemisionOutputController {

	@Autowired
	private ReturnROServicePort returnROServicePort;

	@PostMapping("/search-ro/{folio}")
	public ResponseEntity<?> searchRemissionOutputSummaryByFolio(@PathVariable String folio) {
		log.info("INIT searchRemissionOutputSummaryByFolio()");
		log.info(String.format("PARAMS: [folio: %s ]", folio));
		ResponseModel response = returnROServicePort.searchRemissionOutputSummaryByFolio(folio);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<?> generateReturnRemissionOutput(@RequestBody RemissionOutput remissionOutput) {
		log.info("INIT generateReturnRemissionOutput()");
		log.info(String.format("PARAMS: [ %s ]", remissionOutput.toString()));
		ResponseModel response = returnROServicePort.generateReturnRemissionOutput(remissionOutput);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-detail/{returnROId}")
	public ResponseEntity<?> viewReturnRODetailById(@PathVariable Long returnROId) {
		log.info("INIT viewReturnRODetailById()");
		log.info(String.format("PARAMS: [ returnROId %s ]", returnROId.toString()));
		ResponseModel response = returnROServicePort.viewReturnRODetailById(returnROId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/document/{returnROId}")
	public ResponseEntity<?> generateReturnRODocumentById(@PathVariable Long returnROId) {
		log.info("INIT generateReturnRODocumentById()");
		log.info(String.format("PARAMS: [ returnROId %s ]", returnROId.toString()));
		ResponseModel response = returnROServicePort.generateReturnRODocumentById(returnROId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-all")
	public ResponseEntity<?> viewAllReturnRemissionOutput() {
		log.info("INIT viewAllReturnRemissionOutput()");
		ResponseModel response = returnROServicePort.viewAllReturnRemissionOutput();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/search")
	public ResponseEntity<?> searchReturnRemissionOutputByParams(
			@RequestBody ReturnROSearchParams returnROSearchParams) {
		log.info("INIT searchReturnRemissionOutputByParams()");
		log.info(String.format("PARAMS: [ %s ]", returnROSearchParams.toString()));
		ResponseModel response = returnROServicePort.searchReturnRemissionOutputByParams(returnROSearchParams);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
