package mx.com.desivecore.domain.suppliers.ports;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.suppliers.models.Supplier;

public interface SupplierServicePort {

	ResponseModel createSupplier(Supplier supplier);

	ResponseModel viewAllSupplier();

	ResponseModel viewSupplierDetailById(Long supplierId);

	ResponseModel updateSupplierById(Supplier supplier);

	ResponseModel changeStatusById(Long supplierId, String status);

}
