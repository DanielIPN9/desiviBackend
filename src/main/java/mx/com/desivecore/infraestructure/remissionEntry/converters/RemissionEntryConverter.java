package mx.com.desivecore.infraestructure.remissionEntry.converters;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.remissionEntry.models.RemissionEntry;
import mx.com.desivecore.infraestructure.remissionEntry.entities.RemissionEntryEntity;

@Component
public class RemissionEntryConverter {

	public RemissionEntryEntity remissionEntryToEntity(RemissionEntry remissionEntry) {

		RemissionEntryEntity remissionEntryEntity = new RemissionEntryEntity();

		remissionEntryEntity.setRemissionEntryId(remissionEntry.getRemissionEntryId());
		remissionEntryEntity.setFolio(remissionEntry.getFolio());
		remissionEntryEntity.setCreationDate(remissionEntry.getCreationDate());
		remissionEntryEntity.setRequestDate(remissionEntry.getRequestDate());
		remissionEntryEntity.setBranchId(remissionEntry.getBranch().getBranchId());
		remissionEntryEntity.setSupplierId(remissionEntry.getSupplier().getSupplierId());
		remissionEntryEntity.setUserId(remissionEntry.getUser().getUserId());
		remissionEntryEntity.setObservations(remissionEntry.getObservations());
		remissionEntryEntity.setConditions(remissionEntry.getConditions());
		remissionEntryEntity.setRemissionTotal(remissionEntry.getRemissionTotal());
		remissionEntryEntity.setIvaTotal(remissionEntry.getIvaTotal());
		remissionEntryEntity.setStatus(remissionEntry.isStatus());

		return remissionEntryEntity;
	}

	public RemissionEntry entityToRemissionEntry(RemissionEntryEntity remissionEntryEntity) {

		RemissionEntry remissionEntry = new RemissionEntry();

		remissionEntry.setRemissionEntryId(remissionEntryEntity.getRemissionEntryId());
		remissionEntry.setFolio(remissionEntryEntity.getFolio());
		remissionEntry.setCreationDate(remissionEntryEntity.getCreationDate());
		remissionEntry.setRequestDate(remissionEntryEntity.getRequestDate());
		remissionEntry.setObservations(remissionEntryEntity.getObservations());
		remissionEntry.setConditions(remissionEntryEntity.getConditions());
		remissionEntry.setRemissionTotal(remissionEntryEntity.getRemissionTotal());
		remissionEntry.setIvaTotal(remissionEntryEntity.getIvaTotal());
		remissionEntry.setStatus(remissionEntryEntity.getStatus());

		return remissionEntry;
	}
}
