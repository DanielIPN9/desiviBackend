package mx.com.desivecore.domain.quote;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.products.models.ProductAvailability;
import mx.com.desivecore.domain.products.ports.ProductPersistencePort;
import mx.com.desivecore.domain.quote.models.QuoteOrder;
import mx.com.desivecore.domain.quote.models.QuoteProduct;

@Log
public class QuoteOrderValidator {

	public String validOperativeData(QuoteOrder quoteOrder) {
		log.info("INIT validOperativeData()");
		String validations = "";
		validations = validRequiredField(quoteOrder, validations);
		if (validations.isEmpty())
			validations = validProductList(quoteOrder.getProducts(), validations);
		return validations;
	}

	private String validProductList(List<QuoteProduct> products, String validations) {
		String productListValidation = "";
		int line = 1;
		for (QuoteProduct quoteProduct : products) {

			productListValidation += quoteProduct.getProduct() == null ? " -Producto invalido para la línea " + line
					: "";
			productListValidation += quoteProduct.getAmount() == null
					? " -Debe ingresar una cantidad para la línea " + line
					: "";
			productListValidation += quoteProduct.getAmount() != null
					? quoteProduct.getAmount() <= 0 ? " -Debe ingresar una cantidad mayor a 0 para la línea " + line
							: ""
					: "";
		}
		return validations += productListValidation;
	}

	private String validRequiredField(QuoteOrder quoteOrder, String validations) {

		validations += quoteOrder.getEffectiveDate() == null ? " -La fecha de vigencia es requerida" : "";
		validations += quoteOrder.getBranch() == null ? " -La sucursal es requerida" : "";
		validations += quoteOrder.getClient() == null ? " -El cliente es requerido" : "";
		validations += quoteOrder.getProducts() == null ? " -Debe ingresar un producto como mínimo" : "";
		validations += quoteOrder.getProducts() != null
				? quoteOrder.getProducts().isEmpty() ? " -Debe ingresar un producto como mínimo" : ""
				: "";

		return validations;

	}

	public String validOperativeDataToConvert(QuoteOrder quoteOrder, ProductPersistencePort productPersistencePort) {
		log.info("INIT validOperativeDataToConvert()");
		String validations = "";

		validations += quoteOrder.getIsConverter() ? " -La Cotización ha sido convertida a remisión de salida" : "";
		validations = validEffectiveDate(quoteOrder, validations);

		validations = validAvailability(quoteOrder, productPersistencePort, validations);

		return validations;
	}

	private String validAvailability(QuoteOrder quoteOrder, ProductPersistencePort productPersistencePort,
			String validations) {
		for (QuoteProduct quoteProduct : quoteOrder.getProducts()) {
			ProductAvailability productAvailability = productPersistencePort.findByProducIdAndBranchId(
					quoteProduct.getProduct().getProductId(), quoteOrder.getBranch().getBranchId());
			validations += productAvailability == null
					? "-Cantidad insuficiente para el producto" + quoteProduct.getProduct().getName()
							+ " en la sucursal " + quoteOrder.getBranch().getName()
					: "";
			if (productAvailability != null) {
				validations += productAvailability.getAmount() >= quoteProduct.getAmount() ? ""
						: " -Cantidad insuficiente para el producto " + quoteProduct.getProduct().getName()
								+ ". Disponible: " + productAvailability.getAmount();
			}

		}
		return validations;
	}

	private String validEffectiveDate(QuoteOrder quoteOrder, String validations) {
		Calendar calCurrentDate = Calendar.getInstance();
		calCurrentDate.setTime(new Date());
		LocalDate ldCurrentDay = LocalDate.of(calCurrentDate.get(Calendar.YEAR), calCurrentDate.get(Calendar.MONTH) + 1,
				calCurrentDate.get(Calendar.DAY_OF_MONTH));
		log.info("FECHA ldCurrentDay " + ldCurrentDay.toString());
		Calendar caleffectiveDay = Calendar.getInstance();
		caleffectiveDay.setTime(quoteOrder.getEffectiveDate());
		LocalDate ldEffectiveDate = LocalDate.of(caleffectiveDay.get(Calendar.YEAR),
				caleffectiveDay.get(Calendar.MONTH) + 1, caleffectiveDay.get(Calendar.DAY_OF_MONTH));
		log.info("FECHA ldEffectiveDate " + ldEffectiveDate.toString());
		validations += ldCurrentDay.isBefore(ldEffectiveDate) ? "" : "-Fecha de vigencia invalida ";
		return validations;
	}

}
