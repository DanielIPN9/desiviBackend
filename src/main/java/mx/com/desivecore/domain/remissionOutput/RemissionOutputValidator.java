package mx.com.desivecore.domain.remissionOutput;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.remissionOutput.models.ProductOutput;
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutput;

@Log
public class RemissionOutputValidator {

	public String validOperativeDataToCreate(RemissionOutput remissionOutput) {
		log.info("INIT validOperativeDataToCreate()");
		String validations = "";

		if (remissionOutput == null)
			return "Datos invalidos";

		validations = validrequiredFields(remissionOutput, validations);

		if (validations.isEmpty()) {
			for (ProductOutput productOutput : remissionOutput.getProducts()) {
				validations = validAmount(validations, productOutput);
				validations = validUnitMeasure(validations, productOutput);
				validations = validPurchaeUnitPrice(validations, productOutput);
			}
		}

		return validations;
	}

	private String validPurchaeUnitPrice(String validations, ProductOutput productOutput) {
		validations += productOutput.getSellingPrice() == null
				? "El precio unitario de venta no puede ser negativo para el producto: "
						+ productOutput.getProduct().getProductName()
				: "";

		validations += productOutput.getSellingPrice() != null
				? productOutput.getSellingPrice() < 0.0
						? "El precio unitario de venta no puede ser negativo para el producto: "
								+ productOutput.getProduct().getProductName()
						: ""
				: "";
		return validations;
	}

	private String validUnitMeasure(String validations, ProductOutput productOutput) {
		validations += productOutput.getUnitMeasure() == null
				? "Ingrese una U.M. para el producto: " + productOutput.getProduct().getProductName()
				: "";

		validations += productOutput.getUnitMeasure() != null ? productOutput.getUnitMeasure().isEmpty()
				? "Ingrese una U.M. para el producto: " + productOutput.getProduct().getProductName()
				: "" : "";
		return validations;
	}

	private String validAmount(String validations, ProductOutput productOutput) {
		validations += productOutput.getAmount() == null
				? "-Debe ingresar un valor positivo para la cantidad a ingresar del producto: "
						+ productOutput.getProduct().getProductName()
				: "";
		validations += productOutput.getAmount() != null
				? productOutput.getAmount() < 0.0
						? "-Debe ingresar un valor positivo para la cantidad a ingresar del producto: "
								+ productOutput.getProduct().getProductName()
						: ""
				: "";
		return validations;
	}

	private String validrequiredFields(RemissionOutput remissionOutput, String validations) {
		validations += remissionOutput.getBranch() == null ? "-La sucursal es requerida" : "";
		validations += remissionOutput.getClient() == null ? "-El cliente es requerido" : "";
		validations += remissionOutput.getRequestDay() == null ? "-La fecha de entrega es requerida" : "";
		validations += remissionOutput.getProducts() == null ? "-Debe ingresar un producto como mínimo" : "";
		validations += remissionOutput.getProducts() != null
				? remissionOutput.getProducts().size() == 0 ? "-Debe ingresar un producto como mínimo" : ""
				: "";
		return validations;
	}
}
