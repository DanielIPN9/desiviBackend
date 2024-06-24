package mx.com.desivecore.domain.payments.accountPayable;

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
import mx.com.desivecore.domain.payments.accountPayable.models.AccountPayable;
import mx.com.desivecore.domain.payments.accountPayable.models.RemissionEntryBalance;
import mx.com.desivecore.domain.payments.accountPayable.models.RemissionEntryBalanceSearch;
import mx.com.desivecore.domain.payments.accountPayable.models.RemissionEntryGlobalBalance;
import mx.com.desivecore.domain.payments.accountPayable.ports.AccountPayablePersistencePort;
import mx.com.desivecore.domain.payments.accountPayable.ports.AccountPayableServicePort;
import mx.com.desivecore.domain.payments.models.PaymentState;
import mx.com.desivecore.domain.remissionEntry.ports.RemissionEntryPersistencePort;
import mx.com.desivecore.domain.suppliers.models.Supplier;
import mx.com.desivecore.domain.suppliers.models.SupplierSummary;
import mx.com.desivecore.domain.suppliers.ports.SupplierPersistencePort;
import mx.com.desivecore.infraestructure.configuration.exceptions.InternalError;
import mx.com.desivecore.infraestructure.configuration.exceptions.ValidationError;

@Log
@Service
public class AccountPayableImpl implements AccountPayableServicePort {

	@Autowired
	private AccountPayablePersistencePort accountPayablePersistencePort;

	@Autowired
	private SupplierPersistencePort supplierPersistencePort;

	@Autowired
	private CashPersistencePort cashPersistencePort;

	@Autowired
	private RemissionEntryPersistencePort remissionEntryPersistencePort;

	private ValidRemissionEntryPayment validRemissionEntryPayment = new ValidRemissionEntryPayment();

	@Override
	public ResponseModel findAllByCurrentMonth() {
		log.info("GENERATE PARAMS");
		RemissionEntryBalanceSearch remissionEntryBalanceSearch = new RemissionEntryBalanceSearch();
		LocalDate currentDay = LocalDate.now();
		LocalDate dateFrom = currentDay.withDayOfMonth(1);

		remissionEntryBalanceSearch.setDateFrom(Date.from(dateFrom.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		remissionEntryBalanceSearch.setDateTo(Date.from(currentDay.atStartOfDay(ZoneId.systemDefault()).toInstant()));

		log.info("PARAMS: " + remissionEntryBalanceSearch.toString());
		List<RemissionEntryBalance> remissionEntryBalanceList = Optional
				.ofNullable(accountPayablePersistencePort.findAllByParams(remissionEntryBalanceSearch))
				.orElse(new ArrayList<>());

		return new ResponseModel(remissionEntryBalanceList);
	}

	@Override
	public ResponseModel findAllByParams(RemissionEntryBalanceSearch remissionEntryBalanceSearch) {
		List<RemissionEntryBalance> remissionEntryBalanceList = Optional
				.ofNullable(accountPayablePersistencePort.findAllByParams(remissionEntryBalanceSearch))
				.orElse(new ArrayList<>());
		return new ResponseModel(remissionEntryBalanceList);
	}

	@Override
	public ResponseModel createRecord(AccountPayable accountPayable) {

		RemissionEntryBalance remissionEntryBalance = Optional
				.ofNullable(accountPayablePersistencePort
						.findRemissionEntryBalanceById(accountPayable.getRemissionEntryId()))
				.orElseThrow(() -> new ValidationError("Registro no encontrado. "));

		String validations = validRemissionEntryPayment.validOperativeDataByPayment(remissionEntryBalance,
				accountPayable);
		if (!validations.isEmpty())
			throw new ValidationError(validations);

		log.info("SAVE ACCOUNT PAYABLE");
		Boolean savePayment = accountPayablePersistencePort.createRecord(accountPayable);
		if (!savePayment)
			throw new InternalError();

		Double balanceDue = remissionEntryBalance.getBalanceDue() - accountPayable.getPaymentAmount();
		balanceDue = (double) Math.round(balanceDue * 100) / 100;
		String paymentStatus = getPaymentStatus(balanceDue, remissionEntryBalance.getAmountTotal());

		remissionEntryPersistencePort.updateByAccountPayable(accountPayable.getRemissionEntryId(), balanceDue,
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
	public ResponseModel viewRemissionEntryBalanceDetail(Long remissionEntryId) {
		RemissionEntryGlobalBalance remissionEntryGlobalBalance = Optional
				.ofNullable(accountPayablePersistencePort.viewRemissionEntryBalanceDetail(remissionEntryId))
				.orElseThrow(() -> new ValidationError("Registro no encontrado. "));
		return new ResponseModel(remissionEntryGlobalBalance);
	}

	@Override
	public ResponseModel viewAllSupplierActiveList() {
		List<Supplier> supplierActiveList = Optional.ofNullable(supplierPersistencePort.viewAllSupplierActive())
				.orElse(new ArrayList<>());
		List<SupplierSummary> supplierSummaryList = supplierActiveList.stream()
				.map(supplier -> new SupplierSummary(supplier.getSupplierId(), supplier.getBusinessName()))
				.collect(Collectors.toList());
		return new ResponseModel(supplierSummaryList);
	}

	@Override
	public ResponseModel viewAllPaymentState() {
		List<PaymentState> paymentStateList = Optional.ofNullable(accountPayablePersistencePort.viewAllPaymentState())
				.orElse(new ArrayList<>());
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
