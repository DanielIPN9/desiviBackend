package mx.com.desivecore.domain.productTag;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.productTag.models.ProductTag;
import mx.com.desivecore.domain.productTag.models.ProductTagDocument;
import mx.com.desivecore.domain.productTag.models.ProductTagSummary;
import mx.com.desivecore.domain.productTag.ports.ProductTagPersistencePort;
import mx.com.desivecore.domain.productTag.ports.ProductTagServicePort;
import mx.com.desivecore.domain.products.models.Product;
import mx.com.desivecore.domain.products.ports.ProductPersistencePort;
import mx.com.desivecore.infraestructure.configuration.exceptions.InternalError;
import mx.com.desivecore.infraestructure.configuration.exceptions.ValidationError;

@Log
@Service
public class ProductTagServiceImpl implements ProductTagServicePort {

	@Autowired
	private ProductPersistencePort productPersistencePort;

	@Autowired
	private ProductTagPersistencePort productTagPersistencePort;

	private TagValidator tagValidator = new TagValidator();

	@Override
	public ResponseModel createTag(ProductTag productTag) {
		log.info("INIT createTag()");
		String validations = tagValidator.validOperativeDataToGenerateTag(productTag);
		if (!validations.isEmpty()) {
			log.warning("BAD PARAMS: " + validations);
			throw new ValidationError(validations);
		}
		ProductTag productTagCreated = productTagPersistencePort.saveProductTag(productTag);
		if (productTagCreated == null)
			throw new InternalError();
		return new ResponseModel(productTagCreated);
	}

	@Override
	public ResponseModel viewProductTagList() {
		log.info("INIT viewProductTagList");
		List<ProductTag> productTagList = productTagPersistencePort.viewAllProductTag();
		if (productTagList == null)
			return new ResponseModel(new ArrayList<>());
		List<ProductTagSummary> productTagSummaryList = new ArrayList<>();
		for (ProductTag productTag : productTagList) {
			productTagSummaryList.add(new ProductTagSummary(productTag.getTagId(), productTag.getLot(),
					productTag.getProduct().getName()));
		}
		return new ResponseModel(productTagSummaryList);
	}

	@Override
	public ResponseModel viewTagById(Long tagId) {
		log.info("INIT viewTagById()");
		ProductTag productTag = productTagPersistencePort.viewProductTagById(tagId);
		if (productTag == null)
			throw new ValidationError("Etiqueta no encontrada");
		return new ResponseModel(productTag);
	}

	@Override
	public ResponseModel updateTag(ProductTag productTag) {
		log.info("INIT createTag()");
		String validations = tagValidator.validOperativeDataToGenerateTag(productTag);
		if (!validations.isEmpty()) {
			log.warning("BAD PARAMS: " + validations);
			throw new ValidationError(validations);
		}
		ProductTag productTagUpdated = productTagPersistencePort.saveProductTag(productTag);
		if (productTagUpdated == null)
			throw new InternalError();
		return new ResponseModel(productTagUpdated);
	}

	@Override
	public ResponseModel deleteTabById(Long tagId) {
		log.info("INIT deleteTabById()");
		boolean isDelated = productTagPersistencePort.deleteProductTagById(tagId);
		if (!isDelated)
			throw new InternalError();
		return new ResponseModel(isDelated);
	}

	@Override
	public ResponseModel viewProductActiveList() {
		log.info("INIT viewProductActiveList()");
		List<Product> productList = productPersistencePort.viewALLProduct();
		if (productList == null) {
			log.warning("EMPTY DATA");
			return new ResponseModel(new ArrayList<>());
		}
		return new ResponseModel(productList);
	}

	@Override
	public ResponseModel generateDocumentTag(Long tagId) {
		log.info("INIT generateDocumentTag()");
		ProductTag productTag = productTagPersistencePort.viewProductTagById(tagId);
		if (productTag == null)
			throw new ValidationError("Etiqueta no encontrada");
		ProductTagDocument productTagDocument = new ProductTagDocument(productTag);
		log.info(productTagDocument.toString());
		ResponseModel response = productTagPersistencePort.generateDocumentTag(productTagDocument);
		return response;
	}

}
