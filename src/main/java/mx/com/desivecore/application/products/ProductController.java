package mx.com.desivecore.application.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.products.models.Product;
import mx.com.desivecore.domain.products.models.ProductSearchParams;
import mx.com.desivecore.domain.products.ports.ProductServicePort;

@Log
@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductServicePort productServicePort;

	@PostMapping("/create")
	public ResponseEntity<?> createProduct(@RequestBody Product product) {
		log.info("INIT createProduct()");
		log.info(String.format("PARAMS: [%s]", product.toString()));
		ResponseModel response = productServicePort.createProduct(product);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-all")
	public ResponseEntity<?> viewAllProduct() {
		log.info("INIT viewAllProduct()");
		ResponseModel response = productServicePort.viewAllProductList();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/search")
	public ResponseEntity<?> viewProductListByParams(@RequestBody ProductSearchParams productSearchParams) {
		log.info("INIT viewProductListByParams()");
		log.info(String.format("PARAMS: [%s]", productSearchParams.toString()));
		ResponseModel response = productServicePort.viewProductListByParams(productSearchParams);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/view-detail/{productId}")
	public ResponseEntity<?> viewProductDetailById(@PathVariable Long productId) {
		log.info("INIT viewProductDetailById()");
		log.info(String.format("PARAMS: [productId: %s]", productId.toString()));
		ResponseModel response = productServicePort.viewProductDetailById(productId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateProductById(@RequestBody Product product) {
		log.info("INIT updateProductById()");
		log.info(String.format("PARAMS: [%s]", product.toString()));
		ResponseModel response = productServicePort.updateProductById(product);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/change-status/{status}/{id}")
	public ResponseEntity<?> changeProductStatusById(@PathVariable String status, @PathVariable Long productId) {
		log.info("INIT changeUserStatusById()");
		log.info(String.format("PARAMS:[status: %s, id: %s]", status, productId.toString()));
		ResponseModel responseModel = productServicePort.changeStatusById(status, productId);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}
}
