package mx.com.desivecore.domain.payments.accountReceivable.ports;

import java.util.List;

import mx.com.desivecore.domain.payments.accountReceivable.models.AccountReceivable;
import mx.com.desivecore.domain.payments.accountReceivable.models.RemissionOutputBalance;
import mx.com.desivecore.domain.payments.accountReceivable.models.RemissionOutputBalanceSearch;
import mx.com.desivecore.domain.payments.accountReceivable.models.RemissionOutputGlobalBalance;
import mx.com.desivecore.domain.payments.models.PaymentState;

public interface AccountReceivablePersistencePort {

	List<RemissionOutputBalance> findAllByParams(RemissionOutputBalanceSearch remissionOutputBalanceSearch);

	RemissionOutputBalance findRemissionOutputBalanceById(Long remissionOutputId);

	Boolean createRecord(AccountReceivable accountReceivable);

	RemissionOutputGlobalBalance viewRemissionOutputBalanceDetail(Long remissionOutputId);

	List<PaymentState> viewAllPaymentState();

}
