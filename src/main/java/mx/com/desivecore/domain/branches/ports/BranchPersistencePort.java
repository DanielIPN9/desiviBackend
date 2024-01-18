package mx.com.desivecore.domain.branches.ports;

import java.util.List;

import mx.com.desivecore.domain.branches.models.Branch;

public interface BranchPersistencePort {

	Branch saveBranch(Branch branch);

	List<Branch> findAllBranch();

	Branch findBranchById(Long branchId);

	Branch findBranchByName(String name);

	Branch findBranchByNameAndIdNot(String name, Long branchId);
}
