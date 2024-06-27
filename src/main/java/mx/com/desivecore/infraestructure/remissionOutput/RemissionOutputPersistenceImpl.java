package mx.com.desivecore.infraestructure.remissionOutput;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.clients.models.ClientSummary;
import mx.com.desivecore.domain.products.models.ProductOutputSummary;
import mx.com.desivecore.domain.remissionOutput.models.ProductOutput;
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutput;
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutputSearchParams;
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutputSummary;
import mx.com.desivecore.domain.remissionOutput.models.document.RemissionOutputDocument;
import mx.com.desivecore.domain.remissionOutput.ports.RemissionOutputPersistencePort;
import mx.com.desivecore.infraestructure.branches.converters.BranchConverter;
import mx.com.desivecore.infraestructure.branches.entities.BranchEntity;
import mx.com.desivecore.infraestructure.branches.repositories.BranchReposirory;
import mx.com.desivecore.infraestructure.clients.entities.ClientEntity;
import mx.com.desivecore.infraestructure.clients.repositories.ClientRepository;
import mx.com.desivecore.infraestructure.products.repositories.CustomDSLProductRepository;
import mx.com.desivecore.infraestructure.remissionEntry.entities.SerialNumberEntity;
import mx.com.desivecore.infraestructure.remissionEntry.repositories.SerialNumberRepository;
import mx.com.desivecore.infraestructure.remissionOutput.converters.ProductOutputConverter;
import mx.com.desivecore.infraestructure.remissionOutput.converters.RemissionOutputConverter;
import mx.com.desivecore.infraestructure.remissionOutput.entities.ProductOutputEntity;
import mx.com.desivecore.infraestructure.remissionOutput.entities.RemissionOutputEntity;
import mx.com.desivecore.infraestructure.remissionOutput.repositories.CustomDSLRemissionOutputRepository;
import mx.com.desivecore.infraestructure.remissionOutput.repositories.ProductOutputRepository;
import mx.com.desivecore.infraestructure.remissionOutput.repositories.RemissionOutputRepository;
import mx.com.desivecore.infraestructure.users.converters.UserConverter;
import mx.com.desivecore.infraestructure.users.entities.UserEntity;
import mx.com.desivecore.infraestructure.users.repositories.UserRepository;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Log
@Service
public class RemissionOutputPersistenceImpl implements RemissionOutputPersistencePort {

	private static final String RO_TEMPLATE = "/remissions/outputs/RemissionOutputDocument.jasper";
	private static final String LOGO_REPORT = "/remissions/outputs/logo.jpg";
	private static final String SING_REPORT = "/remissions/outputs/sing_ov.jpg";

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
	private RemissionOutputRepository remissionOutputRepository;

	@Autowired
	private RemissionOutputConverter remissionOutputConverter;

	@Autowired
	private ProductOutputRepository productOutputRepository;

	@Autowired
	private ProductOutputConverter productOutputConverter;

	@Autowired
	private SerialNumberRepository serialNumberRepository;

	@Autowired
	private CustomDSLProductRepository customDSLProductRepository;

	@Autowired
	private CustomDSLRemissionOutputRepository customDSLRemissionOutputRepository;

	@Override
	public RemissionOutput saveRemissionOutput(RemissionOutput remissionOutput) {
		try {
			log.info("INIT saveRemissionOutput()");
			RemissionOutputEntity remissionOutputEntity = remissionOutputConverter
					.remissionOutputToEntity(remissionOutput);
			remissionOutputEntity = remissionOutputRepository.save(remissionOutputEntity);

			for (ProductOutput productOutput : remissionOutput.getProducts()) {
				ProductOutputEntity productOutputEntity = productOutputConverter.productOutputToEntity(productOutput,
						remissionOutputEntity.getRemissionOutputId());
				productOutputEntity = productOutputRepository.save(productOutputEntity);
			}

			return remissionOutputConverter.entityToRemissionOutput(remissionOutputEntity);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public RemissionOutput updateRemissionOutput(RemissionOutput remissionOutput) {
		try {
			log.info("INIT updateRemissionOutput()");
			RemissionOutputEntity remissionOutputEntity = remissionOutputConverter
					.remissionOutputToEntity(remissionOutput);
			remissionOutputEntity = remissionOutputRepository.save(remissionOutputEntity);

			List<ProductOutputEntity> productOutputSavedList = productOutputRepository
					.findAllByRemissionId(remissionOutput.getRemissionOutputId());
			for (ProductOutputEntity productOutputEntity : productOutputSavedList) {
				productOutputRepository.deleteById(productOutputEntity.getId());
			}

			for (ProductOutput productOutput : remissionOutput.getProducts()) {
				ProductOutputEntity productOutputEntity = productOutputConverter.productOutputToEntity(productOutput,
						remissionOutputEntity.getRemissionOutputId());
				productOutputEntity = productOutputRepository.save(productOutputEntity);
			}

			return remissionOutputConverter.entityToRemissionOutput(remissionOutputEntity);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
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
	public RemissionOutput viewRemissionById(Long remissionOutputId) {
		try {
			log.info("INIT viewRemissionById()");
			Optional<RemissionOutputEntity> remissionOutputOptional = remissionOutputRepository
					.findById(remissionOutputId);
			if (!remissionOutputOptional.isPresent())
				return null;

			RemissionOutput remissionOutput = remissionOutputConverter
					.entityToRemissionOutput(remissionOutputOptional.get());

			Optional<UserEntity> userEntityOptional = userRepository
					.findById(remissionOutputOptional.get().getUserId());
			remissionOutput.setUser(userConverter.userEntityToUser(userEntityOptional.get(), true));

			Optional<BranchEntity> branchOptional = branchReposirory
					.findById(remissionOutputOptional.get().getBranchId());
			remissionOutput.setBranch(branchConverter.branchEntityToBranch(branchOptional.get()));

			Optional<ClientEntity> clientOptional = clientRepository
					.findById(remissionOutputOptional.get().getClientId());
			ClientSummary clientSummary = new ClientSummary(clientOptional.get().getClientId(),
					clientOptional.get().getBusinessName());
			remissionOutput.setClient(clientSummary);

			List<ProductOutputEntity> productOutputEntityList = productOutputRepository
					.findAllByRemissionId(remissionOutputId);

			List<ProductOutput> productOutputList = new ArrayList<>();
			for (ProductOutputEntity productOutputEntity : productOutputEntityList) {
				ProductOutput productOutput = productOutputConverter.entityToProductOutput(productOutputEntity);
				ProductOutputSummary productOutputSummary = customDSLProductRepository.findByProductIdAndBranchId(
						remissionOutputOptional.get().getBranchId(), productOutputEntity.getProductId());
				productOutput.setProduct(productOutputSummary);
				productOutputList.add(productOutput);
			}
			remissionOutput.setProducts(productOutputList);
			return remissionOutput;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public boolean cancelRemissionById(Long remissionOutputId) {
		try {
			log.info("INIT cancelRemissionById()");
			remissionOutputRepository.changeRemissionStatusById(false, remissionOutputId);
			return true;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return false;
		}
	}

	@Override
	public List<RemissionOutputSummary> searchRemissionOutputByParams(RemissionOutputSearchParams outputSearchParams) {
		try {
			log.info("INIT searchRemissionOutputByParams()");
			List<RemissionOutputSummary> remissionOutputSummaryList = customDSLRemissionOutputRepository
					.searchRemissionOutputByParams(outputSearchParams);
			return remissionOutputSummaryList;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public ResponseModel generateRemissionDocument(RemissionOutputDocument remissionOutputDocument) {
		try {
			log.info("INIT generateRemissionDocument()");

			log.info(String.format("LOAD REPORT "));
			InputStream file = this.getClass().getResourceAsStream(RO_TEMPLATE);

			InputStream logoImage = this.getClass().getResourceAsStream(LOGO_REPORT);
			remissionOutputDocument.setLogo(logoImage);

			InputStream logoSing = this.getClass().getResourceAsStream(SING_REPORT);
			remissionOutputDocument.setSing(logoSing);

			Collection<RemissionOutputDocument> collection = Collections.singletonList(remissionOutputDocument);

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
	public List<RemissionOutputSummary> searchByUserId(Long userId) {
		try {
			log.info("INIT searchByUserId()");
			List<RemissionOutputSummary> remissionOutputSummaryList = customDSLRemissionOutputRepository
					.findRemissionOutputByUserId(userId);
			return remissionOutputSummaryList;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public Boolean updateByAccountReceivable(Long remissionOutputId, Double balanceDue, String paymentStatus) {
		try {
			log.info("INIT updateByAccountReceivable()");
			remissionOutputRepository.updateBalanceAndPaymentStatusById(balanceDue, paymentStatus, remissionOutputId);
			return true;
		} catch (Exception e) {
			log.info("EXCEPTION: " + e.getMessage());
			return false;
		}
	}
}