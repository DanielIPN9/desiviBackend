package mx.com.desivecore.infraestructure.remissionEntry.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.remissionEntry.models.RemissionEntryHistory;
import mx.com.desivecore.infraestructure.remissionEntry.entities.RemissionEntryHistoryEntity;

@Component
public class RemissionEntryHistoryConverter {

	public RemissionEntryHistoryEntity remissionEntryHistoryToEntity(RemissionEntryHistory remissionEntryHistory) {

		RemissionEntryHistoryEntity remissionEntryHistoryEntity = new RemissionEntryHistoryEntity();

		remissionEntryHistoryEntity.setId(remissionEntryHistory.getId());
		remissionEntryHistoryEntity.setRemissionEntryId(remissionEntryHistory.getRemissionEntryId());
		remissionEntryHistoryEntity.setFolio(remissionEntryHistory.getFolio());
		remissionEntryHistoryEntity.setActionDate(remissionEntryHistory.getAcctionDate());
		remissionEntryHistoryEntity.setAction(remissionEntryHistory.getAction());
		remissionEntryHistoryEntity.setModifiedData(remissionEntryHistory.getModifiedData());

		return remissionEntryHistoryEntity;
	}

	public RemissionEntryHistory entityToRemissionEntryHistory(
			RemissionEntryHistoryEntity remissionEntryHistoryEntity) {

		RemissionEntryHistory remissionEntryHistory = new RemissionEntryHistory();

		remissionEntryHistory.setId(remissionEntryHistoryEntity.getId());
		remissionEntryHistory.setRemissionEntryId(remissionEntryHistoryEntity.getRemissionEntryId());
		remissionEntryHistory.setFolio(remissionEntryHistoryEntity.getFolio());
		remissionEntryHistory.setAcctionDate(remissionEntryHistoryEntity.getActionDate());
		remissionEntryHistory.setAction(remissionEntryHistoryEntity.getAction());
		remissionEntryHistory.setModifiedData(remissionEntryHistoryEntity.getModifiedData());

		return remissionEntryHistory;

	}

	public List<RemissionEntryHistoryEntity> remissionEntryHistoryListToEntityList(
			List<RemissionEntryHistory> remissionEntryHistoryList) {
		List<RemissionEntryHistoryEntity> remissionEntryHistoryEntityList = new ArrayList<>();
		for (RemissionEntryHistory remissionEntryHistory : remissionEntryHistoryList) {
			remissionEntryHistoryEntityList.add(remissionEntryHistoryToEntity(remissionEntryHistory));
		}
		return remissionEntryHistoryEntityList;
	}

	public List<RemissionEntryHistory> entityListToRemissionEntryHistoryList(
			List<RemissionEntryHistoryEntity> remissionEntryHistoryEntityList) {
		List<RemissionEntryHistory> remissionEntryHistoryList = new ArrayList<>();
		for (RemissionEntryHistoryEntity remissionEntryHistoryEntity : remissionEntryHistoryEntityList) {
			remissionEntryHistoryList.add(entityToRemissionEntryHistory(remissionEntryHistoryEntity));
		}
		return remissionEntryHistoryList;
	}
}
