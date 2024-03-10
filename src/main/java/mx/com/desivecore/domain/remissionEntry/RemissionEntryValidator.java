package mx.com.desivecore.domain.remissionEntry;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.remissionEntry.models.ProductEntry;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntry;

@Log
public class RemissionEntryValidator {

	public String validOperativeDataToCreate(RemissionEntry remissionEntry) {
		log.info("INIT validOperativeDataToCreate()");
		String validations = "";

		if (remissionEntry == null)
			return "Datos invalidos";

		validations = validRequiredFields(remissionEntry, validations);

		if (validations.isEmpty()) {
			for (ProductEntry productEntry : remissionEntry.getProducts()) {
				validations = validAmount(validations, productEntry);
				validations = validUnitMeasure(validations, productEntry);
				validations = validPurchaeUnitPrice(validations, productEntry);
			}
		}

		return validations;
	}
	
	private String validPurchaeUnitPrice(String validations, ProductEntry productEntry) {
		validations += productEntry.getPurchaseUnitPrice() == null
				? "El precio unitario de compra no puede ser negativo para el producto: "
						+ productEntry.getProduct().getName()
				: "";

		validations += productEntry.getPurchaseUnitPrice() != null
				? productEntry.getPurchaseUnitPrice() < 0.0
						? "El precio unitario de compra no puede ser negativo para el producto: "
								+ productEntry.getProduct().getName()
						: ""
				: "";
		return validations;
	}

	private String validUnitMeasure(String validations, ProductEntry productEntry) {
		validations += productEntry.getUnitMeasure() == null
				? "Ingrese una U.M. para el producto: " + productEntry.getProduct().getName()
				: "";

		validations += productEntry.getUnitMeasure() != null ? productEntry.getUnitMeasure().isEmpty()
				? "Ingrese una U.M. para el producto: " + productEntry.getProduct().getName()
				: "" : "";
		return validations;
	}

	private String validAmount(String validations, ProductEntry productEntry) {
		validations += productEntry.getAmount() == null
				? "-Debe ingresar un valor positivo para la cantidad a ingresar del producto: "
						+ productEntry.getProduct().getName()
				: "";
		validations += productEntry.getAmount() != null
				? productEntry.getAmount() < 0.0
						? "-Debe ingresar un valor positivo para la cantidad a ingresar del producto: "
								+ productEntry.getProduct().getName()
						: ""
				: "";
		return validations;
	}

	private String validRequiredFields(RemissionEntry remissionEntry, String validations) {
		validations += remissionEntry.getBranch() == null ? "-La sucursal es requerida" : "";
		validations += remissionEntry.getSupplier() == null ? "-El proveedor es requerido" : "";
		validations += remissionEntry.getRequestDate() == null ? "-La fecha de entrega es requerida" : "";
		validations += remissionEntry.getProducts() == null ? "-Debe ingresar un producto como mínimo" : "";
		validations += remissionEntry.getProducts() != null
				? remissionEntry.getProducts().size() == 0 ? "-Debe ingresar un producto como mínimo" : ""
				: "";
		return validations;
	}

}
