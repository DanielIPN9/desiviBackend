package mx.com.desivecore.infraestructure.productTag.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.productTag.models.ProductTag;
import mx.com.desivecore.infraestructure.productTag.entities.ProductTagEntity;

@Component
public class ProductTagConverter {

	public ProductTagEntity productTagToProductTagEntity(ProductTag productTag) {

		ProductTagEntity productTagEntity = new ProductTagEntity();

		productTagEntity.setTagId(productTag.getTagId());
		productTagEntity.setProductId(productTag.getProduct().getProductId());
		productTagEntity.setLot(productTag.getLot());
		productTagEntity.setCreationDate(productTag.getCreationDate());
		productTagEntity.setNetWeight(productTag.getNetWeight());
		productTagEntity.setUm(productTag.getUm());
		productTagEntity.setFullAddress(productTag.getFullAddress());
		productTagEntity.setUrlSite(productTag.getUrlSite());
		productTagEntity.setPhoneNumber(productTag.getPhoneNumber());

		return productTagEntity;
	}

	public ProductTag productTagEntityToProductTag(ProductTagEntity productTagEntity) {

		ProductTag productTag = new ProductTag();

		productTag.setTagId(productTagEntity.getTagId());
		productTag.setLot(productTagEntity.getLot());
		productTag.setCreationDate(productTagEntity.getCreationDate());
		productTag.setNetWeight(productTagEntity.getNetWeight());
		productTag.setUm(productTagEntity.getUm());
		productTag.setFullAddress(productTagEntity.getFullAddress());
		productTag.setUrlSite(productTagEntity.getUrlSite());
		productTag.setPhoneNumber(productTagEntity.getPhoneNumber());

		return productTag;
	}

	public List<ProductTagEntity> productTagListToProductTagEntitiyList(List<ProductTag> productTagList) {
		List<ProductTagEntity> productTagEntityList = new ArrayList<>();
		for (ProductTag productTag : productTagList) {
			productTagEntityList.add(productTagToProductTagEntity(productTag));
		}
		return productTagEntityList;
	}

	public List<ProductTag> productTagEntityListToProductTagList(List<ProductTagEntity> productTagEntityList) {
		List<ProductTag> productTagList = new ArrayList<>();
		for (ProductTagEntity productTagEntity : productTagEntityList) {
			productTagList.add(productTagEntityToProductTag(productTagEntity));
		}
		return productTagList;
	}
}
