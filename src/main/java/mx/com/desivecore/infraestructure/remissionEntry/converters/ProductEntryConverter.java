package mx.com.desivecore.infraestructure.remissionEntry.converters;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.remissionEntry.models.ProductEntry;
import mx.com.desivecore.infraestructure.remissionEntry.entities.ProductEntryEntity;

@Component
public class ProductEntryConverter {

	public ProductEntryEntity productEntryToEntity(ProductEntry productEntry, Long remissionEntryId) {

		ProductEntryEntity productEntryEntity = new ProductEntryEntity();

		productEntryEntity.setId(productEntry.getId());
		productEntryEntity.setRemissionEntryId(remissionEntryId);
		productEntryEntity.setProductId(productEntry.getProduct().getProductId());
		productEntryEntity.setAmount(productEntry.getAmount());
		productEntryEntity.setUnitMeasure(productEntry.getUnitMeasure());
		productEntryEntity.setPurchaseUnitPrice(productEntry.getPurchaseUnitPrice());
		productEntryEntity.setIva(productEntry.getIva());
		productEntryEntity.setNet(productEntry.getNet());
		productEntryEntity.setTotal(productEntry.getTotal());

		return productEntryEntity;
	}

	public ProductEntry entityToProductEntry(ProductEntryEntity productEntryEntity) {

		ProductEntry productEntry = new ProductEntry();

		productEntry.setId(productEntryEntity.getId());
		productEntry.setAmount(productEntryEntity.getAmount());
		productEntry.setUnitMeasure(productEntryEntity.getUnitMeasure());
		productEntry.setPurchaseUnitPrice(productEntryEntity.getPurchaseUnitPrice());
		productEntry.setIva(productEntryEntity.getIva());
		productEntry.setNet(productEntryEntity.getNet());
		productEntry.setTotal(productEntryEntity.getTotal());

		return productEntry;
	}
}
