package mx.com.desivecore.domain.products.ports;

import java.util.List;

import mx.com.desivecore.domain.products.models.Product;
import mx.com.desivecore.domain.products.models.ProductAvailability;
import mx.com.desivecore.domain.products.models.ProductSearchParams;

public interface ProductPersistencePort {

	Product saveProduc(Product product);

	void saveAvailability(List<ProductAvailability> productAvailabilityList, Product product);

	List<Product> viewProductListByParams(ProductSearchParams productSearchParams);

	List<Product> viewALLProduct();

	Product viewProductDetailById(Long productId);

	Product findProductBySku(String sku);

	Product findProductBySkuAndIdNot(String sku, Long productId);

}
