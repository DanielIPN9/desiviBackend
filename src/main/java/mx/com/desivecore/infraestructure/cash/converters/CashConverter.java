package mx.com.desivecore.infraestructure.cash.converters;

import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.cash.models.AccountingType;
import mx.com.desivecore.domain.cash.models.ClosingCash;
import mx.com.desivecore.domain.cash.models.ClosingDetail;
import mx.com.desivecore.domain.cash.models.OpeningCash;
import mx.com.desivecore.infraestructure.cash.entities.AccountingTypeEntity;
import mx.com.desivecore.infraestructure.cash.entities.ClosingCashEntity;
import mx.com.desivecore.infraestructure.cash.entities.ClosingDetailEntity;
import mx.com.desivecore.infraestructure.cash.entities.OpeningCashEntity;

@Component
public class CashConverter {

	public OpeningCashEntity openingCashToEntity(OpeningCash openingCash) {

		OpeningCashEntity openingCashEntity = new OpeningCashEntity();

		openingCashEntity.setOpeningCashId(openingCash.getOpeningCashId());
		openingCashEntity.setCreationDate(openingCash.getCreationDate());
		openingCashEntity.setBranchId(openingCash.getBranch().getBranchId());
		openingCashEntity.setUserEmail(openingCash.getUserEmail());
		openingCashEntity.setUserId(openingCash.getUserId());
		openingCashEntity.setCurrency(openingCash.getCurrency());
		openingCashEntity.setAmount(openingCash.getAmount());
		openingCashEntity.setIsActive(openingCash.getIsActive());
		openingCashEntity.setClosingCashId(openingCash.getClosingCashId());

		return openingCashEntity;
	}

	public OpeningCash entityToOpeningCash(OpeningCashEntity openingCashEntity) {

		OpeningCash openingCash = new OpeningCash();

		openingCash.setOpeningCashId(openingCashEntity.getOpeningCashId());
		openingCash.setCreationDate(openingCashEntity.getCreationDate());
		openingCash.setUserEmail(openingCashEntity.getUserEmail());
		openingCash.setUserId(openingCashEntity.getUserId());
		openingCash.setCurrency(openingCashEntity.getCurrency());
		openingCash.setAmount(openingCashEntity.getAmount());
		openingCash.setIsActive(openingCashEntity.getIsActive());
		openingCash.setClosingCashId(openingCashEntity.getClosingCashId());

		return openingCash;
	}

	public AccountingType entityToAccountingType(AccountingTypeEntity accountingTypeEntity) {

		AccountingType accountingType = new AccountingType();

		accountingType.setCode(accountingTypeEntity.getCode());
		accountingType.setDescription(accountingTypeEntity.getDescription());

		return accountingType;

	}

	public ClosingCashEntity closingCashToEntity(ClosingCash closingCash) {

		ClosingCashEntity closingCashEntity = new ClosingCashEntity();

		closingCashEntity.setClosingCashId(null);
		closingCashEntity.setCreationDate(closingCash.getCreationDate());
		closingCashEntity.setBranchId(closingCash.getBranch().getBranchId());
		closingCashEntity.setUserEmail(closingCash.getUserEmail());
		closingCashEntity.setCurrency(closingCash.getCurrency());
		closingCashEntity.setAmountTotal(closingCash.getAmountTotal());

		return closingCashEntity;

	}

	public ClosingCash entityToClosingCash(ClosingCashEntity closingCashEntity) {

		ClosingCash closingCash = new ClosingCash();

		closingCash.setClosingCashId(closingCashEntity.getClosingCashId());
		closingCash.setCreationDate(closingCashEntity.getCreationDate());
		closingCash.setUserEmail(closingCashEntity.getUserEmail());
		closingCash.setCurrency(closingCashEntity.getCurrency());
		closingCash.setAmountTotal(closingCashEntity.getAmountTotal());

		return closingCash;
	}

	public ClosingDetailEntity closingDetailToEntity(ClosingDetail closingDetail, Long closingCashId) {

		ClosingDetailEntity closingDetailEntity = new ClosingDetailEntity();

		closingDetailEntity.setClosingCashId(closingCashId);
		closingDetailEntity.setAccountingType(closingDetail.getAccountingType().getCode());
		closingDetailEntity.setCurrency(closingDetail.getCurrency());
		closingDetailEntity.setAmount(closingDetail.getAmount());

		return closingDetailEntity;
	}

	public ClosingDetail entityToClosingDetail(ClosingDetailEntity closingDetailEntity,
			List<AccountingType> accountingTypeList) {

		ClosingDetail closingDetail = new ClosingDetail();

		closingDetail.setClosingCashId(closingDetailEntity.getClosingCashId());
		closingDetail.setCurrency(closingDetailEntity.getCurrency());
		closingDetail.setAmount(closingDetailEntity.getAmount());

		for (AccountingType accountingType : accountingTypeList) {
			if (closingDetailEntity.getAccountingType().equalsIgnoreCase(accountingType.getCode())) {
				closingDetail.setAccountingType(accountingType);
				break;
			}
		}

		return closingDetail;
	}
}
