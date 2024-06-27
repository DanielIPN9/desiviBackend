package mx.com.desivecore.domain.payments.accountReceivable;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.constants.AccountinTypeEnum;
import mx.com.desivecore.commons.constants.PaymentStateEnum;
import mx.com.desivecore.domain.payments.accountReceivable.models.AccountReceivable;
import mx.com.desivecore.domain.payments.accountReceivable.models.RemissionOutputBalance;

@Log
public class ValidRemissionOutputPayment {

	public String validOperativeDataByPayment(RemissionOutputBalance remissionOutputBalance,
			AccountReceivable accountReceivable) {
		log.info("INIT validOperativeDataByPayment()");

		StringBuilder validations = new StringBuilder();

		if (remissionOutputBalance.getPaymentState().equals(PaymentStateEnum.FULL_PAYMENT.toString()))
			return validations.append("La remisión tiene pago completo. ").toString();

		if (accountReceivable.getAccountingType() == null)
			return validations.append("El tipo de ingreso es invalido. ").toString();

		if (!validAccountingType(accountReceivable.getAccountingType().getCode()))
			validations.append("El tipo de ingreso es invalido. ");

		Double balanceDue = remissionOutputBalance.getBalanceDue();
		if (accountReceivable.getPaymentAmount() > balanceDue)
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
