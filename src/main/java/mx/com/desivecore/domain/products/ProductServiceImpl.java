package mx.com.desivecore.domain.products;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.branches.ports.BranchPersistencePort;
import mx.com.desivecore.domain.products.models.Product;
import mx.com.desivecore.domain.products.models.ProductAvailability;
import mx.com.desivecore.domain.products.models.ProductSearchParams;
import mx.com.desivecore.domain.products.ports.ProductPersistencePort;
import mx.com.desivecore.domain.products.ports.ProductServicePort;
import mx.com.desivecore.infraestructure.configuration.exceptions.InternalError;
import mx.com.desivecore.infraestructure.configuration.exceptions.ValidationError;

@Log
@Service
public class ProductServiceImpl implements ProductServicePort {

	@Autowired
	private ProductPersistencePort productPersistencePort;

	@Autowired
	private BranchPersistencePort branchPersistencePort;

	private ProductValidator productValidator = new ProductValidator();

	@Override
	public ResponseModel createProduct(Product product) {
		log.info("INIT createProduct()");
		String validations = productValidator.validOperativeDataToCreate(product, productPersistencePort);
		if (!validations.isEmpty()) {
			log.warning("VALIDATION ERROR: " + validations);
			throw new ValidationError("Los datos ingresados son invalidos: " + validations);
		}
		Product productCreated = productPersistencePort.saveProduc(product);
		if (productCreated == null) {
			log.severe("ERROR EN CREACION DE REGISTRO");
			throw new InternalError();
		}

		if (product.getAvailability() != null) {
			log.info("SAVE AVAILABILITY");
			saveAvailability(product.getAvailability(), productCreated);
		}
		return new ResponseModel(productCreated);
	}

	@Override
	public ResponseModel viewAllProductList() {
		log.info("INIT viewAllProductList()");
		List<Product> productList = productPersistencePort.viewALLProduct();
		if (productList == null)
			return new ResponseModel(new ArrayList<>());
		return new ResponseModel(productList);
	}

	@Override
	public ResponseModel viewProductListByParams(ProductSearchParams productSearchParams) {
		log.info("INIT createProduct()");
		List<Product> productList = productPersistencePort.viewProductListByParams(productSearchParams);
		if (productList == null)
			return new ResponseModel(new ArrayList<>());
		return new ResponseModel(productList);
	}

	@Override
	public ResponseModel viewProductDetailById(Long productId) {
		log.info("INIT viewProductDetailById()");
		Product product = productPersistencePort.viewProductDetailById(productId);
		if (product == null) {
			log.warning("DATA NOT EXISTS: " + productId);
			throw new ValidationError("El registro buscado no existe");
		}
		return new ResponseModel(product);
	}

	@Override
	public ResponseModel updateProductById(Product product) {
		log.info("INIT updateProductById()");
		String validations = productValidator.validOperativeDataToUpdate(product, productPersistencePort);
		if (!validations.isEmpty()) {
			log.warning("VALIDATION ERROR: " + validations);
			throw new ValidationError("Los datos ingresados son invalidos: " + validations);
		}
		Product productUpdated = productPersistencePort.saveProduc(product);
		if (productUpdated == null) {
			log.severe("ERROR EN ACTUALIZACION DE REGISTRO");
			throw new InternalError();
		}

		if (product.getAvailability() != null) {
			log.info("SAVE AVAILABILITY");
			saveAvailability(product.getAvailability(), productUpdated);
		}
		return new ResponseModel(productUpdated);
	}

	private void saveAvailability(List<ProductAvailability> productAvailabilityList, Product product) {
		String validations = productValidator.validAvailabilityToSave(productAvailabilityList, branchPersistencePort,
				productPersistencePort);
		if (!validations.isEmpty()) {
			log.warning("ERROR SAVING PRODUCT AVAILABILITY");
			throw new ValidationError(validations);
		}
		for (ProductAvailability productAvailability : productAvailabilityList) {
			productAvailability.setProductId(product.getProductId());
		}
		productPersistencePort.saveAvailability(productAvailabilityList, product);
	}

}
