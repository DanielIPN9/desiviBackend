package mx.com.desivecore.domain.quote;

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
import mx.com.desivecore.domain.products.models.Product;
import mx.com.desivecore.domain.products.models.ProductAvailability;
import mx.com.desivecore.domain.products.ports.ProductPersistencePort;
import mx.com.desivecore.domain.quote.models.QuoteOrder;
import mx.com.desivecore.domain.quote.models.QuoteOrderSummary;
import mx.com.desivecore.domain.quote.models.QuoteSearchParams;
import mx.com.desivecore.domain.quote.models.document.QuoteOrderDocument;
import mx.com.desivecore.domain.quote.ports.QuotePersistencePort;
import mx.com.desivecore.domain.quote.ports.QuoteServicePort;
import mx.com.desivecore.domain.remissionOutput.models.ProductOutput;
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutput;
import mx.com.desivecore.domain.remissionOutput.ports.RemissionOutputPersistencePort;
import mx.com.desivecore.domain.users.models.UserModel;
import mx.com.desivecore.domain.users.ports.UserPersistencePort;
import mx.com.desivecore.infraestructure.configuration.exceptions.InternalError;
import mx.com.desivecore.infraestructure.configuration.exceptions.ValidationError;

@Log
@Service
public class QuoteServiceImpl implements QuoteServicePort {

	private static final String SERIAL_NUMBER_CODE = "CO";

	@Autowired
	private UserPersistencePort userPersistencePort;

	@Autowired
	private ProductPersistencePort productPersistencePort;

	@Autowired
	private BranchPersistencePort branchPersistencePort;

	@Autowired
	private ClientPersistencePort clientPersistencePort;

	@Autowired
	private QuotePersistencePort quotePersistencePort;

	@Autowired
	private RemissionOutputPersistencePort remissionOutputPersistencePort;

	private QuoteOrderValidator quoteOrderValidator = new QuoteOrderValidator();

	private QuoteBusinessConvert quoteBusinessConvert = new QuoteBusinessConvert();

	private StringUtil stringUtil = new StringUtil();

	@Override
	public ResponseModel createQuoteOrder(QuoteOrder quoteOrder) {
		String validations = quoteOrderValidator.validOperativeData(quoteOrder);
		if (!validations.isEmpty())
			throw new ValidationError(validations);

		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		UserModel user = userPersistencePort.findUserByEmail(loggedInUser.getName(), true);

		BigInteger serialNumber = quotePersistencePort.getConsecutiveByCode(SERIAL_NUMBER_CODE);
		String serial = String.valueOf(serialNumber.longValue());
		serial = stringUtil.autocompleteSpace(serial, 5, false);

		quoteOrder.generateQuoteOrderSummary(user, serial);
		log.info(quoteOrder.toString());
		QuoteOrder quoteOrderCreated = quotePersistencePort.saveQuoteOrder(quoteOrder);

		if (quoteOrderCreated == null)
			throw new InternalError();

		return new ResponseModel(quoteOrderCreated);
	}

	@Override
	public ResponseModel viewQouteOrderById(Long quoteId) {
		QuoteOrder quoteOrder = quotePersistencePort.viewQouteOrderById(quoteId);
		if (quoteOrder == null)
			throw new ValidationError("Registro no encontrado");
		return new ResponseModel(quoteOrder);
	}

	@Override
	public ResponseModel searchQuoteOrderByParams(QuoteSearchParams quoteSearchParams) {
		List<QuoteOrderSummary> quoteOrderSummaryList = quotePersistencePort
				.searchQuoteOrderByParams(quoteSearchParams);
		if (quoteOrderSummaryList == null) {
			log.warning("EMPTY DATA");
			return new ResponseModel(new ArrayList<>());
		}
		return new ResponseModel(quoteOrderSummaryList);
	}

	@Override
	public ResponseModel updateQuoteOrder(QuoteOrder quoteOrder) {
		QuoteOrder quoteOrderSaved = quotePersistencePort.viewQouteOrderById(quoteOrder.getQuoteOrderId());
		if (quoteOrderSaved == null)
			throw new ValidationError("Registro no encontrado");

		String validations = quoteOrderValidator.validOperativeData(quoteOrder);
		if (!validations.isEmpty())
			throw new ValidationError(validations);

		quoteOrder.generateQuoteOrderToUpdate(quoteOrderSaved);
		QuoteOrder quoteOrderUpdated = quotePersistencePort.saveQuoteOrder(quoteOrder);

		if (quoteOrderUpdated == null)
			throw new InternalError();

		return new ResponseModel(quoteOrderUpdated);
	}

	@Override
	public ResponseModel generateQuoteOrderDocumentById(Long quoteId) {
		QuoteOrder quoteOrderSaved = quotePersistencePort.viewQouteOrderById(quoteId);
		if (quoteOrderSaved == null)
			throw new ValidationError("Registro no encontrado");
		Client client = clientPersistencePort.findClientById(quoteOrderSaved.getClient().getClientId());
		QuoteOrderDocument quoteOrderDocument = new QuoteOrderDocument(quoteOrderSaved, client);
		log.info(quoteOrderDocument.toString());
		return quotePersistencePort.generateQuoteOrderDocument(quoteOrderDocument);
	}

	@Override
	public ResponseModel covertQuoteOrder(Long quoteId) {
		QuoteOrder quoteOrderSaved = quotePersistencePort.viewQouteOrderById(quoteId);
		if (quoteOrderSaved == null)
			throw new ValidationError("Registro no encontrado");

		String validations = quoteOrderValidator.validOperativeDataToConvert(quoteOrderSaved, productPersistencePort);
		if (!validations.isEmpty())
			throw new ValidationError(validations);

		RemissionOutput remissionOutput = quoteBusinessConvert.generateRemissionOutPut(quoteOrderSaved,
				remissionOutputPersistencePort, stringUtil);

		RemissionOutput remissionOutputCreated = remissionOutputPersistencePort.saveRemissionOutput(remissionOutput);

		if (remissionOutputCreated == null)
			throw new InternalError();

		quotePersistencePort.changeQuoteEffectiveStatusById(quoteId, false);
		quotePersistencePort.changeQuoteConvertStatusById(quoteId, true);
		updateProductAvailabilityByCreate(remissionOutput);

		return new ResponseModel(remissionOutputCreated);
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
	public ResponseModel viewProductActiveList() {
		List<Product> productOutputList = productPersistencePort.viewALLProduct();
		if (productOutputList == null) {
			log.warning("EMPTY DATA");
			return new ResponseModel(new ArrayList<>());
		}
		return new ResponseModel(productOutputList);
	}

}
