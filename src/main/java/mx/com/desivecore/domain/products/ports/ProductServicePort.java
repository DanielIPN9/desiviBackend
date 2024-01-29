package mx.com.desivecore.domain.products.ports;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.products.models.Product;
import mx.com.desivecore.domain.products.models.ProductSearchParams;

public interface ProductServicePort {

	ResponseModel createProduct(Product product);

	ResponseModel viewProductListByParams(ProductSearchParams productSearchParams);

	ResponseModel viewAllProductList();

	ResponseModel viewProductDetailById(Long productId);

	ResponseModel updateProductById(Product product);

}
