package mx.com.desivecore.domain.branches.ports;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.branches.models.Branch;

public interface BranchServicePort {

	ResponseModel createBranch(Branch branch);

	ResponseModel viewAllBranch();

	ResponseModel viewBranchDetailById(Long branchId);

	ResponseModel updateBranchById(Branch branch);

}
