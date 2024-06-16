package mx.com.desivecore.domain.quarantine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.constants.QuarantineMovementEnum;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.domain.branches.models.BranchSummary;
import mx.com.desivecore.domain.branches.ports.BranchPersistencePort;
import mx.com.desivecore.domain.products.models.Product;
import mx.com.desivecore.domain.products.models.ProductAvailability;
import mx.com.desivecore.domain.products.models.ProductSummary;
import mx.com.desivecore.domain.products.ports.ProductPersistencePort;
import mx.com.desivecore.domain.quarantine.models.ProductQuarantine;
import mx.com.desivecore.domain.quarantine.models.ProductQuarantineAction;
import mx.com.desivecore.domain.quarantine.models.ProductQuarantineSummary;
import mx.com.desivecore.domain.quarantine.models.QuarantineAction;
import mx.com.desivecore.domain.quarantine.models.QuarantineSearchParams;
import mx.com.desivecore.domain.quarantine.ports.QuarantinePersistencePort;
import mx.com.desivecore.domain.quarantine.ports.QuarantineServicePort;
import mx.com.desivecore.domain.returnRemissionEntry.models.ReturnRemissionEntry;
import mx.com.desivecore.domain.returnRemissionOutput.models.ReturnRemissionOutput;
import mx.com.desivecore.infraestructure.configuration.exceptions.InternalError;
import mx.com.desivecore.infraestructure.configuration.exceptions.ValidationError;

@Log
@Service
public class QuarantineServiceImpl implements QuarantineServicePort {

	@Autowired
	private QuarantinePersistencePort quarantinePersistencePort;

	@Autowired
	private BranchPersistencePort branchPersistencePort;

	@Autowired
	private ProductPersistencePort productPersistencePort;

	private ReturnRemissionGenerator returnRemissionGenerator = new ReturnRemissionGenerator();

	private QuarantineValidation quarantineValidation = new QuarantineValidation();

	@Override
	public ResponseModel viewQuarantineStatusByParams(QuarantineSearchParams quarantineSearchParams) {
		List<ProductQuarantineSummary> productQuarantineSummaryList = Optional
				.ofNullable(quarantinePersistencePort.viewQuarantineStatusByParams(quarantineSearchParams))
				.orElse(new ArrayList<>());
		return new ResponseModel(productQuarantineSummaryList);
	}

	@Override
	public ResponseModel generateProductMovementByQuarantineId(Long quarantineId) {
		ProductQuarantine productQuarantine = Optional
				.ofNullable(quarantinePersistencePort.viewProductQuarantineByQuarantineId(quarantineId))
				.orElseThrow(() -> new ValidationError("Registro no encontrado"));
		Branch branch = branchPersistencePort.findBranchById(productQuarantine.getBranchId());
		Product product = productPersistencePort.viewProductDetailById(productQuarantine.getProductId());
		ProductQuarantineAction productQuarantineAction = new ProductQuarantineAction(productQuarantine, branch,
				product);
		return new ResponseModel(productQuarantineAction);
	}

	@Override
	public ResponseModel changeProductLocation(ProductQuarantineAction productQuarantineAction) {
		String validations = quarantineValidation.validOperativeData(productQuarantineAction);
		if (!validations.isEmpty())
			throw new ValidationError(validations);
		try {
			QuarantineMovementEnum movement = QuarantineMovementEnum
					.valueOf(productQuarantineAction.getAction().getActionCode());
			Long productId = productQuarantineAction.getProduct().getProductId();
			Long branchId = productQuarantineAction.getBranch().getBranchId();
			switch (movement) {
			case WAREHOUSE_OUTPUT:
				updateWarehouseOutput(productQuarantineAction, productId, branchId);
				break;
			case WAREHOUSE_INPUT:
				updateWarehouseOutput(productQuarantineAction, productId, branchId);
				updateWarehouseInput(productQuarantineAction, productId, branchId);
				break;
			default:
				throw new InternalError();
			}
			return new ResponseModel(true);
		} catch (Exception e) {
			throw new InternalError();
		}
	}

	private void updateWarehouseInput(ProductQuarantineAction productQuarantineAction, Long productId, Long branchId) {
		List<ProductAvailability> availabilityList = new ArrayList<>();
		ProductAvailability productAvailability = productPersistencePort.findByProducIdAndBranchId(productId, branchId);

		if (productAvailability != null) {
			productAvailability.updateAvailability(productQuarantineAction.getAmount());
			availabilityList.add(productAvailability);
		}

		productPersistencePort.saveAvailability(availabilityList, null);
	}

	private void updateWarehouseOutput(ProductQuarantineAction productQuarantineAction, Long productId, Long branchId) {
		ProductQuarantine productQuarantine;
		Double finalAmount;
		productQuarantine = Optional.ofNullable(
				quarantinePersistencePort.viewProductQuarantineDetailByProductIdAndBranchId(productId, branchId))
				.orElseThrow(() -> new InternalError());

		finalAmount = productQuarantine.getAmount() - productQuarantineAction.getAmount();
		productQuarantine.setAmount(finalAmount);

		quarantinePersistencePort.updateProductQuarantine(productQuarantine);
	}

	@Override
	public ResponseModel viewAllQuarantineStatus() {
		List<ProductQuarantineSummary> productQuarantineSummaryList = Optional
				.ofNullable(quarantinePersistencePort.viewAllQuarantineStatus()).orElse(new ArrayList<>());
		return new ResponseModel(productQuarantineSummaryList);
	}

	@Override
	public ResponseModel viewProductQuarantineDetailById(Long quarantineId) {
		ProductQuarantineSummary productQuarantineSummary = quarantinePersistencePort
				.viewProductQuarantineDetailByQuarantineId(quarantineId);
		if (productQuarantineSummary == null) {
			log.info("EMPTY DATA");
			throw new ValidationError("Registro no encontrado");
		}
		return new ResponseModel(productQuarantineSummary);
	}

	@Override
	public void inputProductByRemissionEntry(ReturnRemissionEntry remissionEntry) {

		log.info("GENERATE LIST BY RETRUN REMISSION ENTRY");
		List<ProductQuarantine> productQuarantineREList = returnRemissionGenerator
				.generateByRemissionEntry(remissionEntry);

		List<ProductQuarantine> productQuarantineUpdatedList = new ArrayList<>();
		for (ProductQuarantine productQuarantine : productQuarantineREList) {
			ProductQuarantine productQuarantineSaved = quarantinePersistencePort
					.viewProductQuarantineDetailByProductIdAndBranchId(productQuarantine.getProductId(),
							productQuarantine.getBranchId());
			if (productQuarantineSaved != null) {
				Double finalAmount = productQuarantineSaved.getAmount() + productQuarantine.getAmount();
				productQuarantineSaved.setAmount(finalAmount);
				productQuarantineUpdatedList.add(productQuarantineSaved);
			} else {
				productQuarantineUpdatedList.add(productQuarantine);
			}
		}

		quarantinePersistencePort.updateProductQuarantineList(productQuarantineUpdatedList);

	}

	@Override
	public void inputProductByRemissionOutput(ReturnRemissionOutput returnRemissionOutput) {
		log.info("GENERATE LIST BY RETRUN REMISSION ENTRY");
		List<ProductQuarantine> productQuarantineROList = returnRemissionGenerator
				.generateByRemssionOutput(returnRemissionOutput);

		List<ProductQuarantine> productQuarantineUpdatedList = new ArrayList<>();
		for (ProductQuarantine productQuarantine : productQuarantineROList) {
			ProductQuarantine productQuarantineSaved = quarantinePersistencePort
					.viewProductQuarantineDetailByProductIdAndBranchId(productQuarantine.getProductId(),
							productQuarantine.getBranchId());
			if (productQuarantineSaved != null) {
				Double finalAmount = productQuarantineSaved.getAmount() + productQuarantine.getAmount();
				productQuarantineSaved.setAmount(finalAmount);
				productQuarantineUpdatedList.add(productQuarantineSaved);
			} else {
				productQuarantineUpdatedList.add(productQuarantine);
			}
		}

		quarantinePersistencePort.updateProductQuarantineList(productQuarantineUpdatedList);

	}

	@Override
	public ResponseModel viewQuarantineActionList() {
		List<QuarantineAction> quarantineActionList = Optional.ofNullable(quarantinePersistencePort.viewActionList())
				.orElse(new ArrayList<>());
		return new ResponseModel(quarantineActionList);
	}

	@Override
	public ResponseModel viewAllProductSummary() {
		log.info("INIT viewProductActiveList()");
		List<Product> productList = Optional.ofNullable(productPersistencePort.viewALLProductByStatus(true))
				.orElse(Collections.emptyList());

		if (productList.isEmpty()) {
			log.warning("EMPTY DATA");
			return new ResponseModel(Collections.emptyList());
		}
		List<ProductSummary> productSummarylist = productList.stream()
				.map(product -> new ProductSummary(product.getProductId(), product.getSku(), product.getName(),
						product.getUnitMeasure()))
				.collect(Collectors.toList());
		return new ResponseModel(productSummarylist);
	}

	@Override
	public ResponseModel viewAllBranchSummary() {
		log.info("INIT viewBranchActiveList()");
		List<Branch> branchList = Optional.ofNullable(branchPersistencePort.findAllBranch())
				.orElse(Collections.emptyList());
		if (branchList == null) {
			log.warning("EMPTY DATA");
			return new ResponseModel(new ArrayList<>());
		}
		List<BranchSummary> branchSummaryList = branchList.stream()
				.map(branch -> new BranchSummary(branch.getBranchId(), branch.getName())).collect(Collectors.toList());
		return new ResponseModel(branchSummaryList);
	}
}