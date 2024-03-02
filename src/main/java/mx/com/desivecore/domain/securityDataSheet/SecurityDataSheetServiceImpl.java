package mx.com.desivecore.domain.securityDataSheet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.products.models.Product;
import mx.com.desivecore.domain.products.ports.ProductPersistencePort;
import mx.com.desivecore.domain.securityDataSheet.models.SecurityDataSheet;
import mx.com.desivecore.domain.securityDataSheet.models.SecurityDataSheetSummary;
import mx.com.desivecore.domain.securityDataSheet.models.document.SecurityDataSheetDocument;
import mx.com.desivecore.domain.securityDataSheet.ports.SecurityDataSheetPersistencePort;
import mx.com.desivecore.domain.securityDataSheet.ports.SecurityDataSheetServicePort;
import mx.com.desivecore.infraestructure.configuration.exceptions.InternalError;
import mx.com.desivecore.infraestructure.configuration.exceptions.ValidationError;

@Log
@Service
public class SecurityDataSheetServiceImpl implements SecurityDataSheetServicePort {

	@Autowired
	private SecurityDataSheetPersistencePort securityDataSheetPersistencePort;

	@Autowired
	private ProductPersistencePort productPersistencePort;

	private SecurityDataSheetValidator securityDataSheetValidator = new SecurityDataSheetValidator();

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

	@Override
	public ResponseModel createSecurityDataShet(SecurityDataSheet securityDataSheet) {
		String validations = securityDataSheetValidator.validOperativeDataToCreate(securityDataSheetPersistencePort,
				securityDataSheet);
		if (!validations.isEmpty()) {
			log.warning("BAD PARAMS: " + validations);
			throw new ValidationError(validations);
		}
		SecurityDataSheet securityDataSheetCreated = securityDataSheetPersistencePort
				.saveSecurityDataSheet(securityDataSheet);
		if (securityDataSheetCreated == null)
			throw new InternalError();
		return new ResponseModel(securityDataSheetCreated);
	}

	@Override
	public ResponseModel viewSecurityDataSheetById(Long secuDataSheetId) {
		SecurityDataSheet securityDataSheet = securityDataSheetPersistencePort
				.viewSecurityDataSheetById(secuDataSheetId);
		if (securityDataSheet == null) {
			log.info("DATA NOT FOUND");
			throw new ValidationError("Registro no encontrado");
		}
		return new ResponseModel(securityDataSheet);
	}

	@Override
	public ResponseModel viewAllSecurityDataSheet() {
		List<SecurityDataSheet> securityDataSheetList = securityDataSheetPersistencePort.viewAllSecurityDataSheet();
		if (securityDataSheetList == null) {
			log.info("EMPTY DATA");
			return new ResponseModel(new ArrayList<>());
		}
		List<SecurityDataSheetSummary> dataSheetSummaryList = new ArrayList<>();
		for (SecurityDataSheet securityDataSheet : securityDataSheetList) {
			dataSheetSummaryList.add(new SecurityDataSheetSummary(securityDataSheet.getSecurityDataSheetId(),
					securityDataSheet.getProduct().getSku(), securityDataSheet.getProduct().getName(),
					securityDataSheet.getTradeName()));
		}
		return new ResponseModel(dataSheetSummaryList);
	}

	@Override
	public ResponseModel updateSecurityDataSheetById(SecurityDataSheet securityDataSheet) {
		String validations = securityDataSheetValidator.validOperativeDataToUpdate(securityDataSheetPersistencePort,
				securityDataSheet);
		if (!validations.isEmpty()) {
			log.warning("BAD PARAMS: " + validations);
			throw new ValidationError(validations);
		}
		SecurityDataSheet securityDataSheetUpdated = securityDataSheetPersistencePort
				.saveSecurityDataSheet(securityDataSheet);
		if (securityDataSheetUpdated == null)
			throw new InternalError();
		return new ResponseModel(securityDataSheetUpdated);
	}

	@Override
	public ResponseModel generateDocumentSecurityDataSheetById(Long secuDataSheetId) {
		SecurityDataSheet securityDataSheet = securityDataSheetPersistencePort
				.viewSecurityDataSheetById(secuDataSheetId);
		if (securityDataSheet == null) {
			log.info("DATA NOT FOUND");
			throw new ValidationError("Registro no encontrado");
		}
		SecurityDataSheetDocument securityDataSheetDocument = new SecurityDataSheetDocument(securityDataSheet);
		log.info(securityDataSheetDocument.toString());
		ResponseModel response = securityDataSheetPersistencePort
				.generateDocumentSecurityDataSheet(securityDataSheetDocument);
		return response;
	}

}
