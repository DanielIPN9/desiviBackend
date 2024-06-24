package mx.com.desivecore.domain.payments.accountPayable.ports;

import java.util.List;

import mx.com.desivecore.domain.payments.accountPayable.models.AccountPayable;
import mx.com.desivecore.domain.payments.accountPayable.models.RemissionEntryBalance;
import mx.com.desivecore.domain.payments.accountPayable.models.RemissionEntryBalanceSearch;
import mx.com.desivecore.domain.payments.accountPayable.models.RemissionEntryGlobalBalance;
import mx.com.desivecore.domain.payments.models.PaymentState;

public interface AccountPayablePersistencePort {

	List<RemissionEntryBalance> findAllByParams(RemissionEntryBalanceSearch remissionEntryBalanceSearch);
	
	RemissionEntryBalance findRemissionEntryBalanceById(Long remissionEntryId);

	Boolean createRecord(AccountPayable accountPayable);

	RemissionEntryGlobalBalance viewRemissionEntryBalanceDetail(Long remissionEntryId);

	List<PaymentState> viewAllPaymentState();

}
