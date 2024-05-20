package mx.com.desivecore.infraestructure.quote.converters;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.quote.models.QuoteProduct;
import mx.com.desivecore.infraestructure.products.converters.ProductConverter;
import mx.com.desivecore.infraestructure.products.entities.ProductEntity;
import mx.com.desivecore.infraestructure.products.repositories.ProductRepository;
import mx.com.desivecore.infraestructure.quote.entities.QuoteProductEntity;

@Component
public class QuoteProductConverter {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductConverter productConverter;

	public QuoteProductEntity quoteProductToEntity(QuoteProduct quoteProduct, Long quoteProductId) {
		QuoteProductEntity quoteProductEntity = new QuoteProductEntity();

		quoteProductEntity.setQuoteOrderId(quoteProductId);
		quoteProductEntity.setProductId(quoteProduct.getProduct().getProductId());
		quoteProductEntity.setAmount(quoteProduct.getAmount());
		quoteProductEntity.setUnitMeasure(quoteProduct.getUnitMeasure());
		quoteProductEntity.setSellingPrice(quoteProduct.getSellingPrice());
		quoteProductEntity.setProductDescription(quoteProduct.getProductDescription());
		quoteProductEntity.setIva(quoteProduct.getIva());
		quoteProductEntity.setNet(quoteProduct.getNet());
		quoteProductEntity.setTotal(quoteProduct.getTotal());

		return quoteProductEntity;
	}

	public QuoteProduct entityToQuoteProduct(QuoteProductEntity quoteProductEntity) {

		QuoteProduct quoteProduct = new QuoteProduct();

		quoteProduct.setAmount(quoteProductEntity.getAmount());
		quoteProduct.setUnitMeasure(quoteProductEntity.getUnitMeasure());
		quoteProduct.setSellingPrice(quoteProductEntity.getSellingPrice());
		quoteProduct.setProductDescription(quoteProductEntity.getProductDescription());
		quoteProduct.setIva(quoteProductEntity.getIva());
		quoteProduct.setNet(quoteProductEntity.getNet());
		quoteProduct.setTotal(quoteProductEntity.getTotal());

		Optional<ProductEntity> productOptional = productRepository.findById(quoteProductEntity.getProductId());
		if (productOptional.isPresent())
			quoteProduct.setProduct(productConverter.productEntityToProduct(productOptional.get()));

		return quoteProduct;
	}
}
