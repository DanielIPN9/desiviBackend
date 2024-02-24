package mx.com.desivecore.domain.productTag;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.productTag.models.ProductTag;

@Log
public class TagValidator {

	public String validOperativeDataToGenerateTag(ProductTag productTag) {
		log.info("INIT validOperativeDataToGenerateTag()");

		if (productTag == null)
			return "Datos invalidos";

		String validations = "";
		if (productTag.getProduct() != null) {
			validations += validString(" -Producto", productTag.getProduct().getName());
		} else {
			validations += "Poducto es Requerido";
		}
		validations += validString("Lote", productTag.getLot());
		validations += validString("Dirección ", productTag.getFullAddress());
		validations += validString("URL Sitio", productTag.getUrlSite());
		validations += validString("Número contacto", productTag.getPhoneNumber());
		validations += productTag.getNetWeight() == null ? " -Peso Neto Requerido" : "";
		validations += productTag.getCreationDate() == null ? " -Fecha Ingreso Requerido" : "";

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
