package mx.com.desivecore.domain.returnRemissionOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.domain.branches.ports.BranchPersistencePort;
import mx.com.desivecore.domain.clients.models.Client;
import mx.com.desivecore.domain.clients.models.ClientSummary;
import mx.com.desivecore.domain.clients.ports.ClientPersistencePort;
import mx.com.desivecore.domain.quarantine.ports.QuarantineServicePort;
import mx.com.desivecore.domain.remissionOutput.models.ProductOutput;
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutput;
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutputSearchParams;
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutputSummary;
import mx.com.desivecore.domain.remissionOutput.ports.RemissionOutputPersistencePort;
import mx.com.desivecore.domain.returnRemissionOutput.models.ReturnProductOutput;
import mx.com.desivecore.domain.returnRemissionOutput.models.ReturnROSearchParams;
import mx.com.desivecore.domain.returnRemissionOutput.models.ReturnROSummary;
import mx.com.desivecore.domain.returnRemissionOutput.models.ReturnRemissionOutput;
import mx.com.desivecore.domain.returnRemissionOutput.ports.ReturnROPersistencePort;
import mx.com.desivecore.domain.returnRemissionOutput.ports.ReturnROServicePort;
import mx.com.desivecore.infraestructure.configuration.exceptions.InternalError;
import mx.com.desivecore.infraestructure.configuration.exceptions.ValidationError;

@Log
@Service
public class ReturnRemissionOutputServiceImpl implements ReturnROServicePort {

	private static final String DATA_NOT_FOUND = "Registro no encontrado";

	final String ERROR_MESSAGE = " -Folio no encontrado";

	@Autowired
	private ReturnROPersistencePort returnROPersistencePort;

	@Autowired
	private QuarantineServicePort quarantineServicePort;

	@Autowired
	private RemissionOutputPersistencePort remissionOutputPersistencePort;

	@Autowired
	private BranchPersistencePort branchPersistencePort;

	@Autowired
	private ClientPersistencePort clientPersistencePort;

	private ReturnRemissionOutputValidator remissionOutputValidator = new ReturnRemissionOutputValidator();

	@Override
	public ResponseModel searchRemissionOutputSummaryByFolio(String folio) {
		log.info("INIT searchRemissionOutputSummaryByFolio()");
		RemissionOutputSearchParams remissionOutputSearchParams = new RemissionOutputSearchParams();
		remissionOutputSearchParams.setFolio(folio);

		List<RemissionOutputSummary> remissionOutputSumaryList = remissionOutputPersistencePort
				.searchRemissionOutputByParams(remissionOutputSearchParams);

		RemissionOutput remissionOutput = remissionOutputSumaryList.stream().findFirst()
				.map(remissionOutputSummary -> remissionOutputPersistencePort
						.viewRemissionById(remissionOutputSummary.getRemissionOutputId()))
				.orElseThrow(() -> new ValidationError(ERROR_MESSAGE));

		generateReturnProductSummary(folio, remissionOutput);

		Double totalProductAmount = remissionOutput.getProducts().stream().mapToDouble(ProductOutput::getAmount).sum();
		if (totalProductAmount == 0)
			throw new ValidationError("La orden no cuenta con cantidad disponible para proceso de devolución");

		return new ResponseModel(remissionOutput);
	}

	private void generateReturnProductSummary(String folio, RemissionOutput remissionOutput) {
		log.info("INIT generateReturnProductSummary()");
		ReturnROSearchParams returnROSearchParams = new ReturnROSearchParams();
		returnROSearchParams.setFolio(folio);

		List<ReturnROSummary> returnROSummaryList = returnROPersistencePort
				.searchReturnRemissionOutputByParams(returnROSearchParams);

		if (returnROSummaryList != null) {

			List<ReturnProductOutput> productReturnList = new ArrayList<>();
			for (ReturnROSummary returnROSummary : returnROSummaryList) {
				ReturnRemissionOutput returnRemissionOutput = returnROPersistencePort
						.viewReturnRODetailById(returnROSummary.getReturnROId());
				productReturnList.addAll(returnRemissionOutput.getProducts());
			}

			Map<Long, Double> returnedAmountMap = new HashMap<>();
			for (ReturnProductOutput returnProductOutput : productReturnList) {
				Long productId = returnProductOutput.getProduct().getProductId();
				Double returnedAmount = returnProductOutput.getAmountReturn();
				returnedAmountMap.merge(productId, returnedAmount, Double::sum);
			}

			for (ProductOutput productOutput : remissionOutput.getProducts()) {
				Long productId = productOutput.getProduct().getProductId();
				if (returnedAmountMap.containsKey(productId)) {
					Double remainingAmount = productOutput.getAmount() - returnedAmountMap.get(productId);
					productOutput.setAmount(remainingAmount);
				}
			}
		}
	}

	@Override
	public ResponseModel generateReturnRemissionOutput(RemissionOutput remissionOutput) {

		RemissionOutput remissionOutputSaved = remissionOutputPersistencePort
				.viewRemissionById(remissionOutput.getRemissionOutputId());
		if (remissionOutputSaved == null)
			throw new ValidationError("Remisión de Salida no encontrada");

		String validations = remissionOutputValidator.validOperativeDataToGenerate(remissionOutput,
				remissionOutputSaved, returnROPersistencePort);
		if (!validations.isEmpty())
			throw new ValidationError(validations);

		ReturnRemissionOutput returnRemissionOutput = new ReturnRemissionOutput(remissionOutput);
		ReturnRemissionOutput returnRemissionOutputCreated = returnROPersistencePort
				.generateReturnRemissionOutput(returnRemissionOutput);

		if (returnRemissionOutputCreated == null)
			throw new InternalError();

		try {
			quarantineServicePort.inputProductByRemissionOutput(returnRemissionOutput);
		} catch (Exception e) {
			log.severe(e.getMessage());
		}
		return new ResponseModel(returnRemissionOutputCreated);
	}

	@Override
	public ResponseModel generateReturnRODocumentById(Long returnROId) {
		ReturnRemissionOutput returnRemissionOutputSaved = returnROPersistencePort.viewReturnRODetailById(returnROId);
		if (returnRemissionOutputSaved == null)
			throw new ValidationError(DATA_NOT_FOUND);

		return null;
	}

	@Override
	public ResponseModel viewReturnRODetailById(Long returnROId) {
		ReturnRemissionOutput remissionOutput = returnROPersistencePort.viewReturnRODetailById(returnROId);
		return Optional.ofNullable(remissionOutput).map(ResponseModel::new)
				.orElseThrow(() -> new ValidationError(DATA_NOT_FOUND));
	}

	@Override
	public ResponseModel viewAllReturnRemissionOutput() {
		List<ReturnROSummary> returnROSummaryList = returnROPersistencePort.viewAllReturnRemissionOutput();
		if (returnROSummaryList == null) {
			log.warning("EMPTY DATA");
			return new ResponseModel(new ArrayList<>());
		}
		return new ResponseModel(returnROSummaryList);
	}

	@Override
	public ResponseModel searchReturnRemissionOutputByParams(ReturnROSearchParams returnROSearchParams) {
		List<ReturnROSummary> returnROSummaryList = returnROPersistencePort
				.searchReturnRemissionOutputByParams(returnROSearchParams);
		if (returnROSummaryList == null) {
			log.warning("EMPTY DATA");
			return new ResponseModel(new ArrayList<>());
		}
		return new ResponseModel(returnROSummaryList);
	}

	@Override
	public ResponseModel viewClientActiveList() {
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
