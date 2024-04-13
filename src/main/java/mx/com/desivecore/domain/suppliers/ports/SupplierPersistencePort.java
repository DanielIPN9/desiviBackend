package mx.com.desivecore.domain.suppliers.ports;

import java.util.List;

import mx.com.desivecore.domain.suppliers.models.Supplier;

public interface SupplierPersistencePort {

	Supplier saveSupplier(Supplier supplier);

	List<Supplier> viewAllSupplier();
	
	List<Supplier> viewAllSupplierActive();

	Supplier viewSupplierDetailById(Long supplierId);

	boolean changeStatusById(Long supplierId, boolean status);

	Supplier findSupplierByRfc(String rfc);

	Supplier findSupplierByRfcAndIdNot(String rfc, Long supplierId);

	Supplier findSupplierByEmail(String email);

	Supplier findSupplierByEmailAndIdNot(String email, Long supplierId);

}
