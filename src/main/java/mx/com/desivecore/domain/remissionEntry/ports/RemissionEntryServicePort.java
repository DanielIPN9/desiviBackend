package mx.com.desivecore.domain.remissionEntry.ports;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntry;
import mx.com.desivecore.domain.remissionEntry.models.RemissionSearchParams;

public interface RemissionEntryServicePort {

	ResponseModel createRemissionEntry(RemissionEntry remissionEntry);
	
	ResponseModel viewRemissionById(Long remissionEntryId);
	
	ResponseModel searchRemissionEntryByParams(RemissionSearchParams remissionSearchParams);
	
	ResponseModel viewAllByUserLogin(String userEmail);
	
	ResponseModel updateRemissionEntry(RemissionEntry remissionEntry);
	
	ResponseModel viewRemissionHistoryById(Long remissionEntryId);
	
	ResponseModel generateRemissionDocumentById(Long remissionEntryId);
	
	ResponseModel viewSupplierActiveList();
	
	ResponseModel viewBranchActiveList();
	
	ResponseModel viewProductActiveList();
	
	
}
