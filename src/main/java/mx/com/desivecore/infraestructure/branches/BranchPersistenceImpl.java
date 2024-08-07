package mx.com.desivecore.infraestructure.branches;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.domain.branches.models.BranchPhone;
import mx.com.desivecore.domain.branches.ports.BranchPersistencePort;
import mx.com.desivecore.infraestructure.branches.converters.BranchConverter;
import mx.com.desivecore.infraestructure.branches.converters.BranchPhoneConverter;
import mx.com.desivecore.infraestructure.branches.entities.BranchEntity;
import mx.com.desivecore.infraestructure.branches.entities.BranchPhoneEntity;
import mx.com.desivecore.infraestructure.branches.repositories.BranchPhoneRepository;
import mx.com.desivecore.infraestructure.branches.repositories.BranchReposirory;

@Log
@Service
public class BranchPersistenceImpl implements BranchPersistencePort {

	@Autowired
	private BranchConverter branchConverter;

	@Autowired
	private BranchReposirory branchReposirory;

	@Autowired
	private BranchPhoneConverter branchPhoneConverter;

	@Autowired
	private BranchPhoneRepository branchPhoneRepository;

	@Override
	public Branch saveBranch(Branch branch) {
		try {
			log.info("INIT saveBranch()");
			BranchEntity branchEntity = branchConverter.branchToBranchEntity(branch);
			branchEntity = branchReposirory.save(branchEntity);

			if (branch.getBranchId() != null) {
				List<BranchPhoneEntity> branchPhoneEntityList = branchPhoneRepository
						.findAllByBranchId(branch.getBranchId());

				for (BranchPhoneEntity branchPhoneEntity : branchPhoneEntityList) {
					branchPhoneRepository.delete(branchPhoneEntity);
				}
			}

			if (branch.getPhones() != null) {
				List<BranchPhoneEntity> branchPhoneList = branchPhoneConverter
						.branchPhoneListToBranchPhoneEntityList(branch.getPhones(), branchEntity.getBranchId());
				for (BranchPhoneEntity branchPhoneEntity : branchPhoneList) {
					branchPhoneRepository.save(branchPhoneEntity);
				}

			}

			return branchConverter.branchEntityToBranch(branchEntity);
		} catch (Exception e) {
			log.severe("Exception: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<Branch> findAllBranch() {
		try {
			log.info("INIT findAllBranch()");
			List<BranchEntity> branchEntityList = branchReposirory.findAll();
			return branchConverter.branchEntityListToBranchList(branchEntityList);
		} catch (Exception e) {
			log.severe("Exception: " + e.getMessage());
			return null;
		}
	}

	@Override
	public Branch findBranchById(Long branchId) {
		try {
			Optional<BranchEntity> branchOptional = branchReposirory.findById(branchId);
			Branch branch = null;
			if (branchOptional.isPresent()) {
				branch = branchConverter.branchEntityToBranch(branchOptional.get());
				List<BranchPhoneEntity> branchPhoneEntityList = branchPhoneRepository
						.findAllByBranchId(branch.getBranchId());
				branch.setPhones(branchPhoneConverter.branchPhoneEntityListToBranchPhoneList(branchPhoneEntityList));
			}
			return branch;
		} catch (Exception e) {
			log.severe("Exception: " + e.getMessage());
			return null;
		}
	}

	@Override
	public Branch findBranchByName(String name) {
		try {
			Optional<BranchEntity> branchOptional = branchReposirory.findByName(name);
			Branch branch = null;
			if (branchOptional.isPresent())
				branch = branchConverter.branchEntityToBranch(branchOptional.get());
			return branch;
		} catch (Exception e) {
			log.severe("Exception: " + e.getMessage());
			return null;
		}
	}

	@Override
	public Branch findBranchByNameAndIdNot(String name, Long branchId) {
		try {
			Optional<BranchEntity> branchOptional = branchReposirory.findByNameAndIdNot(name, branchId);
			Branch branch = null;
			if (branchOptional.isPresent())
				branch = branchConverter.branchEntityToBranch(branchOptional.get());
			return branch;
		} catch (Exception e) {
			log.severe("Exception: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<BranchPhone> findRecordListByBranchId(Long branchId) {
		try {
			Optional<BranchEntity> branchOptional = branchReposirory.findById(branchId);
			List<BranchPhone> branchPhoneList = new ArrayList<>();
			if (branchOptional.isPresent()) {
				List<BranchPhoneEntity> branchPhoneEntityList = branchPhoneRepository
						.findAllByBranchId(branchOptional.get().getBranchId());
				branchPhoneList = branchPhoneConverter.branchPhoneEntityListToBranchPhoneList(branchPhoneEntityList);
			}
			return branchPhoneList;
		} catch (Exception e) {
			log.severe("Exception: " + e.getMessage());
			return null;
		}
	}

}
