package mx.com.desivecore.domain.cash.ports;

import java.util.List;

import mx.com.desivecore.domain.cash.models.AccountingType;
import mx.com.desivecore.domain.cash.models.CashSearchParams;
import mx.com.desivecore.domain.cash.models.ClosingCash;
import mx.com.desivecore.domain.cash.models.EntryMovementRecord;
import mx.com.desivecore.domain.cash.models.ExitMovementRecord;
import mx.com.desivecore.domain.cash.models.OpeningCash;
import mx.com.desivecore.domain.cash.models.OpeningCashSummary;
import mx.com.desivecore.domain.cash.models.OperationCashDetail;
import mx.com.desivecore.domain.cash.models.PaymentMovementRecord;

public interface CashPersistencePort {

	OpeningCashSummary findLastOpeningCashByUserId(Long userId);

	Boolean createOpeningCash(OpeningCash openingCash);

	Boolean createEntryMovementCash(EntryMovementRecord entryMovementRecord);

	Boolean createExitMovementCash(ExitMovementRecord exitMovementRecord);

	Boolean createPaymentMovementCash(PaymentMovementRecord paymentMovementRecord);

	Long createClosingCash(ClosingCash closingCash);

	Boolean closeOpeningOperationByClosingAndId(Long openingCashId, Boolean state, Long closingId);

	List<OpeningCashSummary> viewOpeningCashByParams(CashSearchParams cashSearchParams);

	OperationCashDetail viewMovementOpeningCashById(Long openingCashId);

	List<AccountingType> viewAllAccountingTypeList();

}
