package mx.com.desivecore.infraestructure.payments.accountReceivable.converters;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.cash.models.AccountingType;
import mx.com.desivecore.domain.payments.accountReceivable.models.AccountReceivable;
import mx.com.desivecore.infraestructure.payments.accountReceivable.entities.AccountReceivableEntity;

@Component
public class AccountReceivableConverter {

	public AccountReceivableEntity accountReceivableToEntity(AccountReceivable accountReceivable) {
		AccountReceivableEntity accountReceivableEntity = new AccountReceivableEntity();
		accountReceivableEntity.setPaymentId(null);
		accountReceivableEntity.setCreationDate(new Date());
		accountReceivableEntity.setRemissionOutputId(accountReceivable.getRemissionOutputId());
		accountReceivableEntity.setAccountType(accountReceivable.getAccountingType().getCode());
		accountReceivableEntity.setPaymentAmount(accountReceivable.getPaymentAmount());
		return accountReceivableEntity;
	}

	public AccountReceivable entityToAccountReceivable(AccountReceivableEntity accountReceivableEntity,
			List<AccountingType> accountingTypeList) {
		AccountReceivable accountReceivable = new AccountReceivable();
		accountReceivable.setPaymentId(accountReceivableEntity.getPaymentId());
		accountReceivable.setCreationDate(accountReceivableEntity.getCreationDate());
		accountReceivable.setRemissionOutputId(accountReceivableEntity.getRemissionOutputId());
		accountReceivable.setPaymentAmount(accountReceivableEntity.getPaymentAmount());
		for (AccountingType accountingType : accountingTypeList) {
			if (accountReceivableEntity.getAccountType().equalsIgnoreCase(accountingType.getCode())) {
				accountReceivable.setAccountingType(accountingType);
				break;
			}
		}
		return accountReceivable;
	}
}
