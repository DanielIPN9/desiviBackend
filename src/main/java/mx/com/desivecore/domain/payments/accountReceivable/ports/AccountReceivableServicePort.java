package mx.com.desivecore.domain.payments.accountReceivable.ports;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.payments.accountReceivable.models.AccountReceivable;
import mx.com.desivecore.domain.payments.accountReceivable.models.RemissionOutputBalanceSearch;

public interface AccountReceivableServicePort {
	
	ResponseModel findAllByCurrentMonth();

	ResponseModel findAllByParams(RemissionOutputBalanceSearch remissionOutputBalanceSearch);

	ResponseModel createRecord(AccountReceivable accountReceivable);

	ResponseModel viewRemissionOutputBalanceDetail(Long remissionOutputId);

	ResponseModel viewAllClientActiveList();

	ResponseModel viewAllPaymentState();
	
	ResponseModel viewAllAccountingType();

}
