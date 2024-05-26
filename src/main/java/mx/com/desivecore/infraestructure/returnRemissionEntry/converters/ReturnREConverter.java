package mx.com.desivecore.infraestructure.returnRemissionEntry.converters;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.returnRemissionEntry.models.ReturnRemissionEntry;
import mx.com.desivecore.infraestructure.returnRemissionEntry.entities.ReturnRemissionEntryEntity;

@Component
public class ReturnREConverter {

	public ReturnRemissionEntryEntity returnRemissionEntryToEntity(ReturnRemissionEntry returnRemissionEntry) {
		
		ReturnRemissionEntryEntity remissionEntryEntity = new ReturnRemissionEntryEntity();
		
		remissionEntryEntity.setReturnREId(returnRemissionEntry.getReturnREId());
		remissionEntryEntity.setFolio(returnRemissionEntry.getFolio());
		remissionEntryEntity.setCreationDate(returnRemissionEntry.getCreationDate());
		remissionEntryEntity.setBranchId(returnRemissionEntry.getBranch().getBranchId());
		remissionEntryEntity.setSupplierId(returnRemissionEntry.getSupplier().getSupplierId());
		remissionEntryEntity.setUserId(returnRemissionEntry.getUser().getUserId());
		remissionEntryEntity.setObservations(returnRemissionEntry.getObservations());
		remissionEntryEntity.setIvaTotal(returnRemissionEntry.getIvaTotal());
		remissionEntryEntity.setSubTotal(returnRemissionEntry.getSubTotal());
		remissionEntryEntity.setTotal(returnRemissionEntry.getTotal());
		
		return remissionEntryEntity;
		
	}
	
	public ReturnRemissionEntry entityToReturnRemissionEntry(ReturnRemissionEntryEntity returnRemissionEntryEntity) {
		
		ReturnRemissionEntry returnRemissionEntry =  new ReturnRemissionEntry();
		
		returnRemissionEntry.setReturnREId(returnRemissionEntryEntity.getReturnREId());
		returnRemissionEntry.setFolio(returnRemissionEntryEntity.getFolio());
		returnRemissionEntry.setCreationDate(returnRemissionEntryEntity.getCreationDate());
		returnRemissionEntry.setObservations(returnRemissionEntryEntity.getObservations());
		returnRemissionEntry.setIvaTotal(returnRemissionEntryEntity.getIvaTotal());
		returnRemissionEntry.setSubTotal(returnRemissionEntryEntity.getSubTotal());
		returnRemissionEntry.setTotal(returnRemissionEntryEntity.getTotal());
		
		return returnRemissionEntry;
	}
}
