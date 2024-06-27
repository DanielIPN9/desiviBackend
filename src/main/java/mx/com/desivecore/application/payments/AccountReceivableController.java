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
import mx.com.desivecore.domain.payments.accountReceivable.models.AccountReceivable;
import mx.com.desivecore.domain.payments.accountReceivable.models.RemissionOutputBalanceSearch;
import mx.com.desivecore.domain.payments.accountReceivable.ports.AccountReceivableServicePort;

@Log
@RestController
@RequestMapping("/account-receivable")
public class AccountReceivableController {

	@Autowired
	private AccountReceivableServicePort accountReceivableServicePort;

	@GetMapping("/view-month")
	public ResponseEntity<?> findCurrentMonth() {
		log.info("INIT findCurrentMonth()");
		ResponseModel response = accountReceivableServicePort.findAllByCurrentMonth();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/search")
	public ResponseEntity<?> findAllByParams(@RequestBody RemissionOutputBalanceSearch remissionOutputBalanceSearch) {
		log.info("INIT findAllByParams()");
		log.info(String.format("PARAMS:[%s]", remissionOutputBalanceSearch.toString()));
		ResponseModel response = accountReceivableServicePort.findAllByParams(remissionOutputBalanceSearch);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/new-record")
	public ResponseEntity<?> createRecord(@RequestBody AccountReceivable accountReceivable) {
		log.info("INIT createRecord()");
		log.info(String.format("PARAMS:[%s]", accountReceivable.toString()));
		ResponseModel response = accountReceivableServicePort.createRecord(accountReceivable);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-detail/{remissionOutputId}")
	public ResponseEntity<?> viewRemissionOutputBalanceDetail(@PathVariable Long remissionOutputId) {
		log.info("INIT viewRemissionOutputBalanceDetail()");
		log.info(String.format("PARAMS:[remissionOutputId: %s]", remissionOutputId.toString()));
		ResponseModel response = accountReceivableServicePort.viewRemissionOutputBalanceDetail(remissionOutputId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-all/client")
	public ResponseEntity<?> viewAllClientActive() {
		log.info("INIT viewAllSuppliersActive()");
		ResponseModel response = accountReceivableServicePort.viewAllClientActiveList();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-all/payment-state")
	public ResponseEntity<?> viewAllPaymentStateList() {
		log.info("INIT viewAllPaymentStateList()");
		ResponseModel response = accountReceivableServicePort.viewAllPaymentState();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-all/accounting-type")
	public ResponseEntity<?> viewAccountingTypeList() {
		log.info("INIT viewAccountingTypeList()");
		ResponseModel response = accountReceivableServicePort.viewAllAccountingType();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
