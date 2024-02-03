package mx.com.desivecore.domain.suppliers;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.suppliers.models.Supplier;
import mx.com.desivecore.domain.suppliers.ports.SupplierPersistencePort;

@Log
public class SupplierValidator {

	public String validOperativeDataToCreate(Supplier supplier, SupplierPersistencePort supplierPersistencePort) {
		log.info("INIT validOperativeDataToCreate()");
		String validations = "";

		validations = validRequiredFields(supplier, validations);

		Supplier supplierSearch = null;
		supplierSearch = supplierPersistencePort.findSupplierByEmail(supplier.getEmail());
		validations += supplierSearch != null ? "- El corre ingresado ya existe para un proveedor" : "";

		supplierSearch = supplierPersistencePort.findSupplierByRfc(supplier.getRfc());
		validations += supplierSearch != null ? " -El RFC ingresado ya existe para un proveedor" : "";

		return validations;
	}

	public String validOperativeDataToUpdate(Supplier supplier, SupplierPersistencePort supplierPersistencePort) {
		log.info("INIT validOperativeDataToUpdate()");
		String validations = "";

		validations = validRequiredFields(supplier, validations);

		Supplier supplierSearch = null;
		supplierSearch = supplierPersistencePort.findSupplierByEmailAndIdNot(supplier.getEmail(),
				supplier.getSupplierId());
		validations += supplierSearch != null ? "- El corre ingresado ya existe para un proveedor" : "";

		supplierSearch = supplierPersistencePort.findSupplierByRfcAndIdNot(supplier.getRfc(), supplier.getSupplierId());
		validations += supplierSearch != null ? " -El RFC ingresado ya existe para un proveedor" : "";

		return validations;
	}

	private String validRequiredFields(Supplier supplier, String validations) {
		validations += validString("Nombre/Razon Social", supplier.getBusinessName());
		validations += validString("RFC", supplier.getRfc());
		validations += validString("Nombre de contacto", supplier.getContactName());
		validations += validString("Número de contacto", supplier.getContactNumber());
		validations += validString("Email", supplier.getEmail());

		validations += validString("Caller", supplier.getStreet());
		validations += validString("Número Exterior", supplier.getExternalNumber());
		validations += validString("Municipio/Alcaldía", supplier.getMunicipality());
		validations += validString("Colonia", supplier.getColony());
		validations += validString("CP", supplier.getCp());
		return validations;
	}

	private String validString(String fieldName, String value) {
		String validations = "";
		validations = value == null ? "- El campo " + fieldName + " es requerido" : validations;
		validations = value != null ? value.isEmpty() ? "- El campo " + fieldName + " es requerido" : validations
				: validations;
		return validations;
	}
}
