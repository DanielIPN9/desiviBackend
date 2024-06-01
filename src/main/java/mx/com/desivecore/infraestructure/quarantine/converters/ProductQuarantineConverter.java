package mx.com.desivecore.infraestructure.quarantine.converters;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.quarantine.models.ProductQuarantine;
import mx.com.desivecore.infraestructure.quarantine.entities.ProductQuarantineEntity;

@Component
public class ProductQuarantineConverter {

	public ProductQuarantineEntity productQuarantineToEntity(ProductQuarantine productQuarantine) {
		ProductQuarantineEntity productQuarantineEntity = new ProductQuarantineEntity();

		productQuarantineEntity.setProductQuarantineId(productQuarantine.getProductQuarantineId());
		productQuarantineEntity.setProductId(productQuarantine.getProductId());
		productQuarantineEntity.setBranchId(productQuarantine.getBranchId());
		productQuarantineEntity.setAmount(productQuarantine.getAmount());

		return productQuarantineEntity;
	}

	public ProductQuarantine entityToProductQuarantine(ProductQuarantineEntity productQuarantineEntity) {
		ProductQuarantine productQuarantine = new ProductQuarantine();

		productQuarantine.setProductQuarantineId(productQuarantineEntity.getProductQuarantineId());
		productQuarantine.setProductId(productQuarantineEntity.getProductId());
		productQuarantine.setBranchId(productQuarantineEntity.getBranchId());
		productQuarantine.setAmount(productQuarantineEntity.getAmount());

		return productQuarantine;
	}

	public List<ProductQuarantineEntity> productQuarantineListToEntityList(
			List<ProductQuarantine> productQuarantineList) {
		List<ProductQuarantineEntity> productQuarantineEntityList = productQuarantineList.stream()
				.map(productQuarantine -> productQuarantineToEntity(productQuarantine)).collect(Collectors.toList());
		return productQuarantineEntityList;
	}

	public List<ProductQuarantine> entityListToProductQuarantineList(
			List<ProductQuarantineEntity> productQuarantineEntityList) {
		List<ProductQuarantine> productQuarantineList = productQuarantineEntityList.stream()
				.map(productQuarantineEntity -> entityToProductQuarantine(productQuarantineEntity))
				.collect(Collectors.toList());
		return productQuarantineList;
	}
}
