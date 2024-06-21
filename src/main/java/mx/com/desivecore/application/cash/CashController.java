package mx.com.desivecore.application.cash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.cash.models.CashSearchParams;
import mx.com.desivecore.domain.cash.models.ClosingCash;
import mx.com.desivecore.domain.cash.models.EntryMovementRecord;
import mx.com.desivecore.domain.cash.models.ExitMovementRecord;
import mx.com.desivecore.domain.cash.models.OpeningCash;
import mx.com.desivecore.domain.cash.models.PaymentMovementRecord;
import mx.com.desivecore.domain.cash.ports.CashServicePort;

@Log
@RestController
@RequestMapping("/cash")
public class CashController {

	@Autowired
	private CashServicePort cashServicePort;

	@GetMapping("/find-active")
	public ResponseEntity<?> findOpeningCashByUserLogged() {
		log.info("INIT findOpeningCashByUserLogged()");
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String userLogged = loggedInUser.getName();
		ResponseModel response = cashServicePort.viewOpeningCashSummaryByUserLogged(userLogged);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/new-opening")
	public ResponseEntity<?> createOpeningCas(@RequestBody OpeningCash openingCash) {
		log.info("INIT createOpeningCas()");
		log.info(String.format("PARAMS: [%s]", openingCash.toString()));
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String userLogged = loggedInUser.getName();
		ResponseModel response = cashServicePort.createOpeningCash(openingCash, userLogged);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/new-entry/movement")
	public ResponseEntity<?> createEntryMovement(@RequestBody EntryMovementRecord entryMovementRecord) {
		log.info("INIT createEntryMovement()");
		log.info(String.format("PARAMS: [%s]", entryMovementRecord.toString()));
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String userLogged = loggedInUser.getName();
		ResponseModel response = cashServicePort.createEntryMovementCash(entryMovementRecord, userLogged);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/new-exit/movement")
	public ResponseEntity<?> createExitMovementCash(@RequestBody ExitMovementRecord exitMovementRecord) {
		log.info("INIT createExitMovementCash()");
		log.info(String.format("PARAMS: [%s]", exitMovementRecord.toString()));
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String userLogged = loggedInUser.getName();
		ResponseModel response = cashServicePort.createExitMovementCash(exitMovementRecord, userLogged);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/new-paymenmt/movement")
	public ResponseEntity<?> createPaymentMovementCash(@RequestBody PaymentMovementRecord paymentMovementRecord) {
		log.info("INIT createPaymentMovementCash()");
		log.info(String.format("PARAMS: [%s]", paymentMovementRecord.toString()));
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String userLogged = loggedInUser.getName();
		ResponseModel response = cashServicePort.createPaymentMovementCash(paymentMovementRecord, userLogged);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/summary-closing")
	public ResponseEntity<?> generateClosingSummary() {
		log.info("INIT generateClosingSummary()");
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String userLogged = loggedInUser.getName();
		ResponseModel response = cashServicePort.generateClosingSummaryByUserLogged(userLogged);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/new-closing")
	public ResponseEntity<?> createClosingCash(@RequestBody ClosingCash closingCash) {
		log.info("INIT createClosingCash()");
		log.info(String.format("PARAMS: [%s]", closingCash.toString()));
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String userLogged = loggedInUser.getName();
		ResponseModel response = cashServicePort.createClosingCash(closingCash, userLogged);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/search-ro/{folio}")
	public ResponseEntity<?> findRemissionOutputByFolio(@PathVariable String folio) {
		log.info("INIT findRemissionOutputByFolio()");
		log.info(String.format("PARAMS: [folio: %s]", folio));
		ResponseModel response = cashServicePort.searchRemissionOutputSummaryByFolio(folio);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-all/branch")
	public ResponseEntity<?> viewBranchActiveList() {
		log.info("INIT viewBranchActiveList()");
		ResponseModel response = cashServicePort.viewAllBranchActive();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-all/accounting-type")
	public ResponseEntity<?> viewAccountingTypeList() {
		log.info("INIT viewAccountingTypeList()");
		ResponseModel response = cashServicePort.viewAllAccountingType();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/search")
	public ResponseEntity<?> searchOpeningCashByParams(@RequestBody CashSearchParams cashSearchParams) {
		log.info("INIT searchOpeningCashByParams()");
		log.info(String.format("PARAMS: [%s]", cashSearchParams.toString()));
		ResponseModel response = cashServicePort.viewOpeningCashByParams(cashSearchParams);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-detail/{openingCashId}")
	public ResponseEntity<?> viewMovementOpeningCashById(@PathVariable Long openingCashId) {
		log.info("INIT viewMovementOpeningCashById()");
		log.info(String.format("PARAMS: [openingCashId: %s]", openingCashId.toString()));
		ResponseModel response = cashServicePort.viewMovementOpeningCashById(openingCashId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}