package mx.com.desivecore.application.certificate;

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
import mx.com.desivecore.domain.certificate.models.ProductCertificate;
import mx.com.desivecore.domain.certificate.ports.CertificateServicePort;
import mx.com.desivecore.domain.remissionEntry.models.RemissionSearchParams;

@Log
@RestController
@RequestMapping("/certificate")
public class CertificateController {

	@Autowired
	private CertificateServicePort certificateServicePort;

	@PostMapping("/view/remission-summary")
	public ResponseEntity<?> viewRemissionEntrySummary(@RequestBody RemissionSearchParams remissionSearchParams) {
		log.info("INIT viewRemissionEntrySummary()");
		log.info(String.format("PARAMS:[%s]", remissionSearchParams.toString()));
		ResponseModel response = certificateServicePort.viewRemissionEntrySummary(remissionSearchParams);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-detail/{remissionId}/{productId}")
	public ResponseEntity<?> viewCertificateByRemissionAndProduct(@PathVariable Long remissionId,
			@PathVariable Long productId) {
		log.info("INIT viewCertificateByRemissionAndProduct()");
		log.info(String.format("PARAMS:[remissionId: %s , productId: %s]", remissionId.toString(),
				productId.toString()));
		ResponseModel response = certificateServicePort.viewCertificateByRemissionAndProduct(remissionId, productId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<?> createCertificate(@RequestBody ProductCertificate productCertificate) {
		log.info("INIT createCertificate()");
		log.info(String.format("PARAMS:[%s]", productCertificate.toString()));
		ResponseModel response = certificateServicePort.createCertificate(productCertificate);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateCertificateById(@RequestBody ProductCertificate productCertificate) {
		log.info("INIT updateCertificateById()");
		log.info(String.format("PARAMS:[%s]", productCertificate.toString()));
		ResponseModel response = certificateServicePort.updateCertificateById(productCertificate);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@GetMapping("/view-all/sipplier")
	public ResponseEntity<?> viewSupplierActiveList() {
		log.info("INIT viewSupplierActiveList()");
		ResponseModel response = certificateServicePort.viewSupplierActiveList();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-all/branch")
	public ResponseEntity<?> viewBranchActiveList() {
		log.info("INIT viewBranchActiveList()");
		ResponseModel response = certificateServicePort.viewBranchActiveList();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/generate-document/{certificateId}")
	public ResponseEntity<?> generateDocumentCertificateById(@PathVariable Long certificateId) {
		log.info("INIT generateDocumentCertificateById()");
		log.info(String.format("PARAMS:[certificateId: %s ]", certificateId.toString()));
		ResponseModel response = certificateServicePort.generateDocumentCertificateById(certificateId);
		byte[] reporte = (byte[]) response.getData();
		String encodedString = Base64.getEncoder().encodeToString(reporte);
		return new ResponseEntity<>(new ResponseModel(encodedString), HttpStatus.OK);
	}
}
