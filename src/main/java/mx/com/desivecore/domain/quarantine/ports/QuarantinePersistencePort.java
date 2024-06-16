package mx.com.desivecore.domain.quarantine.ports;

import java.util.List;

import mx.com.desivecore.domain.quarantine.models.ProductQuarantine;
import mx.com.desivecore.domain.quarantine.models.ProductQuarantineSummary;
import mx.com.desivecore.domain.quarantine.models.QuarantineAction;
import mx.com.desivecore.domain.quarantine.models.QuarantineSearchParams;

public interface QuarantinePersistencePort {

	List<ProductQuarantineSummary> viewQuarantineStatusByParams(QuarantineSearchParams quarantineSearchParams);

	ProductQuarantineSummary viewProductQuarantineDetailByQuarantineId(Long quarantineId);

	ProductQuarantine viewProductQuarantineDetailByProductIdAndBranchId(Long productId, Long branchId);
	
	ProductQuarantine viewProductQuarantineByQuarantineId(Long quarantineId);

	Boolean updateProductQuarantine(ProductQuarantine productQuarantine);

	Boolean updateProductQuarantineList(List<ProductQuarantine> productQuarantineList);

	List<ProductQuarantineSummary> viewAllQuarantineStatus();

	List<QuarantineAction> viewActionList();

}
