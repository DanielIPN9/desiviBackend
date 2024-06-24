package mx.com.desivecore.application.payments;

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
import mx.com.desivecore.domain.payments.accountPayable.models.AccountPayable;
import mx.com.desivecore.domain.payments.accountPayable.models.RemissionEntryBalanceSearch;
import mx.com.desivecore.domain.payments.accountPayable.ports.AccountPayableServicePort;

@Log
@RestController
@RequestMapping("/account-payable")
public class AccountPayableController {

	@Autowired
	private AccountPayableServicePort accountPayableServicePort;

	@GetMapping("/view-month")
	public ResponseEntity<?> findCurrentMonth() {
		log.info("INIT findCurrentMonth()");
		ResponseModel response = accountPayableServicePort.findAllByCurrentMonth();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/search")
	public ResponseEntity<?> findAllByParams(@RequestBody RemissionEntryBalanceSearch remissionEntryBalanceSearch) {
		log.info("INIT findAllByParams()");
		log.info(String.format("PARAMS:[%s]", remissionEntryBalanceSearch.toString()));
		ResponseModel response = accountPayableServicePort.findAllByParams(remissionEntryBalanceSearch);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/new-record")
	public ResponseEntity<?> createRecord(@RequestBody AccountPayable accountPayable) {
		log.info("INIT createRecord()");
		log.info(String.format("PARAMS:[%s]", accountPayable.toString()));
		ResponseModel response = accountPayableServicePort.createRecord(accountPayable);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-detail/{remissionEntryId}")
	public ResponseEntity<?> viewRemissionEntryBalanceDetail(@PathVariable Long remissionEntryId) {
		log.info("INIT viewRemissionEntryBalanceDetail()");
		log.info(String.format("PARAMS:[remissionEntryId: %s]", remissionEntryId.toString()));
		ResponseModel response = accountPayableServicePort.viewRemissionEntryBalanceDetail(remissionEntryId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-all/supplier")
	public ResponseEntity<?> viewAllSuppliersActive() {
		log.info("INIT viewAllSuppliersActive()");
		ResponseModel response = accountPayableServicePort.viewAllSupplierActiveList();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-all/payment-state")
	public ResponseEntity<?> viewAllPaymentStateList() {
		log.info("INIT viewAllPaymentStateList()");
		ResponseModel response = accountPayableServicePort.viewAllPaymentState();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/view-all/accounting-type")
	public ResponseEntity<?> viewAccountingTypeList() {
		log.info("INIT viewAccountingTypeList()");
		ResponseModel response = accountPayableServicePort.viewAllAccountingType();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
