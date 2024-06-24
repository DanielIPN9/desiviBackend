package mx.com.desivecore.domain.payments.accountPayable.ports;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.payments.accountPayable.models.AccountPayable;
import mx.com.desivecore.domain.payments.accountPayable.models.RemissionEntryBalanceSearch;

public interface AccountPayableServicePort {

	ResponseModel findAllByCurrentMonth();

	ResponseModel findAllByParams(RemissionEntryBalanceSearch remissionEntryBalanceSearch);

	ResponseModel createRecord(AccountPayable accountPayable);

	ResponseModel viewRemissionEntryBalanceDetail(Long remissionEntryId);

	ResponseModel viewAllSupplierActiveList();

	ResponseModel viewAllPaymentState();
	
	ResponseModel viewAllAccountingType();

}
