package mx.com.desivecore.domain.returnRemissionEntry.ports;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntry;
import mx.com.desivecore.domain.returnRemissionEntry.models.ReturnRESearchParams;

public interface ReturnREServicePort {

	ResponseModel searchRemissionEntrySummaryByFolio(String folio);

	ResponseModel generateReturnRemissionEntry(RemissionEntry remissionEntry);
	
	ResponseModel viewDetailByReturnREId(Long retuernREId);

	ResponseModel generateReturnREDocumentById(Long retuernREId);

	ResponseModel viewAllReturnRemissionEntry();

	ResponseModel searchReturnRemissionEntryByParams(ReturnRESearchParams returnRESearchParams);
	
	ResponseModel viewSupplierActiveList();
	
	ResponseModel viewBranchActiveList();

}
