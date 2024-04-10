package mx.com.desivecore.infraestructure.products;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.products.models.Product;
import mx.com.desivecore.domain.products.models.ProductAvailability;
import mx.com.desivecore.domain.products.models.ProductOutputSummary;
import mx.com.desivecore.domain.products.models.ProductSearchParams;
import mx.com.desivecore.domain.products.ports.ProductPersistencePort;
import mx.com.desivecore.infraestructure.products.converters.ProductAvailabilityConverter;
import mx.com.desivecore.infraestructure.products.converters.ProductConverter;
import mx.com.desivecore.infraestructure.products.entities.ProductAvailabilityEntity;
import mx.com.desivecore.infraestructure.products.entities.ProductEntity;
import mx.com.desivecore.infraestructure.products.repositories.CustomDSLProductRepository;
import mx.com.desivecore.infraestructure.products.repositories.ProductAvailabilityRepository;
import mx.com.desivecore.infraestructure.products.repositories.ProductRepository;

@Log
@Service
public class ProductPersistenceImpl implements ProductPersistencePort {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductConverter productConverter;

	@Autowired
	private ProductAvailabilityRepository productAvailabilityRepository;

	@Autowired
	private ProductAvailabilityConverter productAvailabilityConverter;

	@Autowired
	private CustomDSLProductRepository customDSLProductRepository;

	@Override
	public Product saveProduc(Product product) {
		try {
			log.info("INIT saveProduc()");
			ProductEntity productEntity = productConverter.productToProductEntity(product);
			productEntity = productRepository.save(productEntity);
			return productConverter.productEntityToProduct(productEntity);
		} catch (Exception e) {
			log.severe("EXCEPTION ON SAVE: " + e.getMessage());
			return null;
		}
	}

	@Override
	public void saveAvailability(List<ProductAvailability> productAvailabilityList, Product product) {
		try {
			log.info("INIT saveAvailability()");
			for (ProductAvailability productAvailability : productAvailabilityList) {
				ProductAvailabilityEntity productAvailabilityEntity = productAvailabilityConverter
						.productAvailabilityToProductAvailabilityEntity(productAvailability);
				try {
					productAvailabilityEntity = productAvailabilityRepository.save(productAvailabilityEntity);
				} catch (Exception e) {
					log.severe(e.getMessage());
				}
			}
		} catch (Exception e) {
			log.severe("EXCEPTION ON SAVE AVAILABILITY: " + e.getMessage());
		}
	}

	@Override
	public List<Product> viewProductListByParams(ProductSearchParams productSearchParams) {
		try {
			log.info("INIT viewProductListByParams()");
			return null;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<Product> viewALLProduct() {
		try {
			log.info("INIT viewALLProduct()");
			List<ProductEntity> productEntityList = productRepository.findAll();
			return productConverter.productEntityListToProductList(productEntityList);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public Product viewProductDetailById(Long productId) {
		try {
			log.info("INIT viewProductDetailById()");
			Optional<ProductEntity> productOptional = productRepository.findById(productId);
			Product product = null;
			if (!productOptional.isPresent())
				return null;

			product = productConverter.productEntityToProduct(productOptional.get());

			List<ProductAvailabilityEntity> productAvailabilityEntityList = productAvailabilityRepository
					.findByProductId(productId);
			if (!productAvailabilityEntityList.isEmpty())
				product.setAvailability(productAvailabilityConverter
						.productAvailabilityEntityListToProductAvailabilityList(productAvailabilityEntityList));

			return product;

		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public Product findProductBySku(String sku) {
		try {
			log.info("INIT findProductBySku()");
			Optional<ProductEntity> productOptional = productRepository.findBySku(sku);
			if (productOptional.isPresent())
				return productConverter.productEntityToProduct(productOptional.get());
			return null;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public Product findProductBySkuAndIdNot(String sku, Long productId) {
		try {
			log.info("INIT findProductBySkuAndIdNot()");
			Optional<ProductEntity> productOptional = productRepository.findBySkuAndIdNot(null, productId);
			if (productOptional.isPresent())
				return productConverter.productEntityToProduct(productOptional.get());
			return null;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public ProductAvailability findByProducIdAndBranchId(Long productId, Long branchId) {
		try {
			log.info("INIT findByProducIdAndBranchId()");
			Optional<ProductAvailabilityEntity> optional = productAvailabilityRepository
					.findByBranchIdAndProductId(branchId, productId);
			if (optional.isPresent())
				return productAvailabilityConverter.productAvailabilityEntityToProductAvailability(optional.get());
			return null;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<ProductOutputSummary> findAllByBranchId(Long branchId) {
		try {
			return customDSLProductRepository.findAllByBranchId(branchId);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public boolean changeProductStatusById(boolean status, Long productId) {
		try {
			int updatedRow = productRepository.enableById(productId, status);
			if (updatedRow <= 0)
				return false;
			return true;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return false;
		}
	}

	@Override
	public List<Product> viewALLProductByStatus(boolean status) {
		try {
			log.info("INIT viewALLProductByStatus()");
			List<ProductEntity> productEntityList = productRepository.findAllByStatus(status);
			return productConverter.productEntityListToProductList(productEntityList);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

}
