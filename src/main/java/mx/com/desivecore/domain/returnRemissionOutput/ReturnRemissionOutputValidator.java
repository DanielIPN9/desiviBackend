package mx.com.desivecore.domain.returnRemissionOutput;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.remissionOutput.models.ProductOutput;
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutput;
import mx.com.desivecore.domain.returnRemissionOutput.models.ReturnProductOutput;
import mx.com.desivecore.domain.returnRemissionOutput.ports.ReturnROPersistencePort;

@Log
public class ReturnRemissionOutputValidator {

	public String validOperativeDataToGenerate(RemissionOutput remissionOutput, RemissionOutput remissionOutputSaved,
			ReturnROPersistencePort returnROPersistencePort) {
		log.info("INIT validOperativeDataToGenerate()");

		String validations = "";

		validations += remissionOutput.getProducts() == null ? "-Debe ingresar un producto para continuar" : "";
		validations += remissionOutput.getProducts() != null
				? remissionOutput.getProducts().isEmpty() ? "-Debe ingresar un producto para continuar" : ""
				: "";

		if (!validations.isEmpty())
			return validations;

		for (ProductOutput productOutput : remissionOutput.getProducts()) {
			validations += productOutput.getAmount() < 0
					? "-No puede ingresar valores negativos para devolución de productos: "
							+ productOutput.getProduct().getProductName()
					: "";
		}

		if (!validations.isEmpty())
			return validations;

		List<ReturnProductOutput> returnProductOutputLits = returnROPersistencePort
				.searchReturnProductByFolioRO(remissionOutput.getFolio());
		Map<Long, Double> currentAmountMap = generateAmountSummary(remissionOutputSaved, returnProductOutputLits);

		for (ProductOutput productOutput : remissionOutput.getProducts()) {
			Double currentAmount = currentAmountMap.get(productOutput.getProduct().getProductId());
			validations += currentAmount < productOutput.getAmount()
					? "-La cantidad para devolución supera los datos registrados de la orden para el producto: "
							+ productOutput.getProduct().getProductName()
					: "";
		}
		return validations;
	}

	private Map<Long, Double> generateAmountSummary(RemissionOutput remissionOutputSaved,
			List<ReturnProductOutput> returnProductOutputLits) {

		Map<Long, Double> currentAmountMap = new HashMap<>();
		if (returnProductOutputLits != null) {
			Map<Long, Double> returnedAmountMap = new HashMap<>();
			for (ReturnProductOutput returnProductOutput : returnProductOutputLits) {
				Long productId = returnProductOutput.getProduct().getProductId();
				Double returnedAmount = returnProductOutput.getAmountReturn();
				returnedAmountMap.merge(productId, returnedAmount, Double::sum);
			}

			for (ProductOutput productOutput : remissionOutputSaved.getProducts()) {
				Long productId = productOutput.getProduct().getProductId();
				if (returnedAmountMap.containsKey(productId)) {
					Double remainingAmount = productOutput.getAmount() - returnedAmountMap.get(productId);
					productOutput.setAmount(remainingAmount);
				}
			}

			for (ProductOutput productOutput : remissionOutputSaved.getProducts()) {
				Long productId = productOutput.getProduct().getProductId();
				Double returnedAmount = productOutput.getAmount();
				currentAmountMap.merge(productId, returnedAmount, Double::sum);
			}

		} else {
			for (ProductOutput productOutput : remissionOutputSaved.getProducts()) {
				Long productId = productOutput.getProduct().getProductId();
				Double returnedAmount = productOutput.getAmount();
				currentAmountMap.merge(productId, returnedAmount, Double::sum);
			}
		}

		return currentAmountMap;
	}

}
