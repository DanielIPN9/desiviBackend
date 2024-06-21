package mx.com.desivecore.infraestructure.cash.converters;

import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.cash.models.AccountingType;
import mx.com.desivecore.domain.cash.models.EntryMovementRecord;
import mx.com.desivecore.domain.cash.models.ExitMovementRecord;
import mx.com.desivecore.infraestructure.cash.entities.EntryMovementRecordEntity;
import mx.com.desivecore.infraestructure.cash.entities.ExitMovementRecordEntity;

@Component
public class MovementConverter {

	public EntryMovementRecordEntity entryMovementRecordToEntity(EntryMovementRecord entryMovementRecord) {
		EntryMovementRecordEntity entryMovementRecordEntity = new EntryMovementRecordEntity();
		entryMovementRecordEntity.setEntryMovementId(null);
		entryMovementRecordEntity.setOpeningCashId(entryMovementRecord.getOpeningCashId());
		entryMovementRecordEntity.setAccountingType(entryMovementRecord.getAccountingType().getCode());
		entryMovementRecordEntity.setDescription(entryMovementRecord.getDescription());
		entryMovementRecordEntity.setCurrency(entryMovementRecord.getCurrency());
		entryMovementRecordEntity.setAmount(entryMovementRecord.getAmount());
		return entryMovementRecordEntity;
	}

	public EntryMovementRecord entityToEntryMovementRecord(EntryMovementRecordEntity entryMovementRecordEntity,
			List<AccountingType> accountingTypeList) {
		EntryMovementRecord entryMovementRecord = new EntryMovementRecord();
		entryMovementRecord.setOpeningCashId(entryMovementRecordEntity.getOpeningCashId());
		entryMovementRecord.setDescription(entryMovementRecordEntity.getDescription());
		entryMovementRecord.setCurrency(entryMovementRecordEntity.getCurrency());
		entryMovementRecord.setAmount(entryMovementRecordEntity.getAmount());

		for (AccountingType accountingType : accountingTypeList) {
			if (entryMovementRecordEntity.getAccountingType().equalsIgnoreCase(accountingType.getCode())) {
				entryMovementRecord.setAccountingType(accountingType);
				break;
			}
		}
		return entryMovementRecord;
	}

	public ExitMovementRecordEntity exitMovementRecordToEntity(ExitMovementRecord exitMovementRecord) {
		ExitMovementRecordEntity exitMovementRecordEntity = new ExitMovementRecordEntity();
		exitMovementRecordEntity.setExitMovementId(null);
		exitMovementRecordEntity.setOpeningCashId(exitMovementRecord.getOpeningCashId());
		exitMovementRecordEntity.setAccountingType(exitMovementRecord.getAccountingType().getCode());
		exitMovementRecordEntity.setDescription(exitMovementRecord.getDescription());
		exitMovementRecordEntity.setAuthorityUser(exitMovementRecord.getAuthorityUser());
		exitMovementRecordEntity.setCurrency(exitMovementRecord.getCurrency());
		exitMovementRecordEntity.setAmount(exitMovementRecord.getAmount());
		return exitMovementRecordEntity;
	}

	public ExitMovementRecord entityToExitMovementRecord(ExitMovementRecordEntity exitMovementRecordEntity,
			List<AccountingType> accountingTypeList) {
		ExitMovementRecord exitMovementRecord = new ExitMovementRecord();
		exitMovementRecord.setOpeningCashId(exitMovementRecordEntity.getOpeningCashId());
		exitMovementRecord.setDescription(exitMovementRecordEntity.getDescription());
		exitMovementRecord.setAuthorityUser(exitMovementRecordEntity.getAuthorityUser());
		exitMovementRecord.setCurrency(exitMovementRecordEntity.getCurrency());
		exitMovementRecord.setAmount(exitMovementRecordEntity.getAmount());

		for (AccountingType accountingType : accountingTypeList) {
			if (exitMovementRecordEntity.getAccountingType().equalsIgnoreCase(accountingType.getCode())) {
				exitMovementRecord.setAccountingType(accountingType);
				break;
			}
		}

		return exitMovementRecord;
	}
}
