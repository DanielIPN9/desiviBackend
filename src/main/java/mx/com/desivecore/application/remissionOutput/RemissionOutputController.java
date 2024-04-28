package mx.com.desivecore.application.remissionOutput;

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
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutput;
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutputSearchParams;
import mx.com.desivecore.domain.remissionOutput.ports.RemissionOutputServicePort;

@Log
@RestController
@RequestMapping("/remission-output")
public class RemissionOutputController {

	@Autowired
	private RemissionOutputServicePort remissionOutputServicePort;

	@PostMapping("/create")
	public ResponseEntity<?> createRemissionOutput(@RequestBody RemissionOutput remissionOutput) {
		log.info("INIT createRemissionOutput()");
		log.info(String.format("PARAMS:[%s]", remissionOutput.toString()));
		ResponseModel response = remissionOutputServicePort.createRemissionOutput(remissionOutput);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-detail/{remissionOutputId}")
	public ResponseEntity<?> viewRemissionById(@PathVariable Long remissionOutputId) {
		log.info("INIT viewRemissionById()");
		log.info(String.format("PARAMS:[remissionOutputId: %s]", remissionOutputId.toString()));
		ResponseModel response = remissionOutputServicePort.viewRemissionById(remissionOutputId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/search")
	public ResponseEntity<?> searchRemissionOutputByParams(
			@RequestBody RemissionOutputSearchParams remissionOutputSearchParams) {
		log.info("INIT searchRemissionOutputByParams()");
		log.info(String.format("PARAMS:[%s]", remissionOutputSearchParams.toString()));
		ResponseModel response = remissionOutputServicePort.searchRemissionOutputByParams(remissionOutputSearchParams);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-all")
	public ResponseEntity<?> viewAllByUser() {
		log.info("INIT viewAllByUser()");
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String emailUser = loggedInUser.getName();
		ResponseModel response = remissionOutputServicePort.viewAllByUserLogin(emailUser);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateRemissionOutputById(@RequestBody RemissionOutput remissionOutput) {
		log.info("INIT updateRemissionOutputById()");
		log.info(String.format("PARAMS:[%s]", remissionOutput.toString()));
		ResponseModel response = remissionOutputServicePort.updateRemissionOutput(remissionOutput);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/generate/document/{remissionOutputId}")
	public ResponseEntity<?> generateRemissionDocumentById(@PathVariable Long remissionOutputId) {
		log.info("INIT generateRemissionDocumentById()");
		log.info(String.format("PARAMS:[remissionOutputId: %s]", remissionOutputId.toString()));
		ResponseModel response = remissionOutputServicePort.generateRemissionDocumentById(remissionOutputId);
		byte[] reporte = (byte[]) response.getData();
		String encodedString = Base64.getEncoder().encodeToString(reporte);
		return new ResponseEntity<>(new ResponseModel(encodedString), HttpStatus.OK);
	}

	@GetMapping("/view-all/client")
	public ResponseEntity<?> viewClientActiveList() {
		log.info("INIT viewClientActiveList()");
		ResponseModel response = remissionOutputServicePort.viewClientActiveList();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-all/branch")
	public ResponseEntity<?> viewBranchActiveList() {
		log.info("INIT viewBranchActiveList()");
		ResponseModel response = remissionOutputServicePort.viewBranchActiveList();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-all/product/{branchId}")
	public ResponseEntity<?> viewProductListByBranchId(@PathVariable Long branchId) {
		log.info("INIT viewBranchActiveList()");
		log.info(String.format("PARAMS:[branchId: %s]", branchId.toString()));
		ResponseModel response = remissionOutputServicePort.viewProductActiveList(branchId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
