package mx.com.desivecore.infraestructure.branches.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.infraestructure.branches.entities.BranchEntity;

@Component
public class BranchConverter {

	public BranchEntity branchToBranchEntity(Branch branch) {

		BranchEntity branchEntity = new BranchEntity();

		branchEntity.setBranchId(branch.getBranchId());
		branchEntity.setName(branch.getName());
		branchEntity.setStreet(branch.getStreet());
		branchEntity.setExternalNumber(branch.getExternalNumber());
		branchEntity.setMunicipality(branch.getMunicipality());
		branchEntity.setColony(branch.getColony());
		branchEntity.setCp(branch.getCp());
		branchEntity.setState(branch.getState());

		return branchEntity;
	}

	public Branch branchEntityToBranch(BranchEntity branchEntity) {

		Branch branch = new Branch();

		branch.setBranchId(branchEntity.getBranchId());
		branch.setName(branchEntity.getName());
		branch.setStreet(branchEntity.getStreet());
		branch.setExternalNumber(branchEntity.getExternalNumber());
		branch.setMunicipality(branchEntity.getMunicipality());
		branch.setColony(branchEntity.getColony());
		branch.setCp(branchEntity.getCp());
		branch.setState(branchEntity.getState());

		return branch;
	}

	public List<BranchEntity> branchListToBranchEntityList(List<Branch> branchList) {
		List<BranchEntity> branchEntityList = new ArrayList<>();
		for (Branch branch : branchList) {
			branchEntityList.add(branchToBranchEntity(branch));
		}
		return branchEntityList;
	}

	public List<Branch> branchEntityListToBranchList(List<BranchEntity> branchEntityList) {
		List<Branch> branchList = new ArrayList<>();
		for (BranchEntity branchEntity : branchEntityList) {
			branchList.add(branchEntityToBranch(branchEntity));
		}
		return branchList;
	}

}
