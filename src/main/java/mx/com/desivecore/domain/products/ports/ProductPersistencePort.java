package mx.com.desivecore.domain.products.ports;

import java.util.List;

import mx.com.desivecore.domain.products.models.Product;
import mx.com.desivecore.domain.products.models.ProductAvailability;
import mx.com.desivecore.domain.products.models.ProductOutputSummary;
import mx.com.desivecore.domain.products.models.ProductSearchParams;

public interface ProductPersistencePort {

	Product saveProduc(Product product);

	void saveAvailability(List<ProductAvailability> productAvailabilityList, Product product);

	ProductAvailability findByProducIdAndBranchId(Long productId, Long branchId);

	List<Product> viewProductListByParams(ProductSearchParams productSearchParams);

	List<Product> viewALLProduct();

	Product viewProductDetailById(Long productId);

	Product findProductBySku(String sku);

	Product findProductBySkuAndIdNot(String sku, Long productId);

	List<ProductOutputSummary> findAllByBranchId(Long branchId);

	boolean changeProductStatusById(boolean status, Long productId);
	
	List<Product> viewALLProductByStatus(boolean statis);

}
