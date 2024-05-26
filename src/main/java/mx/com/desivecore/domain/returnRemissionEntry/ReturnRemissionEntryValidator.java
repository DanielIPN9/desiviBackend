package mx.com.desivecore.domain.returnRemissionEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.remissionEntry.models.ProductEntry;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntry;
import mx.com.desivecore.domain.returnRemissionEntry.models.ReturnProductEntry;
import mx.com.desivecore.domain.returnRemissionEntry.ports.ReturnREPersistencePort;

@Log
public class ReturnRemissionEntryValidator {

	public String validOperativeDataToGenerate(RemissionEntry remissionEntry, RemissionEntry remissionEntrySaved,
			ReturnREPersistencePort returnREPersistencePort) {
		log.info("INIT validOperativeDataToGenerate()");

		String validations = "";

		validations += remissionEntry.getProducts() == null ? "-Debe ingresar un producto para continuar" : "";
		validations += remissionEntry.getProducts() != null
				? remissionEntry.getProducts().isEmpty() ? " -Debe ingresar un producto para continuar" : ""
				: "";

		if (!validations.isEmpty())
			return validations;

		for (ProductEntry productEntry : remissionEntry.getProducts()) {
			validations += productEntry.getAmount() < 0
					? "-No puede ingresar valores negativos para devolución del producto: "
							+ productEntry.getProduct().getName()
					: "";
		}

		if (!validations.isEmpty())
			return validations;

		List<ReturnProductEntry> returnProductEntryList = returnREPersistencePort
				.searchByFolioRE(remissionEntry.getFolio());
		Map<Long, Double> currentAmountMap = generateAmountSumary(remissionEntrySaved, returnProductEntryList);

		for (ProductEntry productEntry : remissionEntry.getProducts()) {
			Double currentAmount = currentAmountMap.get(productEntry.getProduct().getProductId());
			validations += currentAmount < productEntry.getAmount()
					? " -La cantidad para devolución supera los datos registrados de la orden para el producto: "
							+ productEntry.getProduct().getName()
					: "";
		}

		return validations;
	}

	private Map<Long, Double> generateAmountSumary(RemissionEntry remissionEntrySaved,
			List<ReturnProductEntry> returnProductEntryList) {
		Map<Long, Double> currentAmountMap = new HashMap<>();
		if (returnProductEntryList != null) {
			Map<Long, Double> returnedAmountMap = new HashMap<>();
			for (ReturnProductEntry returnProductEntry : returnProductEntryList) {
				Long productId = returnProductEntry.getProduct().getProductId();
				Double returnedAmount = returnProductEntry.getAmountReturn();
				returnedAmountMap.merge(productId, returnedAmount, Double::sum);
			}

			for (ProductEntry productEntry : remissionEntrySaved.getProducts()) {
				Long productId = productEntry.getProduct().getProductId();
				if (returnedAmountMap.containsKey(productId)) {
					Double remainingAmount = productEntry.getAmount() - returnedAmountMap.get(productId);
					productEntry.setAmount(remainingAmount);
				}
			}

			for (ProductEntry productEntry : remissionEntrySaved.getProducts()) {
				Long productId = productEntry.getProduct().getProductId();
				Double returnedAmount = productEntry.getAmount();
				currentAmountMap.merge(productId, returnedAmount, Double::sum);
			}

		} else {
			for (ProductEntry productEntry : remissionEntrySaved.getProducts()) {
				Long productId = productEntry.getProduct().getProductId();
				Double returnedAmount = productEntry.getAmount();
				currentAmountMap.merge(productId, returnedAmount, Double::sum);
			}
		}

		return currentAmountMap;
	}
}
