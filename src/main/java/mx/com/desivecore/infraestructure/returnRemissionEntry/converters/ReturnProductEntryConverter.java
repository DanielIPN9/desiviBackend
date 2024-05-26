package mx.com.desivecore.infraestructure.returnRemissionEntry.converters;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.returnRemissionEntry.models.ReturnProductEntry;
import mx.com.desivecore.infraestructure.returnRemissionEntry.entities.ReturnProductEntryEntity;

@Component
public class ReturnProductEntryConverter {

	public ReturnProductEntryEntity returnProductEntryToEntity(ReturnProductEntry returnProductEntry, Long returnREId) {

		ReturnProductEntryEntity returnProductEntryEntity = new ReturnProductEntryEntity();

		returnProductEntryEntity.setReturnREId(returnREId);
		returnProductEntryEntity.setProductId(returnProductEntry.getProduct().getProductId());
		returnProductEntryEntity.setAmountReturn(returnProductEntry.getAmountReturn());
		returnProductEntryEntity.setUnitMeasure(returnProductEntry.getUnitMeasure());
		returnProductEntryEntity.setPurchaseUnitPrice(returnProductEntry.getPurchaseUnitPrice());
		returnProductEntryEntity.setIva(returnProductEntry.getIva());
		returnProductEntryEntity.setNet(returnProductEntry.getNet());
		returnProductEntryEntity.setTotal(returnProductEntry.getTotal());

		return returnProductEntryEntity;

	}

	public ReturnProductEntry entityToReturnProductEntry(ReturnProductEntryEntity returnProductEntryEntity) {

		ReturnProductEntry returnProductEntry = new ReturnProductEntry();

		returnProductEntry.setAmountReturn(returnProductEntryEntity.getAmountReturn());
		returnProductEntry.setUnitMeasure(returnProductEntryEntity.getUnitMeasure());
		returnProductEntry.setPurchaseUnitPrice(returnProductEntryEntity.getPurchaseUnitPrice());
		returnProductEntry.setIva(returnProductEntryEntity.getIva());
		returnProductEntry.setNet(returnProductEntryEntity.getNet());
		returnProductEntry.setTotal(returnProductEntryEntity.getTotal());

		return returnProductEntry;
	}
}
