package mx.com.desivecore.application.quarantine;

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
import mx.com.desivecore.domain.quarantine.models.ProductQuarantineAction;
import mx.com.desivecore.domain.quarantine.models.QuarantineSearchParams;
import mx.com.desivecore.domain.quarantine.ports.QuarantineServicePort;

@Log
@RestController
@RequestMapping("/cuarantine")
public class QuarantineController {

	@Autowired
	private QuarantineServicePort quarantineServicePort;

	@GetMapping("/view-all")
	public ResponseEntity<?> viewAllStatus() {
		log.info("INIT viewAllStatus()");
		ResponseModel response = quarantineServicePort.viewAllQuarantineStatus();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/search")
	public ResponseEntity<?> viewStatusByParams(@RequestBody QuarantineSearchParams quarantineSearchParams) {
		log.info("INT viewStatusByParams()");
		log.info(String.format("%s", quarantineSearchParams.toString()));
		ResponseModel response = quarantineServicePort.viewQuarantineStatusByParams(quarantineSearchParams);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-detail/{productId}")
	public ResponseEntity<?> viewProductQuarantineDetailById(@PathVariable Long productId) {
		log.info("INT viewProductQuarantineDetailById()");
		log.info(String.format("productId: %s", productId.toString()));
		ResponseModel response = quarantineServicePort.viewProductQuarantineDetailById(productId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-movement/{quarantineId}")
	public ResponseEntity<?> generateProductMovementSummaryByQuarantineId(@PathVariable Long quarantineId) {
		log.info("INT generateProductMovementSummaryByQuarantineId()");
		log.info(String.format("quarantineId: %s", quarantineId.toString()));
		ResponseModel response = quarantineServicePort.generateProductMovementByQuarantineId(quarantineId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/change/location")
	public ResponseEntity<?> changeProductLocation(@RequestBody ProductQuarantineAction productQuarantineAction) {
		log.info("INT changeProductLocation()");
		log.info(String.format("%s", productQuarantineAction.toString()));
		ResponseModel response = quarantineServicePort.changeProductLocation(productQuarantineAction);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-all/actions")
	public ResponseEntity<?> viewQuarantineActionList() {
		log.info("INT viewQuarantineActionList()");
		ResponseModel response = quarantineServicePort.viewQuarantineActionList();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-all/branch")
	public ResponseEntity<?> viewAllBranchSummaryList() {
		log.info("INT viewAllBranchSummaryList()");
		ResponseModel response = quarantineServicePort.viewAllBranchSummary();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-all/product")
	public ResponseEntity<?> viewAllProductActiveList() {
		log.info("INT viewAllProductActiveList()");
		ResponseModel response = quarantineServicePort.viewAllProductSummary();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
