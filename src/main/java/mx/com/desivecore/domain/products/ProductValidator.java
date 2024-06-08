package mx.com.desivecore.domain.products;

import java.util.List;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.domain.branches.ports.BranchPersistencePort;
import mx.com.desivecore.domain.products.models.Product;
import mx.com.desivecore.domain.products.models.ProductAvailability;
import mx.com.desivecore.domain.products.ports.ProductPersistencePort;

@Log
public class ProductValidator {

	public String validOperativeDataToCreate(Product product, ProductPersistencePort persistencePort) {
		log.info("INIT validOperativeDataToCreate()");
		String validations = "";

		if (product == null)
			return "-Datos inválidos";

		validations = validRequiredFields(product, validations);

		validations = validIVA(product, validations);

		validations = validUnitSellerPrice(product, validations);

		validations = validUnitPurchase(product, validations);

		Product productSearch = null;

		productSearch = persistencePort.findProductBySku(product.getSku());
		validations += productSearch != null ? "-ELl SKU ingresado ya existe " + product.getSku() : "";

		return validations;
	}

	public String validOperativeDataToUpdate(Product product, ProductPersistencePort persistencePort) {
		log.info("INIT validOperativeDataToUpdate()");
		String validations = "";

		if (product == null)
			return "-Datos inválidos";

		validations = validRequiredFields(product, validations);

		validations = validIVA(product, validations);

		validations = validUnitSellerPrice(product, validations);

		validations = validUnitPurchase(product, validations);

		Product productSearch = null;

		productSearch = persistencePort.findProductBySkuAndIdNot(product.getSku(), product.getProductId());
		validations += productSearch != null ? "-ELl SKU ingresado ya existe " + product.getSku() : "";

		return validations;
	}

	public String validAvailabilityToSave(List<ProductAvailability> productAvailabilityList,
			BranchPersistencePort branchPersistencePort, ProductPersistencePort productPersistencePort) {

		String validations = "";

		for (ProductAvailability productAvailability : productAvailabilityList) {

			validations = validBranch(branchPersistencePort, validations, productAvailability);
			if (validations.isEmpty())
				validations += productAvailability.getAmount() < 0.0
						? "- La cantidad ingresada para la sucursal" + productAvailability.getBranch().getName()
								+ "no puede ser menor a cero"
						: "";
		}

		return validations;
	}

	private String validBranch(BranchPersistencePort branchPersistencePort, String validations,
			ProductAvailability productAvailability) {
		validations += productAvailability.getBranch() == null ? "La sucursal es invalida" : "";
		Branch branchSearch = branchPersistencePort.findBranchById(productAvailability.getBranch().getBranchId());
		validations += branchSearch == null ? "La sucursal es invalida" : "";
		return validations;
	}

	private String validUnitPurchase(Product product, String validations) {
		validations += product.getUnitPurchasePrice() == null ? "-El Precio de Compra es Requerido" : "";
		validations += product.getUnitPurchasePrice() != null
				? product.getUnitPurchasePrice() < 0.0 ? "-El precio de Compra no puede ser negativo" : ""
				: "";
		return validations;
	}

	private String validUnitSellerPrice(Product product, String validations) {
		validations += product.getUnitSellingPrice() == null ? "-El Precio de Venta es requerido" : "";
		validations += product.getUnitSellingPrice() != null
				? product.getUnitSellingPrice() < 0.0 ? "-El Precio de Venta no puede ser menor a cero" : ""
				: "";
		return validations;
	}

	private String validIVA(Product product, String validations) {
		validations += product.getIva() == null ? "-El IVA es requerido" : "";
		validations += product.getIva() != null ? product.getIva() < 0.0 ? "-EL IVA no puede ser Negativo" : "" : "";
		return validations;
	}

	private String validRequiredFields(Product product, String validations) {
		validations += validString("Categoria", product.getCategory());
		validations += validString("SKU ", product.getSku());
		validations += validString("Nombre ", product.getName());
		validations += validString("UM ", product.getUnitMeasure());
		validations += product.getMinAvailability() == null || product.getMinAvailability() <= 0
				? "-Debe ingresar un valor para la cantidad mínima en almacenamiento"
				: "";
		return validations;
	}

	private String validString(String fieldName, String value) {
		String validations = "";
		validations = value == null ? "-El campo " + fieldName + " es requerido" : validations;
		validations = value != null ? value.isEmpty() ? "-El campo " + fieldName + " es requerido" : validations
				: validations;
		return validations;
	}

}
