package mx.com.desivecore.domain.branches;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.domain.branches.ports.BranchPersistencePort;
import mx.com.desivecore.domain.branches.ports.BranchServicePort;
import mx.com.desivecore.infraestructure.configuration.exceptions.InternalError;
import mx.com.desivecore.infraestructure.configuration.exceptions.ValidationError;

@Log
@Service
public class BranchServiceImpl implements BranchServicePort {

	@Autowired
	private BranchPersistencePort branchPersistencePort;

	private BranchValidator branchValidator = new BranchValidator();

	@Override
	public ResponseModel createBranch(Branch branch) {
		log.info("INIT createBranch()");
		String validations = branchValidator.validOperativeDataTCreate(branch, branchPersistencePort);
		if (!validations.isEmpty()) {
			log.warning("VALIDATION ERROR: " + validations);
			throw new ValidationError("Los datos ingresados son invalidos: " + validations);
		}
		Branch branchCreated = branchPersistencePort.saveBranch(branch);
		if (branchCreated == null) {
			log.severe("ERROR EN CREACION DE REGISTRO");
			throw new InternalError();
		}
		return new ResponseModel(branchCreated);
	}

	@Override
	public ResponseModel viewAllBranch() {
		log.info("INIT viewAllBranch()");
		List<Branch> branchList = branchPersistencePort.findAllBranch();
		if (branchList == null) {
			log.warning("EMPTY DATA");
			return new ResponseModel(new ArrayList<>());
		}
		return new ResponseModel(branchList);
	}

	@Override
	public ResponseModel viewBranchDetailById(Long branchId) {
		log.info("INIT viewBranchDetailById()");
		Branch branch = branchPersistencePort.findBranchById(branchId);
		if (branch == null) {
			log.warning("DATA NOT EXISTS: " + branchId);
			throw new ValidationError("El registro buscado no existe");
		}
		return new ResponseModel(branch);
	}

	@Override
	public ResponseModel updateBranchById(Branch branch) {
		log.info("INIT updateBranchById()");
		String validations = branchValidator.validOperativeDataToUpdate(branch, branchPersistencePort);
		if (!validations.isEmpty()) {
			log.warning("VALIDATION ERROR: " + validations);
			throw new ValidationError("Los datos ingresados son invalidos: " + validations);
		}
		Branch branchCreated = branchPersistencePort.saveBranch(branch);
		if (branchCreated == null) {
			log.severe("ERROR EN CREACION DE REGISTRO");
			throw new InternalError();
		}
		return new ResponseModel(branchCreated);
	}

}
