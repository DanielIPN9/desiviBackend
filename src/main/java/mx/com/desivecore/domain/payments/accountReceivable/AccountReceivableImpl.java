package mx.com.desivecore.domain.payments.accountReceivable;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.constants.PaymentStateEnum;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.cash.models.AccountingType;
import mx.com.desivecore.domain.cash.ports.CashPersistencePort;
import mx.com.desivecore.domain.clients.models.Client;
import mx.com.desivecore.domain.clients.models.ClientSummary;
import mx.com.desivecore.domain.clients.ports.ClientPersistencePort;
import mx.com.desivecore.domain.payments.accountReceivable.models.AccountReceivable;
import mx.com.desivecore.domain.payments.accountReceivable.models.RemissionOutputBalance;
import mx.com.desivecore.domain.payments.accountReceivable.models.RemissionOutputBalanceSearch;
import mx.com.desivecore.domain.payments.accountReceivable.models.RemissionOutputGlobalBalance;
import mx.com.desivecore.domain.payments.accountReceivable.ports.AccountReceivablePersistencePort;
import mx.com.desivecore.domain.payments.accountReceivable.ports.AccountReceivableServicePort;
import mx.com.desivecore.domain.payments.models.PaymentState;
import mx.com.desivecore.domain.remissionOutput.ports.RemissionOutputPersistencePort;
import mx.com.desivecore.infraestructure.configuration.exceptions.InternalError;
import mx.com.desivecore.infraestructure.configuration.exceptions.ValidationError;

@Log
@Service
public class AccountReceivableImpl implements AccountReceivableServicePort {

	@Autowired
	private AccountReceivablePersistencePort accountReceivablePersistencePort;

	@Autowired
	private CashPersistencePort cashPersistencePort;

	@Autowired
	private ClientPersistencePort clientPersistencePort;

	@Autowired
	private RemissionOutputPersistencePort remissionOutputPersistencePort;

	private ValidRemissionOutputPayment validRemissionOutputPayment = new ValidRemissionOutputPayment();

	@Override
	public ResponseModel findAllByCurrentMonth() {
		log.info("GENERATE PARAMS");
		RemissionOutputBalanceSearch remissionOutputBalanceSearch = new RemissionOutputBalanceSearch();
		LocalDate currentDay = LocalDate.now();
		LocalDate dateFrom = currentDay.withDayOfMonth(1);

		remissionOutputBalanceSearch.setDateFrom(Date.from(dateFrom.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		remissionOutputBalanceSearch.setDateTo(Date.from(currentDay.atStartOfDay(ZoneId.systemDefault()).toInstant()));

		log.info("PARAMS: " + remissionOutputBalanceSearch.toString());
		List<RemissionOutputBalance> remissionOutputBalanceList = Optional
				.ofNullable(accountReceivablePersistencePort.findAllByParams(remissionOutputBalanceSearch))
				.orElse(new ArrayList<>());

		return new ResponseModel(remissionOutputBalanceList);
	}

	@Override
	public ResponseModel findAllByParams(RemissionOutputBalanceSearch remissionOutputBalanceSearch) {
		List<RemissionOutputBalance> remissionOutputBalanceList = Optional
				.ofNullable(accountReceivablePersistencePort.findAllByParams(remissionOutputBalanceSearch))
				.orElse(new ArrayList<>());
		return new ResponseModel(remissionOutputBalanceList);
	}

	@Override
	public ResponseModel createRecord(AccountReceivable accountReceivable) {
		RemissionOutputBalance remissionOutputBalance = Optional
				.ofNullable(accountReceivablePersistencePort
						.findRemissionOutputBalanceById(accountReceivable.getRemissionOutputId()))
				.orElseThrow(() -> new ValidationError("Registro no encontrado. "));

		String validations = validRemissionOutputPayment.validOperativeDataByPayment(remissionOutputBalance,
				accountReceivable);
		if (!validations.isEmpty())
			throw new ValidationError(validations);

		log.info("SAVE ACCOUNT PAYABLE");
		Boolean savePayment = accountReceivablePersistencePort.createRecord(accountReceivable);
		if (!savePayment)
			throw new InternalError();

		Double balanceDue = remissionOutputBalance.getBalanceDue() - accountReceivable.getPaymentAmount();
		balanceDue = (double) Math.round(balanceDue * 100) / 100;
		String paymentStatus = getPaymentStatus(balanceDue, remissionOutputBalance.getAmountTotal());

		remissionOutputPersistencePort.updateByAccountReceivable(accountReceivable.getRemissionOutputId(), balanceDue,
				paymentStatus);

		return new ResponseModel(savePayment);
	}

	private String getPaymentStatus(Double balanceDue, Double amountTotal) {
		String paymentStatus = "";
		if (balanceDue == 0)
			paymentStatus = PaymentStateEnum.FULL_PAYMENT.toString();

		if (balanceDue < amountTotal && balanceDue > 0)
			paymentStatus = PaymentStateEnum.PARTIAL_PAYMENT.toString();

		if (balanceDue == amountTotal)
			paymentStatus = PaymentStateEnum.NO_PAYMENT.toString();

		return paymentStatus;
	}

	@Override
	public ResponseModel viewRemissionOutputBalanceDetail(Long remissionOutputId) {
		RemissionOutputGlobalBalance remissionOutputGlobalBalance = Optional
				.ofNullable(accountReceivablePersistencePort.viewRemissionOutputBalanceDetail(remissionOutputId))
				.orElseThrow(() -> new ValidationError("Registro no encontrado. "));
		return new ResponseModel(remissionOutputGlobalBalance);
	}

	@Override
	public ResponseModel viewAllClientActiveList() {
		List<Client> clientActiveList = Optional.ofNullable(clientPersistencePort.viewAllClients())
				.orElse(new ArrayList<>());

		List<ClientSummary> clientSummaryList = clientActiveList.stream()
				.map(client -> new ClientSummary(client.getClientId(), client.getBusinessName()))
				.collect(Collectors.toList());

		return new ResponseModel(clientSummaryList);
	}

	@Override
	public ResponseModel viewAllPaymentState() {
		List<PaymentState> paymentStateList = Optional
				.ofNullable(accountReceivablePersistencePort.viewAllPaymentState()).orElse(new ArrayList<>());
		return new ResponseModel(paymentStateList);
	}

	@Override
	public ResponseModel viewAllAccountingType() {
		List<AccountingType> accountingTypeList = cashPersistencePort.viewAllAccountingTypeList();
		if (accountingTypeList == null)
			return new ResponseModel(new ArrayList<>());
		return new ResponseModel(accountingTypeList);
	}

}
