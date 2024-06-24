package mx.com.desivecore.infraestructure.payments.converters;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.payments.models.PaymentState;
import mx.com.desivecore.infraestructure.payments.entities.PaymentStateEntity;

@Component
public class PaymentStateConverter {

	public PaymentState entityToPaymentState(PaymentStateEntity paymentStateEntity) {
		PaymentState paymentState = new PaymentState();
		paymentState.setId(paymentStateEntity.getId());
		paymentState.setCode(paymentStateEntity.getCode());
		paymentState.setDescription(paymentStateEntity.getDescription());
		return paymentState;
	}
}
