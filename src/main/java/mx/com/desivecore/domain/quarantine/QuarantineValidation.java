package mx.com.desivecore.domain.quarantine;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.constants.QuarantineMovementEnum;
import mx.com.desivecore.domain.quarantine.models.ProductQuarantineAction;

@Log
public class QuarantineValidation {

	public String validOperativeData(ProductQuarantineAction productQuarantineAction) {
		log.info("INIT validOperativeData()");
		StringBuilder validations = new StringBuilder();

		if (productQuarantineAction.getBranch() == null) {
			validations.append("La sucursal es requerida. ");
		}
		if (productQuarantineAction.getProduct() == null) {
			validations.append("El producto es requerido. ");
		}
		if (productQuarantineAction.getAction() == null) {
			validations.append("El tipo de movimiento es requerido. ");
		}
		if (productQuarantineAction.getAmount() == null || productQuarantineAction.getAmount() <= 0) {
			validations.append("Debe ingresar una cantidad mayor a cero. ");
		}
		if (productQuarantineAction.getAction() != null
				&& !validActionMovement(productQuarantineAction.getAction().getActionCode())) {
			validations.append("Tipo de movimiento inválido. ");
		}

		return validations.toString();
	}

	private Boolean validActionMovement(String actionCode) {
		if (actionCode == null) {
			return false;
		}
		try {
			QuarantineMovementEnum.valueOf(actionCode.toUpperCase());
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

}
