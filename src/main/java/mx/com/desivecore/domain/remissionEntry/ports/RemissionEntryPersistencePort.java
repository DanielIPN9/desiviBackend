package mx.com.desivecore.domain.remissionEntry.ports;

import java.math.BigInteger;
import java.util.List;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntry;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntryDocument;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntryHistory;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntrySummary;
import mx.com.desivecore.domain.remissionEntry.models.RemissionSearchParams;

public interface RemissionEntryPersistencePort {

	RemissionEntry saveRemissionEntry(RemissionEntry remissionEntry);

	RemissionEntry updateRemissionEntry(RemissionEntry remissionEntry);

	BigInteger getConsecutiveByCode(String code);

	RemissionEntryHistory saveRemissionEntryHistory(RemissionEntryHistory remissionEntryHistory);

	RemissionEntry viewRemissionById(Long remissionEntryId);
	
	boolean cancelRemissionById(Long remissionEntryId);

	List<RemissionEntrySummary> searchRemissionEntryByParams(RemissionSearchParams remissionSearchParams);

	List<RemissionEntry> searchRemissionEntryByUserId(Long userId);

	List<RemissionEntryHistory> viewRemissionHistoryById(Long remissionEntryId);

	ResponseModel generateRemissionDocument(RemissionEntryDocument remissionEntryDocument);
	
	Boolean updateByAccountPayable(Long remissionEntryId, Double balanceDue, String paymentStatus);

}
