package mx.com.desivecore.infraestructure.reports;

import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.reports.models.ProductDetail;
import mx.com.desivecore.domain.reports.models.RemissionEntryDetail;
import mx.com.desivecore.domain.reports.models.RemissionOutputDetail;
import mx.com.desivecore.domain.reports.models.document.InventoryReportDocument;
import mx.com.desivecore.domain.reports.models.document.RemissionEntryReportDocument;
import mx.com.desivecore.domain.reports.models.document.RemissionOutputReportDocument;
import mx.com.desivecore.domain.reports.models.search.RemissionEntryParamsReport;
import mx.com.desivecore.domain.reports.models.search.RemissionOutputParamsReport;
import mx.com.desivecore.domain.reports.ports.ReportPersistencePort;
import mx.com.desivecore.infraestructure.reports.repositories.InventoryDSLReportRepository;
import mx.com.desivecore.infraestructure.reports.repositories.RemissionEntryDSLReportRepository;
import mx.com.desivecore.infraestructure.reports.repositories.RemissionOutputDSLReportRepository;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Log
@Service
public class ReportPersistenceImpl implements ReportPersistencePort {

	private static final String INVENTORY_TEMPLATE = "/reports/inventory/InventoryReportDocument.jasper";
	private static final String REMISSION_ENTRY_TEMPLATE = "/reports/remissionEntry/RemissionEntryReportDocument.jasper";
	private static final String REMISSION_OUTPUT_TEMPLATE = "/reports/remissionOutput/RemissionOutputReportDocument.jasper";
	private static final String LOGO_REPORT = "/reports/logo.jpg";

	@Autowired
	private InventoryDSLReportRepository inventoryDSLReportRepository;

	@Autowired
	private RemissionEntryDSLReportRepository remissionEntryDSLReportRepository;

	@Autowired
	private RemissionOutputDSLReportRepository remissionOutputDSLReportRepository;

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
	public ResponseModel generateInventoryReport(InventoryReportDocument inventoryReportDocument) {
		try {
			log.info("INIT generateInventoryReport()");

			log.info(String.format("LOAD REPORT "));
			InputStream file = this.getClass().getResourceAsStream(INVENTORY_TEMPLATE);

			InputStream logoImage = this.getClass().getResourceAsStream(LOGO_REPORT);
			inventoryReportDocument.setLogo(logoImage);

			Collection<InventoryReportDocument> collection = Collections.singletonList(inventoryReportDocument);

			JasperPrint jasperPrint = JasperFillManager.fillReport(file, null,
					new JRBeanCollectionDataSource(collection));

			byte[] remissionEntryReport = JasperExportManager.exportReportToPdf(jasperPrint);

			log.info(String.format("RETURN DATA "));
			return new ResponseModel(remissionEntryReport);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public ResponseModel generateRemissionEntryReport(RemissionEntryReportDocument remissionEntryReportDocument) {
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

			byte[] remissionEntryReport = JasperExportManager.exportReportToPdf(jasperPrint);

			log.info(String.format("RETURN DATA "));
			return new ResponseModel(remissionEntryReport);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public ResponseModel generateRemissionOutputReport(RemissionOutputReportDocument remissionOutputReportDocument) {
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

			byte[] remissionEntryReport = JasperExportManager.exportReportToPdf(jasperPrint);

			log.info(String.format("RETURN DATA "));
			return new ResponseModel(remissionEntryReport);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

}
