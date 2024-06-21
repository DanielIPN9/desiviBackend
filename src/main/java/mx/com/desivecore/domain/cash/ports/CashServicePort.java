package mx.com.desivecore.domain.cash.ports;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.cash.models.CashSearchParams;
import mx.com.desivecore.domain.cash.models.ClosingCash;
import mx.com.desivecore.domain.cash.models.EntryMovementRecord;
import mx.com.desivecore.domain.cash.models.ExitMovementRecord;
import mx.com.desivecore.domain.cash.models.OpeningCash;
import mx.com.desivecore.domain.cash.models.PaymentMovementRecord;

public interface CashServicePort {

	ResponseModel viewOpeningCashSummaryByUserLogged(String userEmail);

	ResponseModel createOpeningCash(OpeningCash openingCash, String userEmail);

	ResponseModel createEntryMovementCash(EntryMovementRecord entryMovementRecord, String userEmail);

	ResponseModel createExitMovementCash(ExitMovementRecord exitMovementRecord, String userEmail);

	ResponseModel createPaymentMovementCash(PaymentMovementRecord paymentMovementRecord, String userEmail);

	ResponseModel generateClosingSummaryByUserLogged(String userEmail);

	ResponseModel createClosingCash(ClosingCash closingCash, String userEmail);

	ResponseModel viewOpeningCashByParams(CashSearchParams cashSearchParams);

	ResponseModel viewMovementOpeningCashById(Long openingCashId);

	ResponseModel searchRemissionOutputSummaryByFolio(String remissionOutputFolio);

	ResponseModel viewAllBranchActive();

	ResponseModel viewAllAccountingType();

}
