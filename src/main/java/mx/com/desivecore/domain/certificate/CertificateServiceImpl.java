package mx.com.desivecore.domain.certificate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.domain.branches.ports.BranchPersistencePort;
import mx.com.desivecore.domain.certificate.models.ProductCertificate;
import mx.com.desivecore.domain.certificate.models.RemissionSummary;
import mx.com.desivecore.domain.certificate.models.document.CertificateDocument;
import mx.com.desivecore.domain.certificate.ports.CertificatePersistencePort;
import mx.com.desivecore.domain.certificate.ports.CertificateServicePort;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntry;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntrySummary;
import mx.com.desivecore.domain.remissionEntry.models.RemissionSearchParams;
import mx.com.desivecore.domain.remissionEntry.ports.RemissionEntryPersistencePort;
import mx.com.desivecore.domain.suppliers.models.Supplier;
import mx.com.desivecore.domain.suppliers.ports.SupplierPersistencePort;
import mx.com.desivecore.infraestructure.configuration.exceptions.InternalError;
import mx.com.desivecore.infraestructure.configuration.exceptions.ValidationError;

@Log
@Service
public class CertificateServiceImpl implements CertificateServicePort {

	@Autowired
	private CertificatePersistencePort certificatePersistencePort;

	@Autowired
	private RemissionEntryPersistencePort remissionEntryPersistencePort;
	
	@Autowired
	private BranchPersistencePort branchPersistencePort;
	
	@Autowired
	private SupplierPersistencePort supplierPersistencePort;

	private CertificateValidator certificateValidator = new CertificateValidator();

	@Override
	public ResponseModel viewRemissionEntrySummary(RemissionSearchParams remissionSearchParams) {

		List<RemissionEntrySummary> remissionEntrySummaryList = remissionEntryPersistencePort
				.searchRemissionEntryByParams(remissionSearchParams);
		if (remissionEntrySummaryList == null)
			return new ResponseModel(new ArrayList<>());

		List<RemissionSummary> remissionSummaryList = certificatePersistencePort
				.findCertificateByRemissionEntryList(remissionEntrySummaryList);

		return new ResponseModel(remissionSummaryList);
	}

	@Override
	public ResponseModel viewCertificateByRemissionAndProduct(Long remissionId, Long productId) {
		ProductCertificate productCertificate = certificatePersistencePort
				.findCertificateIdByRemissionEntryIdAndProductId(remissionId, productId);
		if (productCertificate != null)
			return new ResponseModel(productCertificate);

		log.info("GENERATE SUMMARY");
		RemissionEntry remissionEntry = remissionEntryPersistencePort.viewRemissionById(remissionId);
		if (remissionEntry == null)
			throw new ValidationError("Remisión de entrada invaldia.");

		ProductCertificate productCertificateSummary = new ProductCertificate(remissionEntry, productId);

		if (productCertificateSummary.getProductName() == null || productCertificateSummary.getSku() == null)
			throw new InternalError();

		return new ResponseModel(productCertificateSummary);
	}

	@Override
	public ResponseModel createCertificate(ProductCertificate productCertificate) {
		String validations = certificateValidator.validOperativeDataToCreate(productCertificate,
				certificatePersistencePort);
		if (!validations.isEmpty())
			throw new ValidationError("Valores de entrada invalidos: " + validations);

		ProductCertificate productCertificateCreated = certificatePersistencePort
				.saveProductCertificate(productCertificate);
		if (productCertificateCreated == null)
			throw new InternalError();

		return new ResponseModel(productCertificateCreated);
	}

	@Override
	public ResponseModel updateCertificateById(ProductCertificate productCertificate) {
		String validations = certificateValidator.validOperativeDataToUpdate(productCertificate,
				certificatePersistencePort);
		if (!validations.isEmpty())
			throw new ValidationError("Valores de entrada invalidos: " + validations);

		ProductCertificate productCertificateUpdated = certificatePersistencePort
				.saveProductCertificate(productCertificate);
		if (productCertificateUpdated == null)
			throw new InternalError();

		return new ResponseModel(productCertificateUpdated);
	}

	@Override
	public ResponseModel generateDocumentCertificateById(Long certificateId) {
		ProductCertificate productCertificate = certificatePersistencePort.findCertificateIdById(certificateId);
		if (productCertificate == null) {
			log.warning("DATA NOT FOUND");
			throw new ValidationError("El certificado solicitado no existe");
		}

		CertificateDocument certificateDocument = new CertificateDocument(productCertificate);
		log.info(certificateDocument.toString());
		ResponseModel response = certificatePersistencePort.generateDocumentCertificate(certificateDocument);

		return response;
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
}