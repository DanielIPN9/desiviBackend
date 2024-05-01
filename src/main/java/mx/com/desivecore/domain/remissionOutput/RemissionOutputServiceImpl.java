package mx.com.desivecore.domain.remissionOutput;

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
import mx.com.desivecore.domain.clients.models.Client;
import mx.com.desivecore.domain.clients.models.ClientSummary;
import mx.com.desivecore.domain.clients.ports.ClientPersistencePort;
import mx.com.desivecore.domain.products.models.ProductAvailability;
import mx.com.desivecore.domain.products.models.ProductOutputSummary;
import mx.com.desivecore.domain.products.ports.ProductPersistencePort;
import mx.com.desivecore.domain.remissionOutput.models.ProductOutput;
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutput;
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutputSearchParams;
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutputSummary;
import mx.com.desivecore.domain.remissionOutput.models.document.RemissionOutputDocument;
import mx.com.desivecore.domain.remissionOutput.ports.RemissionOutputPersistencePort;
import mx.com.desivecore.domain.remissionOutput.ports.RemissionOutputServicePort;
import mx.com.desivecore.domain.users.models.UserModel;
import mx.com.desivecore.domain.users.ports.UserPersistencePort;
import mx.com.desivecore.infraestructure.configuration.exceptions.InternalError;
import mx.com.desivecore.infraestructure.configuration.exceptions.ValidationError;

@Log
@Service
public class RemissionOutputServiceImpl implements RemissionOutputServicePort {

	private static final String SERIAL_NUMBER_CODE = "RO";

	@Autowired
	private UserPersistencePort userPersistencePort;

	@Autowired
	private ProductPersistencePort productPersistencePort;

	@Autowired
	private BranchPersistencePort branchPersistencePort;

	@Autowired
	private ClientPersistencePort clientPersistencePort;

	@Autowired
	private RemissionOutputPersistencePort remissionOutputPersistencePort;

	private RemissionOutputValidator reOutputValidator = new RemissionOutputValidator();

	private StringUtil stringUtil = new StringUtil();

	@Override
	public ResponseModel createRemissionOutput(RemissionOutput remissionOutput) {
		String validations = reOutputValidator.validOperativeDataToCreate(remissionOutput);
		if (!validations.isEmpty()) {
			log.warning("BAD PARAMS: " + validations);
			throw new ValidationError(validations);
		}

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		UserModel user = userPersistencePort.findUserByEmail(loggedInUser.getName(), true);

		BigInteger serialNumber = remissionOutputPersistencePort.getConsecutiveByCode(SERIAL_NUMBER_CODE);
		String serial = String.valueOf(serialNumber.longValue());
		serial = stringUtil.autocompleteSpace(serial, 5, false);

		remissionOutput.generateRemissionOutputSummary(user, serial);
		RemissionOutput remissionOtputCreated = remissionOutputPersistencePort.saveRemissionOutput(remissionOutput);

		if (remissionOtputCreated == null)
			throw new InternalError();

		updateProductAvailabilityByCreate(remissionOutput);

		return new ResponseModel(remissionOtputCreated);
	}

	private void updateProductAvailabilityByCreate(RemissionOutput remissionOutput) {
		log.info("INIT updateProductAvailabilityByCreate()");
		List<ProductAvailability> availabilityList = new ArrayList<>();
		for (ProductOutput productOutput : remissionOutput.getProducts()) {
			ProductAvailability productAvailability = productPersistencePort.findByProducIdAndBranchId(
					productOutput.getProduct().getProductId(), remissionOutput.getBranch().getBranchId());

			if (productAvailability != null) {
				productAvailability.updateAvailability(-productOutput.getAmount());
				availabilityList.add(productAvailability);
			}
		}
		productPersistencePort.saveAvailability(availabilityList, null);
	}

	@Override
	public ResponseModel viewRemissionById(Long remssionOutputId) {
		RemissionOutput remissionOutput = remissionOutputPersistencePort.viewRemissionById(remssionOutputId);
		if (remissionOutput == null)
			throw new ValidationError("Registro no encontrado");
		return new ResponseModel(remissionOutput);
	}

	@Override
	public ResponseModel cancelRemissionById(Long remssionOutputId) {

		RemissionOutput remissionOutput = remissionOutputPersistencePort.viewRemissionById(remssionOutputId);
		if (remissionOutput == null)
			throw new ValidationError("Registro no encontrado");

		if (!remissionOutput.isStatus())
			throw new ValidationError("La orden ha sido cancelada");

		List<ProductAvailability> availabilityPlusList = new ArrayList<>();
		for (ProductOutput productOutput : remissionOutput.getProducts()) {
			ProductAvailability productAvailability = productPersistencePort.findByProducIdAndBranchId(
					productOutput.getProduct().getProductId(), remissionOutput.getBranch().getBranchId());
			productAvailability.updateAvailability(productOutput.getAmount());
			availabilityPlusList.add(productAvailability);
		}
		productPersistencePort.saveAvailability(availabilityPlusList, null);

		boolean cancelStatus = remissionOutputPersistencePort.cancelRemissionById(remssionOutputId);

		if (!cancelStatus)
			throw new ValidationError("Error al cambiar estado de cancelación. Contacte a su Administrador");

		return new ResponseModel(cancelStatus);
	}

	@Override
	public ResponseModel searchRemissionOutputByParams(RemissionOutputSearchParams remissionOutputSearchParams) {
		List<RemissionOutputSummary> remissionOutputSummaryList = remissionOutputPersistencePort
				.searchRemissionOutputByParams(remissionOutputSearchParams);
		if (remissionOutputSummaryList == null) {
			log.warning("EMPTY DATA");
			return new ResponseModel(new ArrayList<>());
		}
		return new ResponseModel(remissionOutputSummaryList);
	}

	@Override
	public ResponseModel updateRemissionOutput(RemissionOutput remissionOutput) {
		String validations = reOutputValidator.validOperativeDataToCreate(remissionOutput);
		if (!validations.isEmpty()) {
			log.warning("BAD PARAMS: " + validations);
			throw new ValidationError(validations);
		}

		RemissionOutput remissionOutputSaved = remissionOutputPersistencePort
				.viewRemissionById(remissionOutput.getRemissionOutputId());
		if (remissionOutputSaved == null) {
			log.warning("DATA NOT FOUND ");
			throw new InternalError();
		}

		remissionOutput.generateRemissionOutputToUpdate(remissionOutputSaved);
		RemissionOutput remissionOtputUpdated = remissionOutputPersistencePort.updateRemissionOutput(remissionOutput);
		if (remissionOtputUpdated == null)
			throw new InternalError();

		updateProductAvailabilityByUpdate(remissionOutput, remissionOutputSaved);

		return new ResponseModel(remissionOtputUpdated);
	}

	@Override
	public ResponseModel viewAllByUserLogin(String emailUser) {
		log.info("INIT viewAllByUserLogin()");
		UserModel user = userPersistencePort.findUserByEmail(emailUser, true);
		if (user == null) {
			log.severe("USER NOT FOUND");
			throw new InternalError();
		}
		RemissionOutputSearchParams searchParams = new RemissionOutputSearchParams();
		List<RemissionOutputSummary> remissionOutputSummaryList = remissionOutputPersistencePort
				.searchRemissionOutputByParams(searchParams);

		if (remissionOutputSummaryList == null)
			return new ResponseModel(new ArrayList<>());

		return new ResponseModel(remissionOutputSummaryList);
	}

	private void updateProductAvailabilityByUpdate(RemissionOutput remissionOutput,
			RemissionOutput remissionOutputSaved) {
		log.info("INIT updateProductAvailabilityByUpdate()");

		List<ProductAvailability> availabilityPlusList = new ArrayList<>();
		for (ProductOutput productOutput : remissionOutputSaved.getProducts()) {
			ProductAvailability productAvailability = productPersistencePort.findByProducIdAndBranchId(
					productOutput.getProduct().getProductId(), remissionOutputSaved.getBranch().getBranchId());
			productAvailability.updateAvailability(productOutput.getAmount());
			availabilityPlusList.add(productAvailability);
		}
		productPersistencePort.saveAvailability(availabilityPlusList, null);

		updateProductAvailabilityByCreate(remissionOutput);
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

	@Override
	public ResponseModel viewProductActiveList(Long branchId) {
		List<ProductOutputSummary> productOutputList = productPersistencePort.findAllByBranchId(branchId);
		if (productOutputList == null) {
			log.warning("EMPTY DATA");
			return new ResponseModel(new ArrayList<>());
		}
		return new ResponseModel(productOutputList);
	}

	@Override
	public ResponseModel generateRemissionDocumentById(Long remissionOutputId) {
		RemissionOutput remissionOutputSaved = remissionOutputPersistencePort.viewRemissionById(remissionOutputId);
		if (remissionOutputSaved == null) {
			log.warning("DATA NOT FOUND ");
			throw new InternalError();
		}
		Client client = clientPersistencePort.findClientById(remissionOutputSaved.getClient().getClientId());
		RemissionOutputDocument reOutputDocument = new RemissionOutputDocument(remissionOutputSaved, client);
		log.info(reOutputDocument.toString());
		ResponseModel responseDocument = remissionOutputPersistencePort.generateRemissionDocument(reOutputDocument);
		return responseDocument;
	}

}
