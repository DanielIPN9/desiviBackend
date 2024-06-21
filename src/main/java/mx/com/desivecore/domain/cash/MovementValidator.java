package mx.com.desivecore.domain.cash;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.constants.AccountinTypeEnum;
import mx.com.desivecore.commons.constants.OpeningCashStatusEnum;
import mx.com.desivecore.domain.cash.models.EntryMovementRecord;
import mx.com.desivecore.domain.cash.models.ExitMovementRecord;
import mx.com.desivecore.domain.cash.models.OpeningCashSummary;

@Log
public class MovementValidator {

	public String validOperativeDataToEntryMovement(EntryMovementRecord entryMovementRecord,
			OpeningCashSummary openingCashSummary) {

		log.info("INIT validOperativeDataToEntryMovement()");

		StringBuilder validations = new StringBuilder();

		if (openingCashSummary == null) {
			validations.append("No cuenta con una apertura de caja. ");
			return validations.toString();
		}

		String openingState = OpeningCashSummary.evaluateStatusByCreationDate(openingCashSummary.getCreationDate());
		if (openingState.equals(OpeningCashStatusEnum.EXPIRED.toString())) {
			validations.append("Tiene una apertura de caja vencida. Requiere del proceso de cierre para continuar. ");
			return validations.toString();
		}

		if (entryMovementRecord.getAccountingType() == null) {
			validations.append("El tipo de ingreso es requerido. ");
			return validations.toString();
		}

		if (entryMovementRecord.getAccountingType().getCode() == null
				&& !validAccountingType(entryMovementRecord.getAccountingType().getCode())) {
			validations.append("El tipo de ingreso es invalido. ");
		}

		if (entryMovementRecord.getDescription() == null || entryMovementRecord.getDescription().isEmpty()) {
			validations.append("La descripción de entrada es requerida. ");
		}

		if (entryMovementRecord.getCurrency() == null || entryMovementRecord.getCurrency().isEmpty()) {
			validations.append("El tipo de moneda es requerido. ");
		}

		if (entryMovementRecord.getAmount() == null || entryMovementRecord.getAmount() <= 0) {
			validations.append("El monto debe ser mayor a cero y no negativo. ");
		}

		return validations.toString();
	}

	public String validOperativeDataToExitMovement(ExitMovementRecord exitMovementRecord,
			OpeningCashSummary openingCashSummary) {

		log.info("validOperativeDataToExitMovement()");

		StringBuilder validations = new StringBuilder();

		if (openingCashSummary == null) {
			validations.append("No cuenta con una apertura de caja. ");
			return validations.toString();
		}

		String openingState = OpeningCashSummary.evaluateStatusByCreationDate(openingCashSummary.getCreationDate());
		if (openingState.equals(OpeningCashStatusEnum.EXPIRED.toString())) {
			validations.append("Tiene una apertura de caja vencida. Requiere del proceso de cierre para continuar. ");
			return validations.toString();
		}

		if (exitMovementRecord.getAccountingType() == null) {
			validations.append("El tipo de ingreso es requerido. ");
			return validations.toString();
		}

		if (exitMovementRecord.getAccountingType().getCode() == null
				&& !validAccountingType(exitMovementRecord.getAccountingType().getCode())) {
			validations.append("El tipo de ingreso es invalido. ");
		}

		if (exitMovementRecord.getDescription() == null || exitMovementRecord.getDescription().isEmpty()) {
			validations.append("La descripción de entrada es requerida. ");
		}

		if (exitMovementRecord.getAuthorityUser() == null || exitMovementRecord.getAuthorityUser().isEmpty()) {
			validations.append("El usuario que aprueba la salida es requerido. ");
		}

		if (exitMovementRecord.getCurrency() == null || exitMovementRecord.getCurrency().isEmpty()) {
			validations.append("El tipo de moneda es requerido. ");
		}

		if (exitMovementRecord.getAmount() == null || exitMovementRecord.getAmount() <= 0) {
			validations.append("El monto debe ser mayor a cero y no negativo. ");
		}

		return validations.toString();
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
