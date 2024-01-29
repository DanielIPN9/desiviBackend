package mx.com.desivecore.infraestructure.products.converters;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.products.models.ProductAvailability;
import mx.com.desivecore.infraestructure.branches.converters.BranchConverter;
import mx.com.desivecore.infraestructure.branches.entities.BranchEntity;
import mx.com.desivecore.infraestructure.branches.repositories.BranchReposirory;
import mx.com.desivecore.infraestructure.products.entities.ProductAvailabilityEntity;

@Component
public class ProductAvailabilityConverter {

	@Autowired
	private BranchReposirory branchReposirory;

	@Autowired
	private BranchConverter branchConverter;

	public ProductAvailabilityEntity productAvailabilityToProductAvailabilityEntity(
			ProductAvailability productAvailability) {

		ProductAvailabilityEntity productAvailabilityEntity = new ProductAvailabilityEntity();

		productAvailabilityEntity.setId(productAvailability.getId());
		productAvailabilityEntity.setBranchId(productAvailability.getBranch().getBranchId());
		productAvailabilityEntity.setProductId(productAvailability.getProductId());
		productAvailabilityEntity.setAmount(productAvailability.getAmount());

		return productAvailabilityEntity;
	}

	public ProductAvailability productAvailabilityEntityToProductAvailability(
			ProductAvailabilityEntity productAvailabilityEntity) {

		ProductAvailability productAvailability = new ProductAvailability();

		productAvailability.setId(productAvailabilityEntity.getId());
		Optional<BranchEntity> branchEntity = branchReposirory.findById(productAvailabilityEntity.getBranchId());
		productAvailability.setBranch(branchConverter.branchEntityToBranch(branchEntity.get()));
		productAvailability.setProductId(productAvailabilityEntity.getProductId());
		productAvailability.setAmount(productAvailabilityEntity.getAmount());

		return productAvailability;
	}

	public List<ProductAvailabilityEntity> productAvailabilityListToProductAvailabilityEntityList(
			List<ProductAvailability> productAvailabilityList) {
		List<ProductAvailabilityEntity> produAvailabilityEntityList = new ArrayList<>();
		for (ProductAvailability productAvailability : productAvailabilityList) {
			produAvailabilityEntityList.add(productAvailabilityToProductAvailabilityEntity(productAvailability));
		}
		return produAvailabilityEntityList;
	}

	public List<ProductAvailability> productAvailabilityEntityListToProductAvailabilityList(
			List<ProductAvailabilityEntity> productAvailabilityEntityList) {
		List<ProductAvailability> productAvailabilityList = new ArrayList<>();
		for (ProductAvailabilityEntity productAvailabilityEntity : productAvailabilityEntityList) {
			productAvailabilityList.add(productAvailabilityEntityToProductAvailability(productAvailabilityEntity));
		}
		return productAvailabilityList;
	}

}
