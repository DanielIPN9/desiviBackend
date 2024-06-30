package mx.com.desivecore.reports;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.domain.reports.models.search.AccountingReportParams;
import mx.com.desivecore.domain.reports.models.search.InventoryParamsReport;
import mx.com.desivecore.domain.reports.ports.ReportServicePort;

@Log
@SpringBootTest
public class GenericReportTest {

	@Autowired
	private ReportServicePort reportServicePort;

	@Test
	@DisplayName("VALID INVENTORY REPORT")
	public void validInventoryReport() {
		log.info("INIT validInventoryReport()");

		InventoryParamsReport inventoryParamsReport = new InventoryParamsReport();
		Branch branch = new Branch();
		branch.setBranchId(0L);
		inventoryParamsReport.setBarnch(branch);
		inventoryParamsReport.setFormat("XSL");

		ResponseModel responseReport = reportServicePort.generateInventoryReport(inventoryParamsReport);

		assertAll(

				() -> assertNotEquals(null, responseReport),

				() -> assertNotEquals(null, responseReport.getData()));

		try {
			String date = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss").format(new Date());
			String format = inventoryParamsReport.getFormat().equals("PDF") ? ".pdf" : ".xls";
			FileUtils.writeByteArrayToFile(
					new File("C:\\Users\\Odalis\\Documents\\reports\\inventory_report" + date + format),
					(byte[]) responseReport.getData());
		} catch (IOException e) {
			log.severe(e.getMessage());
		}

	}

	@Test
	@DisplayName("VALID ACCOUNTING GLOBAL REPORT")
	public void validAccountingReportReport() {
		log.info("INIT validAccountingReportReport()");

		AccountingReportParams accountingReportSearchParams = new AccountingReportParams();

		Calendar calendarFrom = Calendar.getInstance();
		calendarFrom.set(Calendar.DAY_OF_MONTH, 1);
		calendarFrom.set(Calendar.MONTH, 0);
		calendarFrom.set(Calendar.YEAR, 2024);

		Date dateFrom = calendarFrom.getTime();
		accountingReportSearchParams.setDateFrom(dateFrom);

		Calendar calendarTo = Calendar.getInstance();
		calendarFrom.set(Calendar.DAY_OF_MONTH, 31);
		calendarFrom.set(Calendar.MONTH, 11);
		calendarFrom.set(Calendar.YEAR, 2024);

		Date dateTo = calendarTo.getTime();
		accountingReportSearchParams.setDateTo(dateTo);

		Branch branch = new Branch();
		branch.setBranchId(0L);

		accountingReportSearchParams.setBranch(branch);

		accountingReportSearchParams.setFormat("XSL");

		ResponseModel responseReport = reportServicePort.generateAccountingReport(accountingReportSearchParams);

		assertAll(

				() -> assertNotEquals(null, responseReport),

				() -> assertNotEquals(null, responseReport.getData()));

		try {
			String date = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss").format(new Date());
			String format = accountingReportSearchParams.getFormat().equals("PDF") ? ".pdf" : ".xls";
			FileUtils.writeByteArrayToFile(
					new File("C:\\Users\\Odalis\\Documents\\reports\\accounting_global_balance_" + date + format),
					(byte[]) responseReport.getData());
		} catch (IOException e) {
			log.severe(e.getMessage());
		}

	}

}
