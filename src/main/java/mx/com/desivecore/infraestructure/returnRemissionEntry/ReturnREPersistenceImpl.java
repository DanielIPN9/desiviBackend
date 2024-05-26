package mx.com.desivecore.infraestructure.returnRemissionEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.domain.products.models.Product;
import mx.com.desivecore.domain.returnRemissionEntry.models.ReturnProductEntry;
import mx.com.desivecore.domain.returnRemissionEntry.models.ReturnRESearchParams;
import mx.com.desivecore.domain.returnRemissionEntry.models.ReturnRESummary;
import mx.com.desivecore.domain.returnRemissionEntry.models.ReturnRemissionEntry;
import mx.com.desivecore.domain.returnRemissionEntry.ports.ReturnREPersistencePort;
import mx.com.desivecore.domain.suppliers.models.Supplier;
import mx.com.desivecore.domain.users.models.UserModel;
import mx.com.desivecore.infraestructure.branches.converters.BranchConverter;
import mx.com.desivecore.infraestructure.branches.entities.BranchEntity;
import mx.com.desivecore.infraestructure.branches.repositories.BranchReposirory;
import mx.com.desivecore.infraestructure.products.converters.ProductConverter;
import mx.com.desivecore.infraestructure.products.entities.ProductEntity;
import mx.com.desivecore.infraestructure.products.repositories.ProductRepository;
import mx.com.desivecore.infraestructure.returnRemissionEntry.converters.ReturnProductEntryConverter;
import mx.com.desivecore.infraestructure.returnRemissionEntry.converters.ReturnREConverter;
import mx.com.desivecore.infraestructure.returnRemissionEntry.entities.ReturnProductEntryEntity;
import mx.com.desivecore.infraestructure.returnRemissionEntry.entities.ReturnRemissionEntryEntity;
import mx.com.desivecore.infraestructure.returnRemissionEntry.repositories.CustomDSLReturnRERepository;
import mx.com.desivecore.infraestructure.returnRemissionEntry.repositories.ReturnProductEntryRepository;
import mx.com.desivecore.infraestructure.returnRemissionEntry.repositories.ReturnRERepository;
import mx.com.desivecore.infraestructure.suppliers.converters.SupplierConverter;
import mx.com.desivecore.infraestructure.suppliers.entities.SupplierEntity;
import mx.com.desivecore.infraestructure.suppliers.repositories.SupplierRepository;
import mx.com.desivecore.infraestructure.users.converters.UserConverter;
import mx.com.desivecore.infraestructure.users.entities.UserEntity;
import mx.com.desivecore.infraestructure.users.repositories.UserRepository;

@Log
@Service
public class ReturnREPersistenceImpl implements ReturnREPersistencePort {

	@Autowired
	private ReturnRERepository returnRERepository;

	@Autowired
	private ReturnProductEntryRepository returnProductEntryRepository;

	@Autowired
	private ReturnREConverter returnREConverter;

	@Autowired
	private ReturnProductEntryConverter returnProductEntryConverter;

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
	private CustomDSLReturnRERepository customDSLReturnRERepository;
	@Override
	public ReturnRemissionEntry generateReturnRemissionEntry(ReturnRemissionEntry returnRemissionEntry) {
		try {
			log.info("INIT generateReturnRemissionEntry()");

			ReturnRemissionEntryEntity returnRemissionEntryEntity = returnREConverter
					.returnRemissionEntryToEntity(returnRemissionEntry);
			returnRemissionEntryEntity = returnRERepository.save(returnRemissionEntryEntity);

			Long returnREId = returnRemissionEntryEntity.getReturnREId();

			List<ReturnProductEntryEntity> returnProductEntryList = returnRemissionEntry.getProducts().stream().map(
					returnProduct -> returnProductEntryConverter.returnProductEntryToEntity(returnProduct, returnREId))
					.collect(Collectors.toList());
			returnProductEntryRepository.saveAll(returnProductEntryList);

			return returnREConverter.entityToReturnRemissionEntry(returnRemissionEntryEntity);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public ResponseModel generateReturnREDocumentById() {
		try {
			log.info("INIT generateReturnREDocumentById()");

			return null;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<ReturnRESummary> viewAllReturnRemissionEntry() {
		try {
			log.info("INIT viewAllReturnRemissionEntry()");

			List<ReturnRESummary> returnRESummaryList = returnRERepository.findAll().stream()
					.map(returnRemissionEntryEntity -> new ReturnRESummary(returnRemissionEntryEntity.getReturnREId(),
							returnRemissionEntryEntity.getFolio(), returnRemissionEntryEntity.getCreationDate(),
							returnRemissionEntryEntity.getTotal()))
					.collect(Collectors.toList());

			return returnRESummaryList;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<ReturnRESummary> searchReturnRemissionEntryByParams(ReturnRESearchParams returnRESearchParams) {
		try {
			log.info("INIT searchReturnRemissionEntryByParams()");
			return customDSLReturnRERepository.seachReturnRESummaryByParams(returnRESearchParams);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public ReturnRemissionEntry viewReturnREDetailById(Long returnREId) {
		try {
			log.info("INIT viewReturnREDetailById()");

			Optional<ReturnRemissionEntryEntity> returnRemissionEntryOptional = returnRERepository.findById(returnREId);

			if (returnRemissionEntryOptional.isPresent()) {

				ReturnRemissionEntryEntity returnRemissionEntryEntity = returnRemissionEntryOptional.get();
				ReturnRemissionEntry returnRemissionEntry = returnREConverter
						.entityToReturnRemissionEntry(returnRemissionEntryEntity);

				Optional<BranchEntity> branchOptional = branchReposirory
						.findById(returnRemissionEntryEntity.getBranchId());

				branchOptional.ifPresent(branchEntity -> {
					Branch branch = branchConverter.branchEntityToBranch(branchEntity);
					returnRemissionEntry.setBranch(branch);
				});

				Optional<SupplierEntity> supplierOptional = supplierRepository
						.findById(returnRemissionEntryEntity.getSupplierId());

				supplierOptional.ifPresent(supplierEntity -> {
					Supplier supplier = supplierConverter.supplierEntityToSupplier(supplierEntity);
					returnRemissionEntry.setSupplier(supplier);
				});

				Optional<UserEntity> userOptional = userRepository.findById(returnRemissionEntryEntity.getUserId());

				userOptional.ifPresent(userEntity -> {
					UserModel user = userConverter.userEntityToUser(userEntity, false);
					returnRemissionEntry.setUser(user);
				});

				List<ReturnProductEntryEntity> returnProductEntryList = returnProductEntryRepository
						.findListByreturnREId(returnREId);
				List<ReturnProductEntry> productEntryList = new ArrayList<>();

				List<Long> productIds = returnProductEntryList.stream().map(ReturnProductEntryEntity::getProductId)
						.collect(Collectors.toList());
				List<ProductEntity> productList = productRepository.findAllById(productIds);

				Map<Long, Product> productMap = productList.stream().map(productConverter::productEntityToProduct)
						.collect(Collectors.toMap(Product::getProductId, Function.identity()));

				for (ReturnProductEntryEntity returnProductEntryEntity : returnProductEntryList) {
					Product product = productMap.get(returnProductEntryEntity.getProductId());
					ReturnProductEntry returnProductEntry = returnProductEntryConverter
							.entityToReturnProductEntry(returnProductEntryEntity);
					returnProductEntry.setProduct(product);
					productEntryList.add(returnProductEntry);
				}

				returnRemissionEntry.setProducts(productEntryList);

				return returnRemissionEntry;
			}
			return null;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<ReturnProductEntry> searchByFolioRE(String remissionEntryFolio) {
		try {
			log.info("INIT searchByFolioRE()");

			List<ReturnProductEntry> productEntryList = new ArrayList<>();

			List<Long> remissionEntryIdList = returnRERepository.findIdsByFolio(remissionEntryFolio);
			List<ReturnProductEntryEntity> returnProductEntryEntityList = returnProductEntryRepository
					.findAllById(remissionEntryIdList);

			List<Long> productIds = returnProductEntryEntityList.stream().map(ReturnProductEntryEntity::getProductId)
					.collect(Collectors.toList());
			List<ProductEntity> productList = productRepository.findAllById(productIds);

			Map<Long, Product> productMap = productList.stream().map(productConverter::productEntityToProduct)
					.collect(Collectors.toMap(Product::getProductId, Function.identity()));

			for (ReturnProductEntryEntity returnProductEntryEntity : returnProductEntryEntityList) {
				Product product = productMap.get(returnProductEntryEntity.getProductId());
				ReturnProductEntry returnProductEntry = returnProductEntryConverter
						.entityToReturnProductEntry(returnProductEntryEntity);
				returnProductEntry.setProduct(product);
				productEntryList.add(returnProductEntry);
			}

			return productEntryList;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

}
