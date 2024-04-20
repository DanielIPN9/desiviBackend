package mx.com.desivecore.domain.productTag;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.domain.branches.models.BranchPhone;
import mx.com.desivecore.domain.branches.models.BranchSummary;
import mx.com.desivecore.domain.branches.ports.BranchPersistencePort;
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

	@Autowired
	private BranchPersistencePort branchPersistencePort;

	private TagValidator tagValidator = new TagValidator();

	@Override
	public ResponseModel createTag(ProductTag productTag) {
		log.info("INIT createTag()");
		String validations = tagValidator.validOperativeDataToGenerateTag(productTag);
		if (!validations.isEmpty()) {
			log.warning("BAD PARAMS: " + validations);
			throw new ValidationError(validations);
		}
		setPhoneNumber(productTag);
		ProductTag productTagCreated = productTagPersistencePort.saveProductTag(productTag);
		if (productTagCreated == null)
			throw new InternalError();
		return new ResponseModel(productTagCreated);
	}

	private void setPhoneNumber(ProductTag productTag) {
		String phoneNumber = "";
		if (productTag.getPhone() != null) {
			if(productTag.getPhone().getPhone() != null) {
				phoneNumber = productTag.getPhone().getPhone();
			}
			
		} else {
			phoneNumber = productTag.getPhoneNumber();
		}
		
		productTag.setPhoneNumber(phoneNumber);
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
		setPhoneNumber(productTag);
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
		List<Product> productList = productPersistencePort.viewALLProductByStatus(true);
		if (productList == null) {
			log.warning("EMPTY DATA");
			return new ResponseModel(new ArrayList<>());
		}
		return new ResponseModel(productList);
	}

	@Override
	public ResponseModel viewBranchList() {
		log.info("INIT viewBranchList()");
		List<Branch> branchList = branchPersistencePort.findAllBranch();
		List<BranchSummary> branchSummaryList = new ArrayList<>();
		if (branchList == null)
			return new ResponseModel(branchSummaryList);
		for (Branch branch : branchList) {
			branchSummaryList.add(new BranchSummary(branch.getBranchId(), branch.getName()));
		}
		return new ResponseModel(branchSummaryList);
	}

	@Override
	public ResponseModel viewBranchPhoneList(Long branchId) {
		log.info("INIT viewBranchPhoneList()");
		List<BranchPhone> branchPhoneList = branchPersistencePort.findRecordListByBranchId(branchId);
		if (branchPhoneList == null)
			return new ResponseModel(new ArrayList<>());
		return new ResponseModel(branchPhoneList);
	}

	@Override
	public ResponseModel generateDocumentTag(Long tagId) {
		log.info("INIT generateDocumentTag()");
		ProductTag productTag = productTagPersistencePort.viewProductTagById(tagId);
		if (productTag == null)
			throw new ValidationError("Etiqueta no encontrada");
		Branch branch = branchPersistencePort.findBranchById(productTag.getBranch().getBranchId());
		ProductTagDocument productTagDocument = new ProductTagDocument(productTag, branch);
		ResponseModel response = productTagPersistencePort.generateDocumentTag(productTagDocument);
		return response;
	}

}
