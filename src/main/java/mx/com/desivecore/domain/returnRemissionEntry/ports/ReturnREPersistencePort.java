package mx.com.desivecore.domain.returnRemissionEntry.ports;

import java.util.List;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.returnRemissionEntry.models.ReturnProductEntry;
import mx.com.desivecore.domain.returnRemissionEntry.models.ReturnRESearchParams;
import mx.com.desivecore.domain.returnRemissionEntry.models.ReturnRESummary;
import mx.com.desivecore.domain.returnRemissionEntry.models.ReturnRemissionEntry;

public interface ReturnREPersistencePort {

	ReturnRemissionEntry generateReturnRemissionEntry(ReturnRemissionEntry returnRemissionEntry);

	ReturnRemissionEntry viewReturnREDetailById(Long returnREId);

	ResponseModel generateReturnREDocumentById();

	List<ReturnRESummary> viewAllReturnRemissionEntry();

	List<ReturnRESummary> searchReturnRemissionEntryByParams(ReturnRESearchParams returnRESearchParams);

	List<ReturnProductEntry> searchByFolioRE(String remissionEntryFolio);
}
