package mx.com.desivecore.infraestructure.payments.accountPayable.converters;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.cash.models.AccountingType;
import mx.com.desivecore.domain.payments.accountPayable.models.AccountPayable;
import mx.com.desivecore.infraestructure.payments.accountPayable.entities.AccountPayableEntity;

@Component
public class AccountPayableConverter {

	public AccountPayableEntity accountPayableToEntity(AccountPayable accountPayable) {
		AccountPayableEntity accountPayableEntity = new AccountPayableEntity();
		accountPayableEntity.setPaymentId(null);
		accountPayableEntity.setCreationDate(new Date());
		accountPayableEntity.setRemissionEntryId(accountPayable.getRemissionEntryId());
		accountPayableEntity.setAccountType(accountPayable.getAccountType().getCode());
		accountPayableEntity.setPaymentAmount(accountPayable.getPaymentAmount());
		return accountPayableEntity;
	}

	public AccountPayable entityToAccountPayable(AccountPayableEntity accountPayableEntity,
			List<AccountingType> accountingTypeList) {
		AccountPayable accountPayable = new AccountPayable();
		accountPayable.setPaymentId(accountPayableEntity.getPaymentId());
		accountPayable.setCreationDate(accountPayableEntity.getCreationDate());
		accountPayable.setRemissionEntryId(accountPayableEntity.getRemissionEntryId());
		accountPayable.setPaymentAmount(accountPayableEntity.getPaymentAmount());
		for (AccountingType accountingType : accountingTypeList) {
			if (accountPayableEntity.getAccountType().equalsIgnoreCase(accountingType.getCode())) {
				accountPayable.setAccountType(accountingType);
				break;
			}
		}
		return accountPayable;
	}

}
