package mx.com.desivecore.infraestructure.certificate.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.certificate.models.ProductCertificate;
import mx.com.desivecore.infraestructure.certificate.entities.ProductCertificateEntity;

@Component
public class ProductCertificateConverter {

	public ProductCertificateEntity productCertificateToEntity(ProductCertificate productCertificate) {

		ProductCertificateEntity productCertificateEntity = new ProductCertificateEntity();

		productCertificateEntity.setCertificateId(productCertificate.getCertificateId());
		productCertificateEntity.setRemissionEntryId(productCertificate.getRemissionEntryId());
		productCertificateEntity.setProductId(productCertificate.getProductId());
		productCertificateEntity.setProductName(productCertificate.getProductName());
		productCertificateEntity.setSku(productCertificate.getSku());
		productCertificateEntity.setClientName(productCertificate.getClientName());
		productCertificateEntity.setLot(productCertificate.getLot());
		productCertificateEntity.setCreationDate(productCertificate.getCreationDate());

		return productCertificateEntity;
	}

	public ProductCertificate entityToProductCertificate(ProductCertificateEntity productCertificateEntity) {

		ProductCertificate productCertificate = new ProductCertificate();

		productCertificate.setCertificateId(productCertificateEntity.getCertificateId());
		productCertificate.setRemissionEntryId(productCertificateEntity.getRemissionEntryId());
		productCertificate.setProductId(productCertificateEntity.getProductId());
		productCertificate.setProductName(productCertificateEntity.getProductName());
		productCertificate.setSku(productCertificateEntity.getSku());
		productCertificate.setClientName(productCertificateEntity.getClientName());
		productCertificate.setLot(productCertificateEntity.getLot());
		productCertificate.setCreationDate(productCertificateEntity.getCreationDate());

		return productCertificate;
	}

	public List<ProductCertificateEntity> productCertificateListToEntityList(
			List<ProductCertificate> productCertificateList) {
		List<ProductCertificateEntity> certificateEntityList = new ArrayList<>();
		for (ProductCertificate productCertificate : productCertificateList) {
			certificateEntityList.add(productCertificateToEntity(productCertificate));
		}
		return certificateEntityList;
	}

	public List<ProductCertificate> entityListToProductCertificateList(
			List<ProductCertificateEntity> productCertificateEntityList) {
		List<ProductCertificate> productCertificateList = new ArrayList<>();
		for (ProductCertificateEntity productCertificateEntity : productCertificateEntityList) {
			productCertificateList.add(entityToProductCertificate(productCertificateEntity));
		}
		return productCertificateList;
	}

}
