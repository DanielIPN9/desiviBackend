package mx.com.desivecore.infraestructure.reports;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.reports.models.AccountPayableSummary;
import mx.com.desivecore.domain.reports.models.AccountReceivableSummary;
import mx.com.desivecore.domain.reports.models.EntryMovementRecordSummary;
import mx.com.desivecore.domain.reports.models.ExitMovementRecordSummary;
import mx.com.desivecore.domain.reports.models.ProductDetail;
import mx.com.desivecore.domain.reports.models.RemissionEntryDetail;
import mx.com.desivecore.domain.reports.models.RemissionOutputDetail;
import mx.com.desivecore.domain.reports.models.document.AccountinReportDocument;
import mx.com.desivecore.domain.reports.models.document.InventoryReportDocument;
import mx.com.desivecore.domain.reports.models.document.RemissionEntryReportDocument;
import mx.com.desivecore.domain.reports.models.document.RemissionOutputReportDocument;
import mx.com.desivecore.domain.reports.models.search.AccountingReportParams;
import mx.com.desivecore.domain.reports.models.search.RemissionEntryParamsReport;
import mx.com.desivecore.domain.reports.models.search.RemissionOutputParamsReport;
import mx.com.desivecore.domain.reports.ports.ReportPersistencePort;
import mx.com.desivecore.infraestructure.configuration.exceptions.ValidationError;
import mx.com.desivecore.infraestructure.reports.repositories.AccountingDSLReportRepository;
import mx.com.desivecore.infraestructure.reports.repositories.InventoryDSLReportRepository;
import mx.com.desivecore.infraestructure.reports.repositories.RemissionEntryDSLReportRepository;
import mx.com.desivecore.infraestructure.reports.repositories.RemissionOutputDSLReportRepository;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Log
@Service
public class ReportPersistenceImpl implements ReportPersistencePort {

	private static final String XLS_FORMAT = "XSL";
	private static final String PDF_FORMAT = "PDF";
	private static final String ACCOUNTING_INVENTORY_TEMPLATE = "/reports/inventory/AccountingInventoryReportDocument.jasper";
	private static final String INVENTORY_TEMPLATE = "/reports/inventory/InventoryReportDocument.jasper";
	private static final String REMISSION_ENTRY_TEMPLATE = "/reports/remissionEntry/RemissionEntryReportDocument.jasper";
	private static final String REMISSION_OUTPUT_TEMPLATE = "/reports/remissionOutput/RemissionOutputReportDocument.jasper";
	private static final String ACCOUNTING_GLOBAL_BALANCE_TEMPLATE = "/reports/accountingReport/AccountingGlobalReportDocument.jasper";
	private static final String LOGO_REPORT = "/reports/logo.jpg";

	@Autowired
	private InventoryDSLReportRepository inventoryDSLReportRepository;

	@Autowired
	private RemissionEntryDSLReportRepository remissionEntryDSLReportRepository;

	@Autowired
	private RemissionOutputDSLReportRepository remissionOutputDSLReportRepository;

	@Autowired
	private AccountingDSLReportRepository accountingDSLReportRepository;

	@Override
	public List<RemissionOutputDetail> searchRemissionOutputDataByParams(
			RemissionOutputParamsReport remissionOutputParamsReport) {
		try {
			log.info("INT searchRemissionOutputDataByParams()");
			return remissionOutputDSLReportRepository.searchRemissionOutputSummaryByParams(remissionOutputParamsReport);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<RemissionEntryDetail> searchRemissionEntryDataByParams(
			RemissionEntryParamsReport remissionEntryParamsReport) {
		try {
			log.info("INT searchRemissionEntryDataByParams()");
			return remissionEntryDSLReportRepository.searchRemissionEntrySummartByParams(remissionEntryParamsReport);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<ProductDetail> searchInventoryDataByParams(Long branchId) {
		try {
			log.info("INT searchInventoryDataByParams()");
			return inventoryDSLReportRepository.searchProductAvailabilityByBranchId(branchId);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<EntryMovementRecordSummary> searchEntryCashMovementByParams(
			AccountingReportParams accountingReportParams) {
		try {
			log.info("INIT searchEntryCashMovementByParams()");
			return accountingDSLReportRepository.searchEntryCashMovementByParams(accountingReportParams);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<ExitMovementRecordSummary> searchExitCashMovementByParams(
			AccountingReportParams accountingReportParams) {
		try {
			log.info("INIT searchExitCashMovementByParams()");
			return accountingDSLReportRepository.searchExitCashMovementByParams(accountingReportParams);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<AccountPayableSummary> searchAccountPayableByParams(AccountingReportParams accountingReportParams) {
		try {
			log.info("INIT searchAccountPayableByParams()");
			return accountingDSLReportRepository.searchAccountPayableByParams(accountingReportParams);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<AccountReceivableSummary> searchAccountReceivableByParams(
			AccountingReportParams accountingReportParams) {
		try {
			log.info("INIT searchAccountReceivableByParams()");
			return accountingDSLReportRepository.searchAccountReceivableByParams(accountingReportParams);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public ResponseModel generateAccountingInventoryReport(InventoryReportDocument inventoryReportDocument,
			String format) {
		try {
			log.info("INIT generateAccountingInventoryReport()");

			log.info(String.format("LOAD REPORT "));
			InputStream file = this.getClass().getResourceAsStream(ACCOUNTING_INVENTORY_TEMPLATE);

			InputStream logoImage = this.getClass().getResourceAsStream(LOGO_REPORT);
			inventoryReportDocument.setLogo(logoImage);

			Collection<InventoryReportDocument> collection = Collections.singletonList(inventoryReportDocument);

			JasperPrint jasperPrint = JasperFillManager.fillReport(file, null,
					new JRBeanCollectionDataSource(collection));

			byte[] remissionEntryReport = null;

			if (format.equalsIgnoreCase(PDF_FORMAT)) {
				remissionEntryReport = JasperExportManager.exportReportToPdf(jasperPrint);
			} else if (format.equalsIgnoreCase(XLS_FORMAT)) {
				ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
				SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArray);
				JRXlsExporter exporter = new JRXlsExporter();
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				exporter.setExporterOutput(output);
				exporter.exportReport();
				output.close();
				remissionEntryReport = byteArray.toByteArray();

			} else {
				throw new ValidationError("Formato inválido");
			}

			log.info(String.format("RETURN DATA "));
			return new ResponseModel(remissionEntryReport);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public ResponseModel generateInventoryReport(InventoryReportDocument inventoryReportDocument, String format) {
		try {
			log.info("INIT generateInventoryReport()");

			log.info(String.format("LOAD REPORT "));
			InputStream file = this.getClass().getResourceAsStream(INVENTORY_TEMPLATE);

			InputStream logoImage = this.getClass().getResourceAsStream(LOGO_REPORT);
			inventoryReportDocument.setLogo(logoImage);

			Collection<InventoryReportDocument> collection = Collections.singletonList(inventoryReportDocument);

			JasperPrint jasperPrint = JasperFillManager.fillReport(file, null,
					new JRBeanCollectionDataSource(collection));

			byte[] remissionEntryReport = null;

			if (format.equalsIgnoreCase(PDF_FORMAT)) {
				remissionEntryReport = JasperExportManager.exportReportToPdf(jasperPrint);
			} else if (format.equalsIgnoreCase(XLS_FORMAT)) {
				ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
				SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArray);
				JRXlsExporter exporter = new JRXlsExporter();
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				exporter.setExporterOutput(output);
				exporter.exportReport();
				output.close();
				remissionEntryReport = byteArray.toByteArray();

			} else {
				throw new ValidationError("Formato inválido");
			}

			log.info(String.format("RETURN DATA "));
			return new ResponseModel(remissionEntryReport);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public ResponseModel generateRemissionEntryReport(RemissionEntryReportDocument remissionEntryReportDocument,
			String format) {
		try {
			log.info("INIT generateRemissionEntryReport()");

			log.info(String.format("LOAD REPORT "));
			InputStream file = this.getClass().getResourceAsStream(REMISSION_ENTRY_TEMPLATE);

			InputStream logoImage = this.getClass().getResourceAsStream(LOGO_REPORT);
			remissionEntryReportDocument.setLogo(logoImage);

			Collection<RemissionEntryReportDocument> collection = Collections
					.singletonList(remissionEntryReportDocument);

			JasperPrint jasperPrint = JasperFillManager.fillReport(file, null,
					new JRBeanCollectionDataSource(collection));

			byte[] remissionEntryReport = null;

			if (format.equalsIgnoreCase(PDF_FORMAT)) {
				remissionEntryReport = JasperExportManager.exportReportToPdf(jasperPrint);
			} else if (format.equalsIgnoreCase(XLS_FORMAT)) {
				ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
				SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArray);
				JRXlsExporter exporter = new JRXlsExporter();
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				exporter.setExporterOutput(output);
				exporter.exportReport();
				output.close();
				remissionEntryReport = byteArray.toByteArray();

			} else {
				throw new ValidationError("Formato inválido");
			}

			log.info(String.format("RETURN DATA "));
			return new ResponseModel(remissionEntryReport);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public ResponseModel generateRemissionOutputReport(RemissionOutputReportDocument remissionOutputReportDocument,
			String format) {
		try {
			log.info("INIT generateRemissionOutputReport()");

			log.info(String.format("LOAD REPORT "));
			InputStream file = this.getClass().getResourceAsStream(REMISSION_OUTPUT_TEMPLATE);

			InputStream logoImage = this.getClass().getResourceAsStream(LOGO_REPORT);
			remissionOutputReportDocument.setLogo(logoImage);

			Collection<RemissionOutputReportDocument> collection = Collections
					.singletonList(remissionOutputReportDocument);

			JasperPrint jasperPrint = JasperFillManager.fillReport(file, null,
					new JRBeanCollectionDataSource(collection));

			byte[] remissionEntryReport = null;

			if (format.equalsIgnoreCase(PDF_FORMAT)) {
				remissionEntryReport = JasperExportManager.exportReportToPdf(jasperPrint);
			} else if (format.equalsIgnoreCase(XLS_FORMAT)) {
				ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
				SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArray);
				JRXlsExporter exporter = new JRXlsExporter();
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				exporter.setExporterOutput(output);
				exporter.exportReport();
				output.close();
				remissionEntryReport = byteArray.toByteArray();

			} else {
				throw new ValidationError("Formato inválido");
			}
			log.info(String.format("RETURN DATA "));
			return new ResponseModel(remissionEntryReport);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public ResponseModel generateAccountingReport(AccountinReportDocument accountinReportDocument, String format) {
		try {
			log.info("INIT generateRemissionOutputReport()");

			log.info(String.format("LOAD REPORT "));
			InputStream file = this.getClass().getResourceAsStream(ACCOUNTING_GLOBAL_BALANCE_TEMPLATE);

			InputStream logoImage = this.getClass().getResourceAsStream(LOGO_REPORT);
			accountinReportDocument.setLogo(logoImage);

			Collection<AccountinReportDocument> collection = Collections.singletonList(accountinReportDocument);

			JasperPrint jasperPrint = JasperFillManager.fillReport(file, null,
					new JRBeanCollectionDataSource(collection));

			byte[] remissionEntryReport = null;

			if (format.equalsIgnoreCase(PDF_FORMAT)) {
				remissionEntryReport = JasperExportManager.exportReportToPdf(jasperPrint);
			} else if (format.equalsIgnoreCase(XLS_FORMAT)) {
				ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
				SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArray);
				JRXlsExporter exporter = new JRXlsExporter();
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				exporter.setExporterOutput(output);
				exporter.exportReport();
				output.close();
				remissionEntryReport = byteArray.toByteArray();

			} else {
				throw new ValidationError("Formato inválido");
			}
			log.info(String.format("RETURN DATA "));
			return new ResponseModel(remissionEntryReport);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

}
