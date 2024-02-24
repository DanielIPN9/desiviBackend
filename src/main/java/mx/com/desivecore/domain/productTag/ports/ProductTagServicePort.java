package mx.com.desivecore.domain.productTag.ports;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.productTag.models.ProductTag;

public interface ProductTagServicePort {
	ResponseModel createTag(ProductTag productTag);

	ResponseModel viewProductTagList();

	ResponseModel viewTagById(Long tagId);

	ResponseModel updateTag(ProductTag productTag);

	ResponseModel deleteTabById(Long tagId);

	ResponseModel viewProductActiveList();

	ResponseModel generateDocumentTag(Long tagId);
}
