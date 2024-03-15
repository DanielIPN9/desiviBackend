package mx.com.desivecore.domain.reports;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.domain.branches.ports.BranchPersistencePort;
import mx.com.desivecore.domain.clients.models.Client;
import mx.com.desivecore.domain.clients.models.ClientSummary;
import mx.com.desivecore.domain.clients.ports.ClientPersistencePort;
import mx.com.desivecore.domain.products.models.Product;
import mx.com.desivecore.domain.products.ports.ProductPersistencePort;
import mx.com.desivecore.domain.reports.models.InventorySearch;
import mx.com.desivecore.domain.reports.models.ProductDetail;
import mx.com.desivecore.domain.reports.models.RemissionEntryDetail;
import mx.com.desivecore.domain.reports.models.RemissionEntrySearch;
import mx.com.desivecore.domain.reports.models.RemissionOutputDetail;
import mx.com.desivecore.domain.reports.models.RemissionOutputSearch;
import mx.com.desivecore.domain.reports.models.document.InventoryReportDocument;
import mx.com.desivecore.domain.reports.models.document.RemissionEntryReportDocument;
import mx.com.desivecore.domain.reports.models.document.RemissionOutputReportDocument;
import mx.com.desivecore.domain.reports.models.search.InventoryParamsReport;
import mx.com.desivecore.domain.reports.models.search.RemissionEntryParamsReport;
import mx.com.desivecore.domain.reports.models.search.RemissionOutputParamsReport;
import mx.com.desivecore.domain.reports.ports.ReportPersistencePort;
import mx.com.desivecore.domain.reports.ports.ReportServicePort;
import mx.com.desivecore.domain.suppliers.models.Supplier;
import mx.com.desivecore.domain.suppliers.ports.SupplierPersistencePort;
import mx.com.desivecore.infraestructure.configuration.exceptions.ValidationError;

@Log
@Service
public class ReportServiceImpl implements ReportServicePort {

	@Autowired
	private ProductPersistencePort productPersistencePort;

	@Autowired
	private BranchPersistencePort branchPersistencePort;

	@Autowired
	private ClientPersistencePort clientPersistencePort;

	@Autowired
	private SupplierPersistencePort supplierPersistencePort;

	@Autowired
	private ReportPersistencePort reportPersistencePort;

	private ReportValidator reportValidator = new ReportValidator();

	@Override
	public ResponseModel generateRemissionEntryReport(RemissionEntryParamsReport remissionEntryParamsReport) {
		String validations = reportValidator.validOperativeDataToRemissionEntryReport(remissionEntryParamsReport);
		if (!validations.isEmpty()) {
			log.warning("BAD PARAMS");
			throw new ValidationError(validations);
		}

		List<RemissionEntryDetail> remissionEntryDetailList = reportPersistencePort
				.searchRemissionEntryDataByParams(remissionEntryParamsReport);
		log.info(remissionEntryDetailList.toString());
		RemissionEntrySearch remissionEntrySearch = new RemissionEntrySearch();
		remissionEntrySearch.generateSummary(remissionEntryDetailList);

		RemissionEntryReportDocument remissionEntryReportDocument = new RemissionEntryReportDocument(
				remissionEntrySearch);
		log.info(remissionEntryReportDocument.toString());

		ResponseModel response = reportPersistencePort.generateRemissionEntryReport(remissionEntryReportDocument);

		return response;
	}

	@Override
	public ResponseModel generateRemissionOutputReport(RemissionOutputParamsReport remissionOutputParamsReport) {
		String validations = reportValidator.validOperativeDataToRemissionOutputReport(remissionOutputParamsReport);
		if (!validations.isEmpty()) {
			log.warning("BAD PARAMS");
			throw new ValidationError(validations);
		}

		List<RemissionOutputDetail> remissionOutputDetailList = reportPersistencePort
				.searchRemissionOutputDataByParams(remissionOutputParamsReport);
		RemissionOutputSearch remissionOutputSearch = new RemissionOutputSearch();
		remissionOutputSearch.generateSummary(remissionOutputDetailList);

		RemissionOutputReportDocument remissionOutputReportDocument = new RemissionOutputReportDocument(
				remissionOutputSearch);
		log.info(remissionOutputReportDocument.toString());

		ResponseModel response = reportPersistencePort.generateRemissionOutputReport(remissionOutputReportDocument);

		return response;
	}

	@Override
	public ResponseModel generateInventoryReport(InventoryParamsReport inventoryParamsReport) {
		String validations = reportValidator.validOperativeDataToInventoryReport(inventoryParamsReport);
		if (!validations.isEmpty()) {
			log.warning("BAD PARAMS");
			throw new ValidationError(validations);
		}
		List<Long> branchIdList = new ArrayList<>();
		List<Branch> branchList = branchPersistencePort.findAllBranch();
		if (inventoryParamsReport.getBarnch().getBranchId() == 0) {
			for (Branch branch : branchList) {
				branchIdList.add(branch.getBranchId());
			}
		}
		branchIdList.add(inventoryParamsReport.getBarnch().getBranchId());

		List<InventorySearch> inventorySearchList = new ArrayList<>();
		for (Long branchId : branchIdList) {
			String barnchName = "";
			for (Branch branch : branchList) {
				if (branch.getBranchId() == branchId)
					barnchName = branch.getName();
			}

			List<ProductDetail> productDetailList = reportPersistencePort.searchInventoryDataByParams(branchId);
			InventorySearch inventorySearch = new InventorySearch(barnchName, productDetailList);
			inventorySearchList.add(inventorySearch);
		}

		List<ProductDetail> productDetailList = new ArrayList<>();

		for (InventorySearch inventorySearch : inventorySearchList) {
			productDetailList.addAll(inventorySearch.getProductDetail());
		}

		InventoryReportDocument inventoryReportDocument = new InventoryReportDocument(productDetailList);
		log.info(inventoryReportDocument.toString());
		ResponseModel response = reportPersistencePort.generateInventoryReport(inventoryReportDocument);

		return response;
	}

	@Override
	public ResponseModel viewAllBranch() {
		log.info("INIT viewBranchActiveList()");
		List<Branch> branchList = branchPersistencePort.findAllBranch();
		if (branchList == null) {
			log.warning("EMPTY DATA");
			return new ResponseModel(new ArrayList<>());
		}
		Branch allBranch = new Branch();
		allBranch.setBranchId(0L);
		allBranch.setName("Todas las sucursales");
		branchList.add(allBranch);
		return new ResponseModel(branchList);
	}

	@Override
	public ResponseModel viwAllClient() {
		log.info("INIT viewClientActiveList()");
		List<Client> clientList = clientPersistencePort.viewAllClients();
		if (clientList == null) {
			log.warning("EMPTY DATA");
			return new ResponseModel(new ArrayList<>());
		}
		List<ClientSummary> clientSummaries = new ArrayList<>();
		for (Client client : clientList) {
			clientSummaries.add(new ClientSummary(client.getClientId(), client.getBusinessName()));
		}
		return new ResponseModel(clientSummaries);
	}

	@Override
	public ResponseModel viewAllSupplier() {
		log.info("INIT viewSupplierActiveList()");
		List<Supplier> supplierList = supplierPersistencePort.viewAllSupplier();
		if (supplierList == null) {
			log.warning("EMPTY DATA");
			return new ResponseModel(new ArrayList<>());
		}
		List<Supplier> supplierActiveList = new ArrayList<>();
		for (Supplier supplier : supplierList) {
			if (supplier.isStatus())
				supplierActiveList.add(supplier);
		}
		return new ResponseModel(supplierActiveList);
	}

	@Override
	public ResponseModel viewAllProduct() {
		log.info("INIT viewProductActiveList()");
		List<Product> productList = productPersistencePort.viewALLProduct();
		if (productList == null) {
			log.warning("EMPTY DATA");
			return new ResponseModel(new ArrayList<>());
		}
		return new ResponseModel(productList);
	}

}
