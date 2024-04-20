package mx.com.desivecore.infraestructure.branches.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.branches.models.BranchPhone;
import mx.com.desivecore.infraestructure.branches.entities.BranchPhoneEntity;

@Component
public class BranchPhoneConverter {

	public BranchPhoneEntity branchPhoneToBranchPhoneEntity(BranchPhone branchPhone, Long branchId) {

		BranchPhoneEntity branchPhoneEntity = new BranchPhoneEntity();

		branchPhoneEntity.setBranchId(branchId);
		branchPhoneEntity.setPhone(branchPhone.getPhone());
		branchPhoneEntity.setExtension(branchPhone.getExtension());

		return branchPhoneEntity;
	}

	public BranchPhone branchPhoneEntityToBranchPhone(BranchPhoneEntity branchPhoneEntity) {
		BranchPhone branchPhone = new BranchPhone();
		branchPhone.setPhone(branchPhoneEntity.getPhone());
		branchPhone.setExtension(branchPhoneEntity.getExtension());
		return branchPhone;
	}

	public List<BranchPhoneEntity> branchPhoneListToBranchPhoneEntityList(List<BranchPhone> branchPhoneList,
			Long branchId) {
		List<BranchPhoneEntity> branchPhoneEntityList = new ArrayList<>();
		for (BranchPhone branchPhone : branchPhoneList) {
			branchPhoneEntityList.add(branchPhoneToBranchPhoneEntity(branchPhone, branchId));
		}
		return branchPhoneEntityList;
	}

	public List<BranchPhone> branchPhoneEntityListToBranchPhoneList(List<BranchPhoneEntity> branchPhoneEntityList) {
		List<BranchPhone> branchPhoneList = new ArrayList<>();
		for (BranchPhoneEntity branchPhoneEntity : branchPhoneEntityList) {
			branchPhoneList.add(branchPhoneEntityToBranchPhone(branchPhoneEntity));
		}
		return branchPhoneList;
	}
}
