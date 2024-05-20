package mx.com.desivecore.infraestructure.quote;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.clients.models.ClientSummary;
import mx.com.desivecore.domain.quote.models.QuoteOrder;
import mx.com.desivecore.domain.quote.models.QuoteOrderSummary;
import mx.com.desivecore.domain.quote.models.QuoteProduct;
import mx.com.desivecore.domain.quote.models.QuoteSearchParams;
import mx.com.desivecore.domain.quote.models.document.QuoteOrderDocument;
import mx.com.desivecore.domain.quote.ports.QuotePersistencePort;
import mx.com.desivecore.infraestructure.branches.converters.BranchConverter;
import mx.com.desivecore.infraestructure.branches.repositories.BranchReposirory;
import mx.com.desivecore.infraestructure.clients.repositories.ClientRepository;
import mx.com.desivecore.infraestructure.quote.converters.QuoteOrderConverter;
import mx.com.desivecore.infraestructure.quote.converters.QuoteProductConverter;
import mx.com.desivecore.infraestructure.quote.entities.QuoteOrderEntity;
import mx.com.desivecore.infraestructure.quote.entities.QuoteProductEntity;
import mx.com.desivecore.infraestructure.quote.repositories.CustomDSLQuoteRepository;
import mx.com.desivecore.infraestructure.quote.repositories.QuoteOrderRepository;
import mx.com.desivecore.infraestructure.quote.repositories.QuoteProductRepository;
import mx.com.desivecore.infraestructure.remissionEntry.entities.SerialNumberEntity;
import mx.com.desivecore.infraestructure.remissionEntry.repositories.SerialNumberRepository;
import mx.com.desivecore.infraestructure.users.converters.UserConverter;
import mx.com.desivecore.infraestructure.users.repositories.UserRepository;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Log
@Service
public class QuotePersistenceImpl implements QuotePersistencePort {

	private static final String QO_TEMPLATE = "/quote/QuoteDocument.jasper";
	private static final String LOGO_REPORT = "/quote/logo.jpg";

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserConverter userConverter;

	@Autowired
	private BranchReposirory branchReposirory;

	@Autowired
	private BranchConverter branchConverter;

	@Autowired
	private QuoteOrderRepository quoteOrderRepository;

	@Autowired
	private QuoteProductRepository quoteProductRepository;

	@Autowired
	private QuoteOrderConverter quoteOrderConverter;

	@Autowired
	private QuoteProductConverter quoteProductConverter;

	@Autowired
	private SerialNumberRepository serialNumberRepository;

	@Autowired
	private CustomDSLQuoteRepository customDSLQuoteRepository;

	@Override
	public QuoteOrder saveQuoteOrder(QuoteOrder quoteOrder) {
		try {
			log.info("INIT saveQuoteOrder()");
			QuoteOrderEntity quoteOrderEntity = quoteOrderConverter.quoteOrderToEntity(quoteOrder);
			quoteOrderEntity = quoteOrderRepository.save(quoteOrderEntity);

			if (quoteOrder.getQuoteOrderId() != null)
				deleteProductListByQuoteOrderId(quoteOrder.getQuoteOrderId());

			for (QuoteProduct quoteProduct : quoteOrder.getProducts()) {
				QuoteProductEntity quoteProductEntity = quoteProductConverter.quoteProductToEntity(quoteProduct,
						quoteOrderEntity.getQuoteOrderId());
				quoteProductRepository.save(quoteProductEntity);
			}
			return quoteOrderConverter.entityToQuoteOrder(quoteOrderEntity);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	private void deleteProductListByQuoteOrderId(Long quoteOrderId) {
		log.info("DELETE QUOTE PRODUCT LIST");
		List<QuoteProductEntity> quoteProductEntityList = quoteProductRepository
				.findProductByQuoteOrderId(quoteOrderId);
		quoteProductRepository.deleteAll(quoteProductEntityList);

	}

	@Override
	public BigInteger getConsecutiveByCode(String code) {
		try {
			log.info("INIT getConsecutiveByCode()");
			Optional<SerialNumberEntity> serialNumOptional = serialNumberRepository.findByCode(code);
			if (!serialNumOptional.isPresent())
				return null;

			SerialNumberEntity serialNumberEntity = serialNumOptional.get();
			BigInteger serialNumber = serialNumberEntity.getNumber();
			serialNumberEntity.setNumber(serialNumber.add(new BigInteger("1")));
			serialNumberRepository.save(serialNumberEntity);

			return serialNumber;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public QuoteOrder viewQouteOrderById(Long quoteId) {
		try {
			log.info("INIT viewQouteOrderById()");
			Optional<QuoteOrderEntity> quoteOptional = quoteOrderRepository.findById(quoteId);

			if (quoteOptional.isPresent()) {

				QuoteOrderEntity quoteOrderEntity = quoteOptional.get();
				QuoteOrder quoteOrder = quoteOrderConverter.entityToQuoteOrder(quoteOrderEntity);

				userRepository.findById(quoteOrderEntity.getUserId())
						.map(userEntity -> userConverter.userEntityToUser(userEntity, true))
						.ifPresent(quoteOrder::setUser);

				branchReposirory.findById(quoteOrderEntity.getBranchId()).map(branchConverter::branchEntityToBranch)
						.ifPresent(quoteOrder::setBranch);

				clientRepository.findById(quoteOrderEntity.getClientId()).map(
						clientEntity -> new ClientSummary(clientEntity.getClientId(), clientEntity.getBusinessName()))
						.ifPresent(quoteOrder::setClient);

				List<QuoteProduct> quoteProductList = quoteProductRepository.findProductByQuoteOrderId(quoteId).stream()
						.map(quoteProductConverter::entityToQuoteProduct).collect(Collectors.toList());

				quoteOrder.setProducts(quoteProductList);

				return quoteOrder;
			}

			return null;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<QuoteOrderSummary> searchQuoteOrderByParams(QuoteSearchParams quoteSearchParams) {
		try {
			log.info("INIT searchQuoteOrderByParams()");
			return customDSLQuoteRepository.searchQuoteOrderByParams(quoteSearchParams);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public ResponseModel generateQuoteOrderDocument(QuoteOrderDocument quoteOrderDocument) {
		try {
			log.info("INIT generateQuoteOrderDocument()");

			log.info(String.format("LOAD REPORT "));
			InputStream file = this.getClass().getResourceAsStream(QO_TEMPLATE);

			InputStream logoImage = this.getClass().getResourceAsStream(LOGO_REPORT);
			quoteOrderDocument.setLogo(logoImage);

			Collection<QuoteOrderDocument> collection = Collections.singletonList(quoteOrderDocument);

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
	public boolean changeQuoteEffectiveStatusById(Long quoteId, boolean status) {
		try {
			log.info("INIT changeQuoteEffectiveStatusById()");
			quoteOrderRepository.changeEffectiveStatusById(quoteId, status);
			return true;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return false;
		}
	}

	@Override
	public boolean changeQuoteConvertStatusById(Long quoteId, boolean status) {
		try {
			log.info("INIT changeQuoteConvertStatusById()");
			quoteOrderRepository.changeQuoteStatusConverterById(quoteId, status);
			return true;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return false;
		}
	}

}
