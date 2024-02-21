package mx.com.desivecore.infraestructure.remissionEntry;

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
import mx.com.desivecore.domain.remissionEntry.models.ProductEntry;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntry;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntryDocument;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntryHistory;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntrySummary;
import mx.com.desivecore.domain.remissionEntry.models.RemissionSearchParams;
import mx.com.desivecore.domain.remissionEntry.ports.RemissionEntryPersistencePort;
import mx.com.desivecore.infraestructure.branches.converters.BranchConverter;
import mx.com.desivecore.infraestructure.branches.entities.BranchEntity;
import mx.com.desivecore.infraestructure.branches.repositories.BranchReposirory;
import mx.com.desivecore.infraestructure.configuration.exceptions.InternalError;
import mx.com.desivecore.infraestructure.products.converters.ProductConverter;
import mx.com.desivecore.infraestructure.products.entities.ProductEntity;
import mx.com.desivecore.infraestructure.products.repositories.ProductRepository;
import mx.com.desivecore.infraestructure.remissionEntry.converters.ProductEntryConverter;
import mx.com.desivecore.infraestructure.remissionEntry.converters.RemissionEntryConverter;
import mx.com.desivecore.infraestructure.remissionEntry.converters.RemissionEntryHistoryConverter;
import mx.com.desivecore.infraestructure.remissionEntry.entities.ProductEntryEntity;
import mx.com.desivecore.infraestructure.remissionEntry.entities.RemissionEntryEntity;
import mx.com.desivecore.infraestructure.remissionEntry.entities.RemissionEntryHistoryEntity;
import mx.com.desivecore.infraestructure.remissionEntry.entities.SerialNumberEntity;
import mx.com.desivecore.infraestructure.remissionEntry.repositories.CustomDSLRemissionEntryRepository;
import mx.com.desivecore.infraestructure.remissionEntry.repositories.ProductEntryRepository;
import mx.com.desivecore.infraestructure.remissionEntry.repositories.RemissionEntryHistoryRepository;
import mx.com.desivecore.infraestructure.remissionEntry.repositories.RemissionEntryRepository;
import mx.com.desivecore.infraestructure.remissionEntry.repositories.SerialNumberRepository;
import mx.com.desivecore.infraestructure.suppliers.converters.SupplierConverter;
import mx.com.desivecore.infraestructure.suppliers.entities.SupplierEntity;
import mx.com.desivecore.infraestructure.suppliers.repositories.SupplierRepository;
import mx.com.desivecore.infraestructure.users.converters.UserConverter;
import mx.com.desivecore.infraestructure.users.entities.UserEntity;
import mx.com.desivecore.infraestructure.users.repositories.UserRepository;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Log
@Service
public class RemissionEntryPersistenceImpl implements RemissionEntryPersistencePort {

	private static final String RE_TEMPLATE = "/remissions/entries/RemissionEntryDocument.jasper";
	private static final String LOGO_REPORT = "/remissions/entries/logo.jpg";
	private static final String SING_REPORT = "/remissions/entries/sing_ov.png";

	@Autowired
	private RemissionEntryConverter remissionEntryConverter;

	@Autowired
	private RemissionEntryRepository remissionEntryRepository;

	@Autowired
	private CustomDSLRemissionEntryRepository customDSLRemissionEntryRepository;

	@Autowired
	private ProductEntryConverter productEntryConverter;

	@Autowired
	private ProductEntryRepository productEntryRepository;

	@Autowired
	private RemissionEntryHistoryConverter remissionEntryHistoryConverter;

	@Autowired
	private RemissionEntryHistoryRepository remissionEntryHistoryRepository;

	@Autowired
	private SupplierRepository supplierRepository;

	@Autowired
	private SupplierConverter supplierConverter;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserConverter userConverter;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductConverter productConverter;

	@Autowired
	private BranchReposirory branchReposirory;

	@Autowired
	private BranchConverter branchConverter;

	@Autowired
	private SerialNumberRepository serialNumberRepository;

	@Override
	public RemissionEntry saveRemissionEntry(RemissionEntry remissionEntry) {
		try {
			log.info("INIT saveRemissionEntry()");
			RemissionEntryEntity remissionEntryEntity = remissionEntryConverter.remissionEntryToEntity(remissionEntry);
			remissionEntryEntity = remissionEntryRepository.save(remissionEntryEntity);

			for (ProductEntry productEntry : remissionEntry.getProducts()) {
				ProductEntryEntity productEntryEntity = productEntryConverter.productEntryToEntity(productEntry,
						remissionEntryEntity.getRemissionEntryId());
				productEntryEntity = productEntryRepository.save(productEntryEntity);
			}

			return remissionEntryConverter.entityToRemissionEntry(remissionEntryEntity);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public RemissionEntry updateRemissionEntry(RemissionEntry remissionEntry) {
		try {
			log.info("INIT updateRemissionEntry()");
			RemissionEntryEntity remissionEntryEntity = remissionEntryConverter.remissionEntryToEntity(remissionEntry);
			remissionEntryEntity = remissionEntryRepository.save(remissionEntryEntity);

			List<ProductEntryEntity> productEntrySavedList = productEntryRepository
					.findAllByRemissionId(remissionEntry.getRemissionEntryId());
			for (ProductEntryEntity productEntryEntity : productEntrySavedList) {
				productEntryRepository.deleteById(productEntryEntity.getId());
			}

			for (ProductEntry productEntry : remissionEntry.getProducts()) {
				ProductEntryEntity productEntryEntity = productEntryConverter.productEntryToEntity(productEntry,
						remissionEntryEntity.getRemissionEntryId());
				productEntryEntity = productEntryRepository.save(productEntryEntity);
			}

			return remissionEntryConverter.entityToRemissionEntry(remissionEntryEntity);
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
	public RemissionEntryHistory saveRemissionEntryHistory(RemissionEntryHistory remissionEntryHistory) {
		try {
			log.info("INIT saveRemissionEntryHistory()");
			RemissionEntryHistoryEntity remissionEntryHistoryEntity = remissionEntryHistoryConverter
					.remissionEntryHistoryToEntity(remissionEntryHistory);
			remissionEntryHistoryEntity = remissionEntryHistoryRepository.save(remissionEntryHistoryEntity);
			return remissionEntryHistoryConverter.entityToRemissionEntryHistory(remissionEntryHistoryEntity);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<RemissionEntry> searchRemissionEntryByUserId(Long userId) {
		try {
			log.info("INIT searchRemissionEntryByUserId()");
			List<RemissionEntryEntity> remissionEntryEntityList = remissionEntryRepository.findAllByUserId(userId);
			List<RemissionEntry> remissionEntryList = new ArrayList<>();
			for (RemissionEntryEntity remissionEntryEntity : remissionEntryEntityList) {
				Optional<SupplierEntity> supplierOptional = supplierRepository
						.findById(remissionEntryEntity.getSupplierId());
				Optional<BranchEntity> branchOptional = branchReposirory.findById(remissionEntryEntity.getBranchId());
				RemissionEntry remissionEntry = remissionEntryConverter.entityToRemissionEntry(remissionEntryEntity);
				remissionEntry.setSupplier(supplierConverter.supplierEntityToSupplier(supplierOptional.get()));
				remissionEntry.setBranch(branchConverter.branchEntityToBranch(branchOptional.get()));
				remissionEntryList.add(remissionEntry);
			}
			return remissionEntryList;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public RemissionEntry viewRemissionById(Long remissionEntryId) {
		try {
			log.info("INIT viewRemissionById()");
			Optional<RemissionEntryEntity> remissionEntryOptional = remissionEntryRepository.findById(remissionEntryId);
			if (!remissionEntryOptional.isPresent())
				return null;

			RemissionEntry remissionEntry = remissionEntryConverter
					.entityToRemissionEntry(remissionEntryOptional.get());

			Optional<SupplierEntity> supplierEntityOptional = supplierRepository
					.findById(remissionEntryOptional.get().getSupplierId());
			remissionEntry.setSupplier(supplierConverter.supplierEntityToSupplier(supplierEntityOptional.get()));

			Optional<UserEntity> userEntityOptional = userRepository.findById(remissionEntryOptional.get().getUserId());
			remissionEntry.setUser(userConverter.userEntityToUser(userEntityOptional.get(), true));

			Optional<BranchEntity> branchOptional = branchReposirory
					.findById(remissionEntryOptional.get().getBranchId());
			remissionEntry.setBranch(branchConverter.branchEntityToBranch(branchOptional.get()));

			List<ProductEntryEntity> productEntryEntityList = productEntryRepository
					.findAllByRemissionId(remissionEntryId);

			List<ProductEntry> productEntryList = new ArrayList<>();
			for (ProductEntryEntity productEntryEntity : productEntryEntityList) {
				ProductEntry productEntry = productEntryConverter.entityToProductEntry(productEntryEntity);
				Optional<ProductEntity> productEntityOptional = productRepository
						.findById(productEntryEntity.getProductId());
				productEntry.setProduct(productConverter.productEntityToProduct(productEntityOptional.get()));
				productEntryList.add(productEntry);
			}
			remissionEntry.setProducts(productEntryList);
			return remissionEntry;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<RemissionEntrySummary> searchRemissionEntryByParams(RemissionSearchParams remissionSearchParams) {
		try {
			log.info("INIT searchRemissionEntryByParams()");
			return customDSLRemissionEntryRepository.searchRemissionEntryByParams(remissionSearchParams);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<RemissionEntryHistory> viewRemissionHistoryById(Long remissionEntryId) {
		try {
			log.info("INIT RemissionEntryHistory()");
			List<RemissionEntryHistoryEntity> remissionEntryHistoryEntityList = remissionEntryHistoryRepository
					.findALLByRemissionEntryId(remissionEntryId);
			return remissionEntryHistoryConverter
					.entityListToRemissionEntryHistoryList(remissionEntryHistoryEntityList);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public ResponseModel generateRemissionDocument(RemissionEntryDocument remissionEntryDocument) {
		log.info("INIT generateRemissionDocument()");
		try {

			log.info(String.format("LOAD REPORT "));
			InputStream file = this.getClass().getResourceAsStream(RE_TEMPLATE);

			InputStream logoImage = this.getClass().getResourceAsStream(LOGO_REPORT);
			remissionEntryDocument.setLogo(logoImage);
			
			InputStream logoSing = this.getClass().getResourceAsStream(SING_REPORT);
			remissionEntryDocument.setSing(logoSing);
			
			Collection<RemissionEntryDocument> collection = Collections.singletonList(remissionEntryDocument);

			JasperPrint jasperPrint = JasperFillManager.fillReport(file, null,
					new JRBeanCollectionDataSource(collection));

			byte[] remissionEntryReport = JasperExportManager.exportReportToPdf(jasperPrint);

			log.info(String.format("RETURN DATA "));
			return new ResponseModel(remissionEntryReport);

		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			throw new InternalError();
		}
	}

}
