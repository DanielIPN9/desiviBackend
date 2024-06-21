package mx.com.desivecore.domain.cash;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.constants.AccountinTypeEnum;
import mx.com.desivecore.domain.cash.models.ClosingCash;
import mx.com.desivecore.domain.cash.models.OpeningCash;
import mx.com.desivecore.domain.cash.models.OpeningCashSummary;

@Log
public class CashValidator {

	public String validOperativeData(OpeningCash openingCash, OpeningCashSummary openingCashSummary) {
		log.info("INIT validOperativeData()");
		StringBuilder validation = new StringBuilder();

		if (openingCashSummary != null) {
			if (openingCashSummary.getOpeningCashId() != null) {
				validation.append("Ya cuenta con una apertura de caja.");
				return validation.toString();
			}
		}

		if (openingCash.getCurrency() == null || openingCash.getCurrency().isEmpty())
			validation.append("Debe ingresar el tipo de moneda. ");

		if (openingCash.getAmount() == null || openingCash.getAmount() < 0)
			validation.append("La cantidad no puede ser negativa. ");

		return validation.toString();
	}

	public String validClosingOperativeData(ClosingCash closingCash, OpeningCashSummary openingCashSummary) {
		log.info("INIT validClosingOperativeData()");
		StringBuilder validation = new StringBuilder();

		if (openingCashSummary == null) {
			validation.append("No cuenta con una caja activa.");
			return validation.toString();
		}

		if (closingCash.getClosingDetail() == null || closingCash.getClosingDetail().isEmpty()) {
			validation.append("Debe ingresar un concepto para continuar. ");
			return validation.toString();
		}

		closingCash.getClosingDetail().stream().forEach(closingDetail -> {

			int line = 1;

			if (closingDetail.getAccountingType() == null) {
				validation.append("El tipo de ingreso es requerido. ");
			}

			if (closingDetail.getAccountingType() != null && closingDetail.getAccountingType().getCode() == null
					&& !validAccountingType(closingDetail.getAccountingType().getCode())) {
				validation.append("El tipo de ingreso es invalido. ");
			}

			if (closingDetail.getCurrency() == null || closingDetail.getCurrency().isEmpty()) {
				validation.append("El tipo de moneda es requerido. ");
			}

			if (closingDetail.getAmount() == null || closingDetail.getAmount() < 0) {
				validation.append("Debe ingresar un valo no negativo o cero. ");
			}

			if (validation.length() > 0)
				validation.append("Para la línea ").append(line).append(" ");

			line++;
		});

		return validation.toString();
	}

	private Boolean validAccountingType(String accountingCode) {
		try {
			AccountinTypeEnum.valueOf(accountingCode);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
