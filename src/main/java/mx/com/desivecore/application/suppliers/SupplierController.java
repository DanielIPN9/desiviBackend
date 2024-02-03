package mx.com.desivecore.application.suppliers;

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
import mx.com.desivecore.domain.suppliers.models.Supplier;
import mx.com.desivecore.domain.suppliers.ports.SupplierServicePort;

@Log
@RestController
@RequestMapping("/supplier")
public class SupplierController {

	@Autowired
	private SupplierServicePort supplierServicePort;

	@PostMapping("/create")
	public ResponseEntity<?> createSupplier(@RequestBody Supplier supplier) {
		log.info("INIT createSupplier()");
		log.info(String.format("PARAMS: [%s]", supplier.toString()));
		ResponseModel response = supplierServicePort.createSupplier(supplier);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-all")
	public ResponseEntity<?> viewAllSupplier() {
		log.info("viewAllSupplier()");
		ResponseModel response = supplierServicePort.viewAllSupplier();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-detail/{supplierId}")
	public ResponseEntity<?> viewSupplierDetailById(@PathVariable Long supplierId) {
		log.info("INIT viewSupplierDetailById()");
		log.info(String.format("PARAMS: [supplierId: %s]", supplierId.toString()));
		ResponseModel response = supplierServicePort.viewSupplierDetailById(supplierId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateSupplierById(@RequestBody Supplier supplier) {
		log.info("INIT updateSupplierById()");
		log.info(String.format("PARAMS: [%s]", supplier.toString()));
		ResponseModel response = supplierServicePort.updateSupplierById(supplier);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/change-status/{status}/{supplierId}")
	public ResponseEntity<?> changeStatusById(@PathVariable String status, @PathVariable Long supplierId) {
		log.info("INIT changeStatusById()");
		log.info(String.format("PARAMS:[status: %s, supplierId: %s]", status, supplierId.toString()));
		ResponseModel responseModel = supplierServicePort.changeStatusById(supplierId, status);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

}
