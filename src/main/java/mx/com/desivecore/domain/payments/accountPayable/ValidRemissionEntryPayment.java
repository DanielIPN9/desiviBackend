package mx.com.desivecore.domain.payments.accountPayable;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.constants.AccountinTypeEnum;
import mx.com.desivecore.commons.constants.PaymentStateEnum;
import mx.com.desivecore.domain.payments.accountPayable.models.AccountPayable;
import mx.com.desivecore.domain.payments.accountPayable.models.RemissionEntryBalance;

@Log
public class ValidRemissionEntryPayment {

	public String validOperativeDataByPayment(RemissionEntryBalance remissionEntryBalance,
			AccountPayable accountPayable) {

		log.info("INIT validOperativeDataByPayment()");
		StringBuilder validations = new StringBuilder();

		if (remissionEntryBalance.getPaymentSate().equals(PaymentStateEnum.FULL_PAYMENT.toString()))
			return validations.append("La remisión tiene pago completo. ").toString();

		if (accountPayable.getAccountType() == null)
			return validations.append("El tipo de ingreso es invalido. ").toString();

		if (!validAccountingType(accountPayable.getAccountType().getCode()))
			validations.append("El tipo de ingreso es invalido. ");

		Double balanceDue = remissionEntryBalance.getBalanceDue();
		if (accountPayable.getPaymentAmount() > balanceDue)
			validations.append("El pago ingresado es mayor al saldo pendiente. ");

		return validations.toString();
	}

	private Boolean validAccountingType(String code) {
		try {
			AccountinTypeEnum.valueOf(code);
			return true;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return false;
		}
	}

}
