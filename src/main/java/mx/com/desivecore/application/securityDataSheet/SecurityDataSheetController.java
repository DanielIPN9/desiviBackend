package mx.com.desivecore.application.securityDataSheet;

import java.util.Base64;

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
import mx.com.desivecore.domain.securityDataSheet.models.SecurityDataSheet;
import mx.com.desivecore.domain.securityDataSheet.ports.SecurityDataSheetServicePort;

@Log
@RestController
@RequestMapping("/security-data-sheet")
public class SecurityDataSheetController {

	@Autowired
	private SecurityDataSheetServicePort securityDataSheetServicePort;

	@PostMapping("/create")
	public ResponseEntity<?> createSecurityDataShet(@RequestBody SecurityDataSheet securityDataSheet) {
		log.info("INIT createSecurityDataShet()");
		log.info(String.format("PARAMS:[%s]", securityDataSheet.toString()));
		ResponseModel response = securityDataSheetServicePort.createSecurityDataShet(securityDataSheet);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-all/product")
	public ResponseEntity<?> viewProductActiveList() {
		log.info("INIT viewProductActiveList");
		ResponseModel response = securityDataSheetServicePort.viewProductActiveList();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-detail/{secDataSheetId}")
	public ResponseEntity<?> viewSecurityDataSheetById(@PathVariable Long secDataSheetId) {
		log.info("INIT viewSecurityDataSheetById()");
		log.info(String.format("PARAMS:[secDataSheetId: secDataSheetId]", secDataSheetId.toString()));
		ResponseModel response = securityDataSheetServicePort.viewSecurityDataSheetById(secDataSheetId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-all")
	public ResponseEntity<?> viewAllSecurityDataSheet() {
		log.info("INIT viewAllSecurityDataSheet()");
		ResponseModel response = securityDataSheetServicePort.viewAllSecurityDataSheet();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateSecurityDataSheetById(@RequestBody SecurityDataSheet securityDataSheet) {
		log.info("INIT updateSecurityDataSheetById()");
		log.info(String.format("PARAMS:[%s]", securityDataSheet.toString()));
		ResponseModel response = securityDataSheetServicePort.updateSecurityDataSheetById(securityDataSheet);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/generate-document/{secDataSheetId}")
	public ResponseEntity<?> generateDocumentSecurityDataSheetById(@PathVariable Long secDataSheetId) {
		log.info("INIT viewSecurityDataSheetById()");
		log.info(String.format("PARAMS:[secDataSheetId: secDataSheetId]", secDataSheetId.toString()));
		ResponseModel response = securityDataSheetServicePort.generateDocumentSecurityDataSheetById(secDataSheetId);
		byte[] reporte = (byte[]) response.getData();
		String encodedString = Base64.getEncoder().encodeToString(reporte);
		return new ResponseEntity<>(new ResponseModel(encodedString), HttpStatus.OK);
	}

}
