package mx.com.desivecore.application.reports;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.reports.models.search.AccountingReportParams;
import mx.com.desivecore.domain.reports.models.search.InventoryParamsReport;
import mx.com.desivecore.domain.reports.models.search.RemissionEntryParamsReport;
import mx.com.desivecore.domain.reports.models.search.RemissionOutputParamsReport;
import mx.com.desivecore.domain.reports.ports.ReportServicePort;

@Log
@RestController
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private ReportServicePort reportServicePort;

	@PostMapping("/remission-entry")
	public ResponseEntity<?> generateRemissionEntryReport(
			@RequestBody RemissionEntryParamsReport remissionEntryParamsReport) {
		log.info("INIT generateRemissionEntryReport()");
		log.info(String.format("PARMAS:[%s]", remissionEntryParamsReport.toString()));
		ResponseModel response = reportServicePort.generateRemissionEntryReport(remissionEntryParamsReport);
		byte[] reporte = (byte[]) response.getData();
		String encodedString = Base64.getEncoder().encodeToString(reporte);
		return new ResponseEntity<>(new ResponseModel(encodedString), HttpStatus.OK);
	}

	@PostMapping("/remission-output")
	public ResponseEntity<?> generateRemissionOutputReport(
			@RequestBody RemissionOutputParamsReport remissionOutputParamsReport) {
		log.info("INIT generateRemissionOutputReport()");
		log.info(String.format("PARMAS:[%s]", remissionOutputParamsReport.toString()));
		ResponseModel response = reportServicePort.generateRemissionOutputReport(remissionOutputParamsReport);
		byte[] reporte = (byte[]) response.getData();
		String encodedString = Base64.getEncoder().encodeToString(reporte);
		return new ResponseEntity<>(new ResponseModel(encodedString), HttpStatus.OK);
	}

	@PostMapping("/inventory")
	public ResponseEntity<?> generateInventoryReport(@RequestBody InventoryParamsReport inventoryParamsReport) {
		log.info("INIT generateRemissionOutputReport()");
		log.info(String.format("PARMAS:[%s]", inventoryParamsReport.toString()));
		ResponseModel response = reportServicePort.generateInventoryReport(inventoryParamsReport);
		byte[] reporte = (byte[]) response.getData();
		String encodedString = Base64.getEncoder().encodeToString(reporte);
		return new ResponseEntity<>(new ResponseModel(encodedString), HttpStatus.OK);
	}

	@PostMapping("/accounting-balance")
	public ResponseEntity<?> generateAccountingReport(
			@RequestBody AccountingReportParams accountingReportSearchParams) {
		log.info("INIT generateAccountingReport()");
		log.info(String.format("PARMAS:[%s]", accountingReportSearchParams.toString()));
		ResponseModel response = reportServicePort.generateAccountingReport(accountingReportSearchParams);
		byte[] reporte = (byte[]) response.getData();
		String encodedString = Base64.getEncoder().encodeToString(reporte);
		return new ResponseEntity<>(new ResponseModel(encodedString), HttpStatus.OK);
	}

	@GetMapping("/view-all/sipplier")
	public ResponseEntity<?> viewSupplierActiveList() {
		log.info("INIT viewSupplierActiveList()");
		ResponseModel response = reportServicePort.viewAllSupplier();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-all/branch")
	public ResponseEntity<?> viewBranchActiveList() {
		log.info("INIT viewBranchActiveList()");
		ResponseModel response = reportServicePort.viewAllBranch();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-all/product")
	public ResponseEntity<?> viewProductActiveList() {
		log.info("INIT viewProductActiveList()");
		ResponseModel response = reportServicePort.viewAllProduct();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-all/client")
	public ResponseEntity<?> viewClientActiveList() {
		log.info("INIT viewClientActiveList()");
		ResponseModel response = reportServicePort.viwAllClient();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
