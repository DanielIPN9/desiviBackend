package mx.com.desivecore.infraestructure.products.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.products.models.Product;
import mx.com.desivecore.infraestructure.products.entities.ProductEntity;

@Component
public class ProductConverter {

	public ProductEntity productToProductEntity(Product product) {

		ProductEntity productEntity = new ProductEntity();

		productEntity.setProductId(product.getProductId());
		productEntity.setSku(product.getSku());
		productEntity.setName(product.getName());
		productEntity.setUnitMeasure(product.getUnitMeasure());
		productEntity.setIva(product.getIva());
		productEntity.setUnitSellingPrice(product.getUnitSellingPrice());
		productEntity.setUnitPurchasePrice(product.getUnitPurchasePrice());
		productEntity.setStatus(product.isStatus());

		return productEntity;
	}

	public Product productEntityToProduct(ProductEntity productEntity) {

		Product product = new Product();

		product.setProductId(productEntity.getProductId());
		product.setSku(productEntity.getSku());
		product.setName(productEntity.getName());
		product.setUnitMeasure(productEntity.getUnitMeasure());
		product.setIva(productEntity.getIva());
		product.setUnitSellingPrice(productEntity.getUnitSellingPrice());
		product.setUnitPurchasePrice(productEntity.getUnitPurchasePrice());
		product.setStatus(productEntity.isStatus());

		return product;
	}

	public List<ProductEntity> productListToProductEntityList(List<Product> productList) {
		List<ProductEntity> productEntityList = new ArrayList<>();
		for (Product product : productList) {
			productEntityList.add(productToProductEntity(product));
		}
		return productEntityList;
	}

	public List<Product> productEntityListToProductList(List<ProductEntity> productEntityList) {
		List<Product> productList = new ArrayList<>();
		for (ProductEntity productEntity : productEntityList) {
			productList.add(productEntityToProduct(productEntity));
		}
		return productList;
	}

}
