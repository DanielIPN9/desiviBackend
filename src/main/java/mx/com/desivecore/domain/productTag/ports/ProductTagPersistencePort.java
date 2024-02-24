package mx.com.desivecore.domain.productTag.ports;

import java.util.List;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.productTag.models.ProductTag;
import mx.com.desivecore.domain.productTag.models.ProductTagDocument;

public interface ProductTagPersistencePort {

	ProductTag saveProductTag(ProductTag productTag);

	ProductTag viewProductTagById(Long tagId);

	List<ProductTag> viewAllProductTag();

	boolean deleteProductTagById(Long tagId);

	ResponseModel generateDocumentTag(ProductTagDocument productTagDocument);

}
