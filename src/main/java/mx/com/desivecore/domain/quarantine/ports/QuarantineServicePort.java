package mx.com.desivecore.domain.quarantine.ports;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.quarantine.models.ProductQuarantineAction;
import mx.com.desivecore.domain.quarantine.models.QuarantineSearchParams;
import mx.com.desivecore.domain.returnRemissionEntry.models.ReturnRemissionEntry;
import mx.com.desivecore.domain.returnRemissionOutput.models.ReturnRemissionOutput;

public interface QuarantineServicePort {

	ResponseModel viewQuarantineStatusByParams(QuarantineSearchParams quarantineSearchParams);

	ResponseModel changeProductLocation(ProductQuarantineAction productQuarantineAction);

	ResponseModel viewAllQuarantineStatus();

	ResponseModel viewProductQuarantineDetailById(Long productId);

	ResponseModel generateProductMovementByQuarantineId(Long quarantineId);

	void inputProductByRemissionEntry(ReturnRemissionEntry remissionEntry);

	void inputProductByRemissionOutput(ReturnRemissionOutput remissionOutput);

	ResponseModel viewQuarantineActionList();

	ResponseModel viewAllProductSummary();

	ResponseModel viewAllBranchSummary();
}
