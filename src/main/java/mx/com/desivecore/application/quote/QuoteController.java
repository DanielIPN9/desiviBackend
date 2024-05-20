package mx.com.desivecore.application.quote;

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
import mx.com.desivecore.domain.quote.models.QuoteOrder;
import mx.com.desivecore.domain.quote.models.QuoteSearchParams;
import mx.com.desivecore.domain.quote.ports.QuoteServicePort;

@Log
@RestController
@RequestMapping("/quote")
public class QuoteController {

	@Autowired
	private QuoteServicePort quoteServicePort;

	@PostMapping("/create")
	public ResponseEntity<?> createQuoteOrder(@RequestBody QuoteOrder quoteOrder) {
		log.info("INIT createQuoteOrder()");
		log.info(String.format("PARAMS: [%s]", quoteOrder.toString()));
		ResponseModel responseModel = quoteServicePort.createQuoteOrder(quoteOrder);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/view-detail/{quoteId}")
	public ResponseEntity<?> viewQouteOrderById(@PathVariable Long quoteId) {
		log.info("INIT viewQouteOrderById()");
		log.info(String.format("PARAMS: [quoteId: %s]", quoteId.toString()));
		ResponseModel responseModel = quoteServicePort.viewQouteOrderById(quoteId);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateQuoteOrder(@RequestBody QuoteOrder quoteOrder) {
		log.info("INIT updateQuoteOrder()");
		log.info(String.format("PARAMS: [%s]", quoteOrder.toString()));
		ResponseModel responseModel = quoteServicePort.updateQuoteOrder(quoteOrder);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@PostMapping("/convert/{quoteId}")
	public ResponseEntity<?> covertQuoteOrderById(@PathVariable Long quoteId) {
		log.info("INIT covertQuoteOrderById()");
		log.info(String.format("PARAMS: [quoteId: %s]", quoteId.toString()));
		ResponseModel responseModel = quoteServicePort.covertQuoteOrder(quoteId);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@PostMapping("/search")
	public ResponseEntity<?> searchQuoteOrderByParams(@RequestBody QuoteSearchParams quoteSearchParams) {
		log.info("INIT searchQuoteOrderByParams()");
		log.info(String.format("PARAMS: [%s]", quoteSearchParams.toString()));
		ResponseModel responseModel = quoteServicePort.searchQuoteOrderByParams(quoteSearchParams);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/view-all/client")
	public ResponseEntity<?> viewClientActiveList() {
		log.info("INIT viewClientActiveList()");
		ResponseModel responseModel = quoteServicePort.viewClientActiveList();
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/view-all/branch")
	public ResponseEntity<?> viewBranchActiveList() {
		log.info("INIT viewBranchActiveList()");
		ResponseModel responseModel = quoteServicePort.viewBranchActiveList();
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/view-all/product")
	public ResponseEntity<?> viewProductActiveList() {
		log.info("INIT viewProductActiveList()");
		ResponseModel responseModel = quoteServicePort.viewProductActiveList();
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	@GetMapping("/document/{quoteId}")
	public ResponseEntity<?> generateDocumentByQuoteOrderId(@PathVariable Long quoteId) {
		log.info("INIT generateDocumentByQuoteOrderId()");
		log.info(String.format("PARAMS: [quoteId: %s]", quoteId.toString()));
		ResponseModel response = quoteServicePort.generateQuoteOrderDocumentById(quoteId);
		byte[] reporte = (byte[]) response.getData();
		String encodedString = Base64.getEncoder().encodeToString(reporte);
		return new ResponseEntity<>(new ResponseModel(encodedString), HttpStatus.OK);

	}
}