package mx.com.desivecore.domain.reports;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.reports.models.search.InventoryParamsReport;
import mx.com.desivecore.domain.reports.models.search.RemissionEntryParamsReport;
import mx.com.desivecore.domain.reports.models.search.RemissionOutputParamsReport;

@Log
public class ReportValidator {

	public String validOperativeDataToInventoryReport(InventoryParamsReport inventoryParamsReport) {
		log.info("INIT validOperativeDataToInventoryReport()");
		String validations = "";
		if (inventoryParamsReport == null)
			return "-Debe ingresar una opción de sucursal";
		validations += inventoryParamsReport.getBarnch() == null ? "-Debe ingresar una opción de sucursal" : "";
		validations += inventoryParamsReport.getBarnch() != null
				? inventoryParamsReport.getBarnch().getBranchId() == null ? "-Debe ingresar una opción de sucursal" : ""
				: "";
		return validations;
	}

	public String validOperativeDataToRemissionEntryReport(RemissionEntryParamsReport remissionEntryParamsReport) {
		log.info("INIT validOperativeDataToRemissionEntryReport()");
		String validations = "";
		if (remissionEntryParamsReport == null)
			return "-Datos de búsqueda invalidos";

		validations += remissionEntryParamsReport.getDateFrom() == null ? " -La fecha de inicio es obligatoria" : "";
		validations += remissionEntryParamsReport.getDateTo() == null ? "- La fecha de fin es obligatoria" : "";

		return validations;
	}

	public String validOperativeDataToRemissionOutputReport(RemissionOutputParamsReport remissionOutputParamsReport) {
		log.info("INIT validOperativeDataToRemissionOutputReport()");
		String validations = "";
		if (remissionOutputParamsReport == null)
			return "-Datos de búsqueda invalidos";

		validations += remissionOutputParamsReport.getDateFrom() == null ? " -La fecha de inicio es obligatoria" : "";
		validations += remissionOutputParamsReport.getDateTo() == null ? "- La fecha de fin es obligatoria" : "";

		return validations;
	}
}
