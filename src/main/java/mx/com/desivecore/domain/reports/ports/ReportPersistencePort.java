package mx.com.desivecore.domain.reports.ports;

import java.util.List;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.reports.models.AccountPayableSummary;
import mx.com.desivecore.domain.reports.models.AccountReceivableSummary;
import mx.com.desivecore.domain.reports.models.EntryMovementRecordSummary;
import mx.com.desivecore.domain.reports.models.ExitMovementRecordSummary;
import mx.com.desivecore.domain.reports.models.ProductDetail;
import mx.com.desivecore.domain.reports.models.RemissionEntryDetail;
import mx.com.desivecore.domain.reports.models.RemissionOutputDetail;
import mx.com.desivecore.domain.reports.models.document.AccountinReportDocument;
import mx.com.desivecore.domain.reports.models.document.InventoryReportDocument;
import mx.com.desivecore.domain.reports.models.document.RemissionEntryReportDocument;
import mx.com.desivecore.domain.reports.models.document.RemissionOutputReportDocument;
import mx.com.desivecore.domain.reports.models.search.AccountingReportParams;
import mx.com.desivecore.domain.reports.models.search.RemissionEntryParamsReport;
import mx.com.desivecore.domain.reports.models.search.RemissionOutputParamsReport;

public interface ReportPersistencePort {

	List<RemissionOutputDetail> searchRemissionOutputDataByParams(
			RemissionOutputParamsReport remissionOutputParamsReport);

	List<RemissionEntryDetail> searchRemissionEntryDataByParams(RemissionEntryParamsReport remissionEntryParamsReport);

	List<ProductDetail> searchInventoryDataByParams(Long branchId);

	List<EntryMovementRecordSummary> searchEntryCashMovementByParams(AccountingReportParams accountingReportParams);

	List<ExitMovementRecordSummary> searchExitCashMovementByParams(AccountingReportParams accountingReportParams);

	List<AccountPayableSummary> searchAccountPayableByParams(AccountingReportParams accountingReportParams);

	List<AccountReceivableSummary> searchAccountReceivableByParams(AccountingReportParams accountingReportParams);

	ResponseModel generateInventoryReport(InventoryReportDocument inventoryReportDocument, String format);

	ResponseModel generateRemissionEntryReport(RemissionEntryReportDocument remissionEntryReportDocument,
			String format);

	ResponseModel generateRemissionOutputReport(RemissionOutputReportDocument remissionOutputReportDocument,
			String format7);

	ResponseModel generateAccountingReport(AccountinReportDocument accountinReportDocument, String format);

}
