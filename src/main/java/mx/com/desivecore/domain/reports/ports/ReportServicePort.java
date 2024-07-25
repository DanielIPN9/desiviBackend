package mx.com.desivecore.domain.reports.ports;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.reports.models.search.AccountingReportParams;
import mx.com.desivecore.domain.reports.models.search.InventoryParamsReport;
import mx.com.desivecore.domain.reports.models.search.RemissionEntryParamsReport;
import mx.com.desivecore.domain.reports.models.search.RemissionOutputParamsReport;

public interface ReportServicePort {

	ResponseModel generateRemissionEntryReport(RemissionEntryParamsReport remissionEntryParamsReport);

	ResponseModel generateRemissionOutputReport(RemissionOutputParamsReport remissionOutputParamsReport);

	ResponseModel generateAccountingInventoryReport(InventoryParamsReport inventoryParamsReport);
	
	ResponseModel generateInventoryReport(InventoryParamsReport inventoryParamsReport);

	ResponseModel generateAccountingReport(AccountingReportParams accountingReportSearchParams);

	ResponseModel viewAllBranch();

	ResponseModel viwAllClient();

	ResponseModel viewAllSupplier();

	ResponseModel viewAllProduct();

}
