package mx.com.desivecore.domain.returnRemissionEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.remissionEntry.models.ProductEntry;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntry;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntrySummary;
import mx.com.desivecore.domain.remissionEntry.models.RemissionSearchParams;
import mx.com.desivecore.domain.remissionEntry.ports.RemissionEntryPersistencePort;
import mx.com.desivecore.domain.returnRemissionEntry.models.ReturnProductEntry;
import mx.com.desivecore.domain.returnRemissionEntry.models.ReturnRESearchParams;
import mx.com.desivecore.domain.returnRemissionEntry.models.ReturnRESummary;
import mx.com.desivecore.domain.returnRemissionEntry.models.ReturnRemissionEntry;
import mx.com.desivecore.domain.returnRemissionEntry.ports.ReturnREPersistencePort;
import mx.com.desivecore.domain.returnRemissionEntry.ports.ReturnREServicePort;
import mx.com.desivecore.infraestructure.configuration.exceptions.InternalError;
import mx.com.desivecore.infraestructure.configuration.exceptions.ValidationError;

@Log
@Service
public class ReturnRemissionEntryServiceImpl implements ReturnREServicePort {

	final String ERROR_MESSAGE = " -Folio no encontrado";

	@Autowired
	private ReturnREPersistencePort returnREPersistencePort;

	@Autowired
	private RemissionEntryPersistencePort remissionEntryPersistencePort;

	private ReturnRemissionEntryValidator remissionEntryValidator = new ReturnRemissionEntryValidator();

	@Override
	public ResponseModel searchRemissionEntrySummaryByFolio(String folio) {
		log.info("INIT searchRemissionEntrySummaryByFolio");
		RemissionSearchParams remissionSearchParams = new RemissionSearchParams();
		remissionSearchParams.setFolio(folio);

		List<RemissionEntrySummary> remissionEntrySummaryList = remissionEntryPersistencePort
				.searchRemissionEntryByParams(remissionSearchParams);

		RemissionEntry remissionEntry = remissionEntrySummaryList.stream().findFirst()
				.map(remissionSummary -> remissionEntryPersistencePort
						.viewRemissionById(remissionSummary.getRemissionEntryId()))
				.orElseThrow(() -> new ValidationError(ERROR_MESSAGE));

		generateReturnProductSummary(folio, remissionEntry);

		return new ResponseModel(remissionEntry);
	}

	private void generateReturnProductSummary(String folio, RemissionEntry remissionEntry) {
		log.info("INIT generateReturnProductSummary");
		ReturnRESearchParams returnRESearchParams = new ReturnRESearchParams();
		returnRESearchParams.setFolio(folio);

		List<ReturnRESummary> returnRESummaryList = returnREPersistencePort
				.searchReturnRemissionEntryByParams(returnRESearchParams);

		if (returnRESummaryList != null) {

			List<ReturnProductEntry> productReturnList = new ArrayList<>();

			for (ReturnRESummary returnRESummary : returnRESummaryList) {
				ReturnRemissionEntry returnRemissionEntry = returnREPersistencePort
						.viewReturnREDetailById(returnRESummary.getReturnREId());
				productReturnList.addAll(returnRemissionEntry.getProducts());
			}

			Map<Long, Double> returnedAmountMap = new HashMap<>();
			for (ReturnProductEntry returnProductEntry : productReturnList) {
				Long productId = returnProductEntry.getProduct().getProductId();
				Double returnedAmount = returnProductEntry.getAmountReturn();
				returnedAmountMap.merge(productId, returnedAmount, Double::sum);
			}

			for (ProductEntry productEntry : remissionEntry.getProducts()) {
				Long productId = productEntry.getProduct().getProductId();
				if (returnedAmountMap.containsKey(productId)) {
					Double remainingAmount = productEntry.getAmount() - returnedAmountMap.get(productId);
					productEntry.setAmount(remainingAmount);
				}
			}
		}
	}

	@Override
	public ResponseModel generateReturnRemissionEntry(RemissionEntry remissionEntry) {
		RemissionEntry remissionEntrySaved = remissionEntryPersistencePort
				.viewRemissionById(remissionEntry.getRemissionEntryId());
		if (remissionEntrySaved == null)
			throw new ValidationError("Remisión de Entrada no encontrada");

		String validations = remissionEntryValidator.validOperativeDataToGenerate(remissionEntry, remissionEntrySaved,
				returnREPersistencePort);
		if (!validations.isEmpty())
			throw new ValidationError(validations);

		ReturnRemissionEntry returnRemissionEntry = new ReturnRemissionEntry(remissionEntry);
		returnRemissionEntry = returnREPersistencePort.generateReturnRemissionEntry(returnRemissionEntry);

		if (returnRemissionEntry == null)
			throw new InternalError();

		return new ResponseModel(returnRemissionEntry);
	}

	@Override
	public ResponseModel viewDetailByReturnREId(Long retuernREId) {
		ReturnRemissionEntry returnRemissionEntry = returnREPersistencePort.viewReturnREDetailById(retuernREId);
		return Optional.ofNullable(returnRemissionEntry).map(ResponseModel::new)
				.orElseThrow(() -> new ValidationError("Registro no encontrado"));
	}

	@Override
	public ResponseModel generateReturnREDocumentById(Long retuernREId) {
		ReturnRemissionEntry returnRemissionEntrySaved = returnREPersistencePort.viewReturnREDetailById(retuernREId);
		if (returnRemissionEntrySaved == null)
			throw new ValidationError("Registro no encontrado");

		return null;
	}

	@Override
	public ResponseModel viewAllReturnRemissionEntry() {
		List<ReturnRESummary> returnRESummaryList = returnREPersistencePort.viewAllReturnRemissionEntry();
		if (returnRESummaryList == null) {
			log.warning("EMPTY DATA");
			return new ResponseModel(new ArrayList<>());
		}
		return new ResponseModel(returnRESummaryList);
	}

	@Override
	public ResponseModel searchReturnRemissionEntryByParams(ReturnRESearchParams returnRESearchParams) {
		List<ReturnRESummary> returnRESummaryList = returnREPersistencePort
				.searchReturnRemissionEntryByParams(returnRESearchParams);
		if (returnRESummaryList == null) {
			log.warning("EMPTY DATA");
			return new ResponseModel(new ArrayList<>());
		}
		return new ResponseModel(returnRESummaryList);
	}

}
