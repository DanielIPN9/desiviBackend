package mx.com.desivecore.infraestructure.suppliers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.suppliers.models.Supplier;
import mx.com.desivecore.domain.suppliers.ports.SupplierPersistencePort;
import mx.com.desivecore.infraestructure.suppliers.converters.SupplierConverter;
import mx.com.desivecore.infraestructure.suppliers.entities.SupplierEntity;
import mx.com.desivecore.infraestructure.suppliers.repositories.SupplierRepository;

@Log
@Service
public class SupplierPersistenceImpl implements SupplierPersistencePort {

	@Autowired
	private SupplierRepository supplierRepository;

	@Autowired
	private SupplierConverter supplierConverter;

	@Override
	public Supplier saveSupplier(Supplier supplier) {
		try {
			log.info("INIT saveSupplier()");
			SupplierEntity supplierEntity = supplierConverter.supplierToSupplierEntity(supplier);
			supplierEntity = supplierRepository.save(supplierEntity);
			return supplierConverter.supplierEntityToSupplier(supplierEntity);
		} catch (Exception e) {
			log.info("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<Supplier> viewAllSupplier() {
		try {
			log.info("INIT viewAllSupplier()");
			List<SupplierEntity> supplierEntityList = supplierRepository.findAll();
			return supplierConverter.supplierEntityListToSupplierList(supplierEntityList);
		} catch (Exception e) {
			log.info("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public Supplier viewSupplierDetailById(Long supplierId) {
		try {
			log.info("INIT viewSupplierDetailById()");
			Optional<SupplierEntity> supplierOptional = supplierRepository.findById(supplierId);
			if (supplierOptional.isPresent())
				return supplierConverter.supplierEntityToSupplier(supplierOptional.get());
			return null;
		} catch (Exception e) {
			log.info("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public boolean changeStatusById(Long supplierId, boolean status) {
		try {
			log.info("INIT changeStatusById()");
			int updatedRow = supplierRepository.enableById(supplierId, status);
			if (updatedRow <= 0)
				return false;
			return true;
		} catch (Exception e) {
			log.info("EXCEPTION: " + e.getMessage());
			return false;
		}
	}

	@Override
	public Supplier findSupplierByRfc(String rfc) {
		try {
			log.info("INIT findSupplierByRfc()");
			Optional<SupplierEntity> supplierOptional = supplierRepository.findByRfc(rfc);
			if (supplierOptional.isPresent())
				return supplierConverter.supplierEntityToSupplier(supplierOptional.get());
			return null;
		} catch (Exception e) {
			log.info("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public Supplier findSupplierByRfcAndIdNot(String rfc, Long supplierId) {
		try {
			log.info("INIT findSupplierByRfcAndIdNot()");
			Optional<SupplierEntity> supplierOptional = supplierRepository.findByRfcAndIdNot(rfc, supplierId);
			if (supplierOptional.isPresent())
				return supplierConverter.supplierEntityToSupplier(supplierOptional.get());
			return null;
		} catch (Exception e) {
			log.info("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public Supplier findSupplierByEmail(String email) {
		try {
			log.info("INIT findSupplierByEmail()");
			Optional<SupplierEntity> supplierOptional = supplierRepository.findByEmail(email);
			if (supplierOptional.isPresent())
				return supplierConverter.supplierEntityToSupplier(supplierOptional.get());
			return null;
		} catch (Exception e) {
			log.info("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public Supplier findSupplierByEmailAndIdNot(String email, Long supplierId) {
		try {
			log.info("INIT findSupplierByEmailAndIdNot()");
			Optional<SupplierEntity> supplierOptional = supplierRepository.findByEmailAndIdNot(email, supplierId);
			if (supplierOptional.isPresent())
				return supplierConverter.supplierEntityToSupplier(supplierOptional.get());
			return null;
		} catch (Exception e) {
			log.info("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

}
