package mx.com.desivecore.domain.quarantine.ports;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.quarantine.models.ProductQuarantineAction;
import mx.com.desivecore.domain.quarantine.models.QuarantineSearchParams;
import mx.com.desivecore.domain.returnRemissionEntry.models.ReturnRemissionEntry;

public interface QuarantineServicePort {

	ResponseModel viewQuarantineStatusByParams(QuarantineSearchParams quarantineSearchParams);

	ResponseModel changeProductLocation(ProductQuarantineAction productQuarantineAction);

	ResponseModel viewAllQuarantineStatus();

	ResponseModel viewProductQuarantineDetailById(Long productId);

	void inputProductByRemissionEntry(ReturnRemissionEntry remissionEntry);

	void inputProductByRemissionOutput();

	ResponseModel viewQuarantineActionList();

	ResponseModel viewAllProductSummary();

	ResponseModel viewAllBranchSummary();
}
