package mx.com.desivecore.domain.suppliers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.suppliers.models.Supplier;
import mx.com.desivecore.domain.suppliers.ports.SupplierPersistencePort;
import mx.com.desivecore.domain.suppliers.ports.SupplierServicePort;
import mx.com.desivecore.infraestructure.configuration.exceptions.InternalError;
import mx.com.desivecore.infraestructure.configuration.exceptions.ValidationError;

@Log
@Service
public class SupplierServiceImpl implements SupplierServicePort {

	@Autowired
	private SupplierPersistencePort supplierPersistencePort;

	private SupplierValidator supplierValidator = new SupplierValidator();

	@Override
	public ResponseModel createSupplier(Supplier supplier) {
		log.info("INIT createSupplier()");
		String validations = supplierValidator.validOperativeDataToCreate(supplier, supplierPersistencePort);
		if (!validations.isEmpty()) {
			log.warning("BAD PARAMS: " + validations);
			throw new ValidationError(validations);
		}

		Supplier supplierCreated = supplierPersistencePort.saveSupplier(supplier);
		if (supplierCreated == null) {
			log.severe("INTERNAL ERROR ON CREATE");
			throw new InternalError();
		}

		return new ResponseModel(supplierCreated);
	}

	@Override
	public ResponseModel viewAllSupplier() {
		log.info("INIT viewAllSupplier()");
		List<Supplier> supplierLits = supplierPersistencePort.viewAllSupplier();
		if (supplierLits == null) {
			log.info("EMPTY DATA");
			return new ResponseModel(new ArrayList<>());
		}
		return new ResponseModel(supplierLits);
	}

	@Override
	public ResponseModel viewSupplierDetailById(Long supplierId) {
		log.info("INIT viewSupplierDetailById()");
		Supplier supplier = supplierPersistencePort.viewSupplierDetailById(supplierId);
		if (supplier == null) {
			log.info("DATA NOT FOUND");
			throw new ValidationError("El registro no existe");
		}
		return new ResponseModel(supplier);
	}

	@Override
	public ResponseModel updateSupplierById(Supplier supplier) {
		log.info("INIT updateSupplierById()");
		String validations = supplierValidator.validOperativeDataToUpdate(supplier, supplierPersistencePort);
		if (!validations.isEmpty()) {
			log.warning("BAD PARAMS: " + validations);
			throw new ValidationError(validations);
		}

		Supplier supplierUpdated = supplierPersistencePort.saveSupplier(supplier);
		if (supplierUpdated == null) {
			log.severe("INTERNAL ERROR ON UPDATE");
			throw new InternalError();
		}

		return new ResponseModel(supplierUpdated);
	}

	@Override
	public ResponseModel changeStatusById(Long supplierId, String status) {
		log.info("INIT changeStatusById()");
		boolean userStatus = status.equals("ACTIVE") ? true : false;
		boolean changeStatus = supplierPersistencePort.changeStatusById(supplierId, userStatus);
		if (!changeStatus)
			throw new InternalError();
		return new ResponseModel(changeStatus);
	}

}
