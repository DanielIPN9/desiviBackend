package mx.com.desivecore.domain.remissionEntry;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.commons.utils.StringUtil;
import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.domain.branches.ports.BranchPersistencePort;
import mx.com.desivecore.domain.products.models.Product;
import mx.com.desivecore.domain.products.models.ProductAvailability;
import mx.com.desivecore.domain.products.ports.ProductPersistencePort;
import mx.com.desivecore.domain.remissionEntry.models.ProductEntry;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntry;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntryDocument;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntryHistory;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntrySummary;
import mx.com.desivecore.domain.remissionEntry.models.RemissionSearchParams;
import mx.com.desivecore.domain.remissionEntry.ports.RemissionEntryPersistencePort;
import mx.com.desivecore.domain.remissionEntry.ports.RemissionEntryServicePort;
import mx.com.desivecore.domain.suppliers.models.Supplier;
import mx.com.desivecore.domain.suppliers.ports.SupplierPersistencePort;
import mx.com.desivecore.domain.users.models.UserModel;
import mx.com.desivecore.domain.users.ports.UserPersistencePort;
import mx.com.desivecore.infraestructure.configuration.exceptions.InternalError;
import mx.com.desivecore.infraestructure.configuration.exceptions.ValidationError;

@Log
@Service
public class RemissionEntryServiceImpl implements RemissionEntryServicePort {

	private static final String UPDATE_REMISSION = "Actualización de Remisión";

	private static final String SERIAL_NUMBER_CODE = "RE";

	private static final String CREACION_REMISION = "Creación Remisión";

	@Autowired
	private ProductPersistencePort productPersistencePort;

	@Autowired
	private UserPersistencePort userPersistencePort;

	@Autowired
	private SupplierPersistencePort supplierPersistencePort;

	@Autowired
	private BranchPersistencePort branchPersistencePort;

	@Autowired
	private RemissionEntryPersistencePort remissionEntryPersistencePort;

	private RemissionEntryValidator remissionEntryValidator = new RemissionEntryValidator();

	private StringUtil stringUtil = new StringUtil();

	@Override
	public ResponseModel createRemissionEntry(RemissionEntry remissionEntry) {
		log.info("INIT createRemissionEntry()");
		String validations = remissionEntryValidator.validOperativeDataToCreate(remissionEntry);
		if (!validations.isEmpty()) {
			log.warning("BAD PARAMS: " + validations);
			throw new ValidationError(validations);
		}

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		UserModel user = userPersistencePort.findUserByEmail(loggedInUser.getName(), true);

		BigInteger serialNumber = remissionEntryPersistencePort.getConsecutiveByCode(SERIAL_NUMBER_CODE);
		String serial = String.valueOf(serialNumber.longValue());
		serial = stringUtil.autocompleteSpace(serial, 5, false);

		remissionEntry.generateRemissionEntrySummary(user, serial);
		RemissionEntry remissionEntryCreated = remissionEntryPersistencePort.saveRemissionEntry(remissionEntry);

		if (remissionEntryCreated == null)
			throw new InternalError();

		generateHistoricalRecord(remissionEntryCreated, CREACION_REMISION);

		updateProductAvailabilityByCreate(remissionEntry);

		return new ResponseModel(remissionEntryCreated);
	}

	private void generateHistoricalRecord(RemissionEntry remissionEntry, String action) {
		try {
			log.info("INIT generateHistoricalRecord()");
			RemissionEntryHistory remissionEntryHistory = new RemissionEntryHistory();
			remissionEntryHistory.generateSummary(remissionEntry, action);
			remissionEntryHistory = remissionEntryPersistencePort.saveRemissionEntryHistory(remissionEntryHistory);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
		}

	}

	private void updateProductAvailabilityByCreate(RemissionEntry remissionEntry) {
		log.info("INIT updateProductAvailability()");
		List<ProductAvailability> availabilityList = new ArrayList<>();
		for (ProductEntry productEntry : remissionEntry.getProducts()) {
			ProductAvailability productAvailability = productPersistencePort.findByProducIdAndBranchId(
					productEntry.getProduct().getProductId(), remissionEntry.getBranch().getBranchId());

			if (productAvailability != null) {
				productAvailability.updateAvailability(productEntry.getAmount());
				availabilityList.add(productAvailability);
			}

			if (productAvailability == null) {
				availabilityList.add(new ProductAvailability(null, remissionEntry.getBranch(),
						productEntry.getProduct().getProductId(), productEntry.getAmount()));
			}

		}
		productPersistencePort.saveAvailability(availabilityList, null);
	}

	@Override
	public ResponseModel viewAllByUserLogin(String userEmail) {
		log.info("INIT viewAllByUserLogin()");
		UserModel user = userPersistencePort.findUserByEmail(userEmail, true);
		if (user == null) {
			log.severe("USER NOT FOUND");
			throw new InternalError();
		}
		List<RemissionEntry> remissionEntryList = remissionEntryPersistencePort
				.searchRemissionEntryByUserId(user.getUserId());
		if (remissionEntryList == null)
			return new ResponseModel(new ArrayList<>());

		List<RemissionEntrySummary> remissionEntrySummaryList = new ArrayList<>();
		for (RemissionEntry remissionEntry : remissionEntryList) {
			remissionEntrySummaryList.add(new RemissionEntrySummary(remissionEntry.getRemissionEntryId(),
					remissionEntry.getFolio(), remissionEntry.getCreationDate(), remissionEntry.getRequestDate(),
					remissionEntry.getSupplier().getBusinessName(), remissionEntry.getBranch().getName(),
					remissionEntry.getRemissionTotal()));
		}

		return new ResponseModel(remissionEntrySummaryList);
	}

	@Override
	public ResponseModel viewRemissionById(Long remissionEntryId) {
		log.info("INIT viewRemissionById()");
		RemissionEntry remissionEntry = remissionEntryPersistencePort.viewRemissionById(remissionEntryId);
		if (remissionEntry == null)
			throw new ValidationError("Registro no encontrado");
		return new ResponseModel(remissionEntry);
	}

	@Override
	public ResponseModel viewRemissionHistoryById(Long remissionEntryId) {
		log.info("INIT viewRemissionHistoryById()");
		List<RemissionEntryHistory> remissionEntryHistoryList = remissionEntryPersistencePort
				.viewRemissionHistoryById(remissionEntryId);
		if (remissionEntryHistoryList == null)
			return new ResponseModel(new ArrayList<>());
		return new ResponseModel(remissionEntryHistoryList);
	}

	@Override
	public ResponseModel searchRemissionEntryByParams(RemissionSearchParams remissionSearchParams) {
		log.info("INIT viewRemissionHistoryById()");
		List<RemissionEntrySummary> remissionEntrySummaryList = remissionEntryPersistencePort
				.searchRemissionEntryByParams(remissionSearchParams);
		if (remissionEntrySummaryList == null)
			return new ResponseModel(new ArrayList<>());
		return new ResponseModel(remissionEntrySummaryList);
	}

	@Override
	public ResponseModel updateRemissionEntry(RemissionEntry remissionEntry) {
		log.info("INIT updateRemissionEntry()");

		String validations = remissionEntryValidator.validOperativeDataToCreate(remissionEntry);
		if (!validations.isEmpty()) {
			log.warning("BAD PARAMS: " + validations);
			throw new ValidationError(validations);
		}

		RemissionEntry remissionEntrySaved = remissionEntryPersistencePort
				.viewRemissionById(remissionEntry.getRemissionEntryId());
		if (remissionEntrySaved == null) {
			log.warning("DATA NOT FOUND ");
			throw new InternalError();
		}

		remissionEntry.generateRemissionEntrySummaryToUpdate(remissionEntrySaved);
		RemissionEntry remissionEntryUpdated = remissionEntryPersistencePort.updateRemissionEntry(remissionEntry);

		if (remissionEntryUpdated == null)
			throw new InternalError();

		updateProductAvailabilityByUpdate(remissionEntry, remissionEntrySaved);

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		UserModel user = userPersistencePort.findUserByEmail(loggedInUser.getName(), true);
		generateHistoricalRecordByUpdate(remissionEntry, UPDATE_REMISSION, user);

		return new ResponseModel(remissionEntry);
	}

	private void updateProductAvailabilityByUpdate(RemissionEntry remissionEntry, RemissionEntry remissionEntrySaved) {
		log.info("INIT updateProductAvailabilityByUpdate()");

		List<ProductAvailability> availabilitySubtractionList = new ArrayList<>();
		for (ProductEntry productEntry : remissionEntrySaved.getProducts()) {
			ProductAvailability productAvailability = productPersistencePort.findByProducIdAndBranchId(
					productEntry.getProduct().getProductId(), remissionEntrySaved.getBranch().getBranchId());
			productAvailability.updateAvailability(-productEntry.getAmount());
			availabilitySubtractionList.add(productAvailability);
		}
		productPersistencePort.saveAvailability(availabilitySubtractionList, null);

		List<ProductAvailability> availabilityList = new ArrayList<>();
		for (ProductEntry productEntry : remissionEntry.getProducts()) {
			ProductAvailability productAvailability = productPersistencePort.findByProducIdAndBranchId(
					productEntry.getProduct().getProductId(), remissionEntry.getBranch().getBranchId());

			if (productAvailability != null) {
				productAvailability.updateAvailability(productEntry.getAmount());
				availabilityList.add(productAvailability);
			}

			if (productAvailability == null) {
				availabilityList.add(new ProductAvailability(null, remissionEntry.getBranch(),
						productEntry.getProduct().getProductId(), productEntry.getAmount()));
			}

		}
		productPersistencePort.saveAvailability(availabilityList, null);

	}

	private void generateHistoricalRecordByUpdate(RemissionEntry remissionEntry, String action, UserModel user) {
		try {
			log.info("INIT generateHistoricalRecord()");
			RemissionEntryHistory remissionEntryHistory = new RemissionEntryHistory();
			remissionEntryHistory.generateSummaryByUpdate(remissionEntry, action, user);
			remissionEntryHistory = remissionEntryPersistencePort.saveRemissionEntryHistory(remissionEntryHistory);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
		}

	}

	@Override
	public ResponseModel generateRemissionDocumentById(Long remissionEntryId) {
		log.info("INIT generateRemissionDocumentById()");
		RemissionEntry remissionEntry = remissionEntryPersistencePort.viewRemissionById(remissionEntryId);
		if (remissionEntry == null)
			throw new InternalError();
		RemissionEntryDocument remissionEntryDocument = new RemissionEntryDocument(remissionEntry);
		log.info(remissionEntryDocument.toString());
		ResponseModel responseDocument = remissionEntryPersistencePort
				.generateRemissionDocument(remissionEntryDocument);
		return responseDocument;
	}

	@Override
	public ResponseModel viewSupplierActiveList() {
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
	public ResponseModel viewBranchActiveList() {
		log.info("INIT viewBranchActiveList()");
		List<Branch> branchList = branchPersistencePort.findAllBranch();
		if (branchList == null) {
			log.warning("EMPTY DATA");
			return new ResponseModel(new ArrayList<>());
		}
		return new ResponseModel(branchList);
	}

	@Override
	public ResponseModel viewProductActiveList() {
		log.info("INIT viewProductActiveList()");
		List<Product> productList = productPersistencePort.viewALLProduct();
		if (productList == null) {
			log.warning("EMPTY DATA");
			return new ResponseModel(new ArrayList<>());
		}
		return new ResponseModel(productList);
	}

}
