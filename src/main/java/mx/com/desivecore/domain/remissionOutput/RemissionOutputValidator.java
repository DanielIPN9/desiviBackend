package mx.com.desivecore.domain.remissionOutput;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.products.models.ProductAvailability;
import mx.com.desivecore.domain.products.ports.ProductPersistencePort;
import mx.com.desivecore.domain.remissionOutput.models.ProductOutput;
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutput;

@Log
public class RemissionOutputValidator {

	public String validOperativeDataToCreate(RemissionOutput remissionOutput,
			ProductPersistencePort productPersistencePort) {
		log.info("INIT validOperativeDataToCreate()");
		String validations = "";

		if (remissionOutput == null)
			return "Datos invalidos";

		validations = validrequiredFields(remissionOutput, validations);

		if (remissionOutput.getProducts() != null) {
			for (ProductOutput productOutput : remissionOutput.getProducts()) {
				validations = validAmount(validations, productOutput);
				validations = validAvailability(validations, productOutput, remissionOutput, productPersistencePort);
				validations = validUnitMeasure(validations, productOutput);
				validations =validProductDescription(validations, productOutput);
				validations = validPurchaeUnitPrice(validations, productOutput);
			}
		}

		return validations;
	}

	public String validOperativeDataToUpdate(RemissionOutput remissionOutput, RemissionOutput remissionOutputSaved,
			ProductPersistencePort productPersistencePort) {

		log.info("INIT validOperativeDataToUpdate()");
		String validations = "";
		if (remissionOutput == null)
			return "Datos invalidos";
		validations = validrequiredFields(remissionOutput, validations);

		if (remissionOutput.getProducts() != null) {
			for (ProductOutput productOutput : remissionOutput.getProducts()) {
				validations = validAmount(validations, productOutput);
				validations = validAvailabilityToUpdate(validations, productOutput, remissionOutput,
						remissionOutputSaved, productPersistencePort);
				validations = validUnitMeasure(validations, productOutput);
				validations =validProductDescription(validations, productOutput);
				validations = validPurchaeUnitPrice(validations, productOutput);
			}
		}

		return validations;
	}

	private String validAvailabilityToUpdate(String validations, ProductOutput productOutput,
			RemissionOutput remissionOutput, RemissionOutput remissionOutputSaved,
			ProductPersistencePort productPersistencePort) {

		ProductAvailability productAvailability = productPersistencePort.findByProducIdAndBranchId(
				productOutput.getProduct().getProductId(), remissionOutput.getBranch().getBranchId());

		validations += productAvailability == null ? "Producto sin existencia disponible" : "";

		if (productAvailability != null) {
			Double amountAvailability = productAvailability.getAmount();
			Double amountSaved = 0.0;
			for (ProductOutput productOutpuSaved : remissionOutputSaved.getProducts()) {
				if (productOutput.getProduct().getProductId() == productOutpuSaved.getProduct().getProductId())
					amountSaved = productOutpuSaved.getAmount();
			}
			amountAvailability += amountSaved;

			log.info("PRODUCT AVAILAVILITY PLUS SAVED: " + amountAvailability);
			validations += productOutput.getAmount() > amountAvailability
					? "Cantidad no disponible. Existencia: " + productAvailability.getAmount()
					: "";
		}

		return validations;
	}

	private String validAvailability(String validations, ProductOutput productOutput, RemissionOutput remissionOutput,
			ProductPersistencePort productPersistencePort) {
		ProductAvailability productAvailability = productPersistencePort.findByProducIdAndBranchId(
				productOutput.getProduct().getProductId(), remissionOutput.getBranch().getBranchId());

		validations += productAvailability == null ? "Producto sin existencia disponible" : "";

		validations += productAvailability != null ? productOutput.getAmount() > productAvailability.getAmount()
				? "Cantidad no disponible. Existencia: " + productAvailability.getAmount()
				: "" : "";

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
	
	private String validProductDescription(String validations, ProductOutput productOutput) {
		validations += productOutput.getProductDescription() == null
				? "Ingrese una descripción para el producto: " + productOutput.getProduct().getProductName()
				: "";

		validations += productOutput.getProductDescription() != null ? productOutput.getProductDescription().isEmpty()
				? "Ingrese una descripción para el producto: " + productOutput.getProduct().getProductName()
				: "" : "";
		return validations;
	}

	private String validAmount(String validations, ProductOutput productOutput) {

		validations += productOutput.getAmount() == null
				? "-Debe ingresar un valor positivo para la cantidad a ingresar del producto: "
						+ productOutput.getProduct().getProductName()
				: "";

		validations += productOutput.getAmount() != null
				? productOutput.getAmount() <= 0.0
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
