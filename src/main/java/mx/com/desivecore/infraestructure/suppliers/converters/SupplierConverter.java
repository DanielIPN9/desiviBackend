package mx.com.desivecore.infraestructure.suppliers.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.suppliers.models.Supplier;
import mx.com.desivecore.infraestructure.suppliers.entities.SupplierEntity;

@Component
public class SupplierConverter {

	public SupplierEntity supplierToSupplierEntity(Supplier supplier) {

		SupplierEntity supplierEntity = new SupplierEntity();

		supplierEntity.setSupplierId(supplier.getSupplierId());
		supplierEntity.setBusinessName(supplier.getBusinessName());
		supplierEntity.setRfc(supplier.getRfc());
		supplierEntity.setContactName(supplier.getContactName());
		supplierEntity.setContactNumber(supplier.getContactNumber());
		supplierEntity.setEmail(supplier.getEmail());
		supplierEntity.setStatus(supplier.isStatus());
		supplierEntity.setStreet(supplier.getStreet());
		supplierEntity.setExternalNumber(supplier.getExternalNumber());
		supplierEntity.setInternalNumber(supplier.getInternalNumber());
		supplierEntity.setMunicipality(supplier.getMunicipality());
		supplierEntity.setColony(supplier.getColony());
		supplierEntity.setCp(supplier.getCp());

		return supplierEntity;
	}

	public Supplier supplierEntityToSupplier(SupplierEntity supplierEntity) {
		
		Supplier supplier = new Supplier();

		supplier.setSupplierId(supplierEntity.getSupplierId());
		supplier.setBusinessName(supplierEntity.getBusinessName());
		supplier.setRfc(supplierEntity.getRfc());
		supplier.setContactName(supplierEntity.getContactName());
		supplier.setContactNumber(supplierEntity.getContactNumber());
		supplier.setEmail(supplierEntity.getEmail());
		supplier.setStatus(supplierEntity.isStatus());
		supplier.setStreet(supplierEntity.getStreet());
		supplier.setExternalNumber(supplierEntity.getExternalNumber());
		supplier.setInternalNumber(supplierEntity.getInternalNumber());
		supplier.setMunicipality(supplierEntity.getMunicipality());
		supplier.setColony(supplierEntity.getColony());
		supplier.setCp(supplierEntity.getCp());

		return supplier;
	}

	public List<SupplierEntity> supplierListToSupplierEntityList(List<Supplier> supplierList) {
		List<SupplierEntity> supplierEntityList = new ArrayList<>();
		for (Supplier supplier : supplierList) {
			supplierEntityList.add(supplierToSupplierEntity(supplier));
		}
		return supplierEntityList;
	}

	public List<Supplier> supplierEntityListToSupplierList(List<SupplierEntity> supplierEntityList) {
		List<Supplier> supplierList = new ArrayList<>();
		for (SupplierEntity supplierEntity : supplierEntityList) {
			supplierList.add(supplierEntityToSupplier(supplierEntity));
		}
		return supplierList;
	}

}
