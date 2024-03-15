package mx.com.desivecore.domain.reports.ports;

import java.util.List;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.reports.models.ProductDetail;
import mx.com.desivecore.domain.reports.models.RemissionEntryDetail;
import mx.com.desivecore.domain.reports.models.RemissionOutputDetail;
import mx.com.desivecore.domain.reports.models.document.InventoryReportDocument;
import mx.com.desivecore.domain.reports.models.document.RemissionEntryReportDocument;
import mx.com.desivecore.domain.reports.models.document.RemissionOutputReportDocument;
import mx.com.desivecore.domain.reports.models.search.RemissionEntryParamsReport;
import mx.com.desivecore.domain.reports.models.search.RemissionOutputParamsReport;

public interface ReportPersistencePort {

	List<RemissionOutputDetail> searchRemissionOutputDataByParams(RemissionOutputParamsReport remissionOutputParamsReport);

	List<RemissionEntryDetail> searchRemissionEntryDataByParams(RemissionEntryParamsReport remissionEntryParamsReport);

	List<ProductDetail> searchInventoryDataByParams(Long branchId);

	ResponseModel generateInventoryReport(InventoryReportDocument inventoryReportDocument);

	ResponseModel generateRemissionEntryReport(RemissionEntryReportDocument remissionEntryReportDocument);

	ResponseModel generateRemissionOutputReport(RemissionOutputReportDocument remissionOutputReportDocument);

}
