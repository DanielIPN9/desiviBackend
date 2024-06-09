package mx.com.desivecore.infraestructure.returnRemissionOutput;

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
import mx.com.desivecore.domain.clients.models.Client;
import mx.com.desivecore.domain.clients.models.ClientSummary;
import mx.com.desivecore.domain.products.models.Product;
import mx.com.desivecore.domain.products.models.ProductOutputSummary;
import mx.com.desivecore.domain.returnRemissionOutput.models.ReturnProductOutput;
import mx.com.desivecore.domain.returnRemissionOutput.models.ReturnROSearchParams;
import mx.com.desivecore.domain.returnRemissionOutput.models.ReturnROSummary;
import mx.com.desivecore.domain.returnRemissionOutput.models.ReturnRemissionOutput;
import mx.com.desivecore.domain.returnRemissionOutput.ports.ReturnROPersistencePort;
import mx.com.desivecore.domain.users.models.UserModel;
import mx.com.desivecore.infraestructure.branches.converters.BranchConverter;
import mx.com.desivecore.infraestructure.branches.entities.BranchEntity;
import mx.com.desivecore.infraestructure.branches.repositories.BranchReposirory;
import mx.com.desivecore.infraestructure.clients.converters.ClientConverter;
import mx.com.desivecore.infraestructure.clients.entities.ClientEntity;
import mx.com.desivecore.infraestructure.clients.repositories.ClientRepository;
import mx.com.desivecore.infraestructure.products.converters.ProductConverter;
import mx.com.desivecore.infraestructure.products.entities.ProductEntity;
import mx.com.desivecore.infraestructure.products.repositories.ProductRepository;
import mx.com.desivecore.infraestructure.returnRemissionOutput.converters.ReturnProductOutputConverter;
import mx.com.desivecore.infraestructure.returnRemissionOutput.converters.ReturnROConverter;
import mx.com.desivecore.infraestructure.returnRemissionOutput.entities.ReturnProductOutputEntity;
import mx.com.desivecore.infraestructure.returnRemissionOutput.entities.ReturnRemissionOutputEntity;
import mx.com.desivecore.infraestructure.returnRemissionOutput.repositories.CustomDSLReturnRORepository;
import mx.com.desivecore.infraestructure.returnRemissionOutput.repositories.ReturnProductOutputRepository;
import mx.com.desivecore.infraestructure.returnRemissionOutput.repositories.ReturnRORepository;
import mx.com.desivecore.infraestructure.users.converters.UserConverter;
import mx.com.desivecore.infraestructure.users.entities.UserEntity;
import mx.com.desivecore.infraestructure.users.repositories.UserRepository;

@Log
@Service
public class ReturnROPersistenceImpl implements ReturnROPersistencePort {

	@Autowired
	private ReturnROConverter returnROConverter;

	@Autowired
	private ReturnProductOutputConverter returnProductOutputConverter;

	@Autowired
	private ReturnRORepository returnRORepository;

	@Autowired
	private ReturnProductOutputRepository returnProductOutputRepository;

	@Autowired
	private CustomDSLReturnRORepository customDSLReturnRORepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ClientConverter clientConverter;

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

	@Override
	public ReturnRemissionOutput generateReturnRemissionOutput(ReturnRemissionOutput returnRemissionOutput) {
		try {
			log.info("INIT generateReturnRemissionOutput()");
			ReturnRemissionOutputEntity returnRemissionOutputEntity = returnROConverter
					.returnRemissionOutputToEntity(returnRemissionOutput);
			returnRemissionOutputEntity = returnRORepository.save(returnRemissionOutputEntity);

			Long returnROId = returnRemissionOutputEntity.getReturnROId();

			List<ReturnProductOutputEntity> returnProductOutputEntityList = returnRemissionOutput.getProducts().stream()
					.map(returnProductOutput -> returnProductOutputConverter
							.returnProductOutputToEntity(returnProductOutput, returnROId))
					.collect(Collectors.toList());

			returnProductOutputRepository.saveAll(returnProductOutputEntityList);

			return returnROConverter.entityToReturnRemissionOutput(returnRemissionOutputEntity);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public ResponseModel generateReturnRODocument() {
		try {
			log.info("INIT generateReturnRODocument()");

			return null;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public ReturnRemissionOutput viewReturnRODetailById(Long returnROId) {
		try {
			log.info("INIT viewReturnRODetailById()");

			Optional<ReturnRemissionOutputEntity> returnROOptional = returnRORepository.findById(returnROId);

			if (returnROOptional.isPresent()) {

				ReturnRemissionOutputEntity returnRemissionOutputEntity = returnROOptional.get();
				ReturnRemissionOutput returnRemissionOutput = returnROConverter
						.entityToReturnRemissionOutput(returnRemissionOutputEntity);

				Optional<BranchEntity> branchOptional = branchReposirory
						.findById(returnRemissionOutputEntity.getBranchId());

				branchOptional.ifPresent(branchEntity -> {
					Branch branch = branchConverter.branchEntityToBranch(branchEntity);
					returnRemissionOutput.setBranch(branch);
				});

				Optional<ClientEntity> clientOptional = clientRepository
						.findById(returnRemissionOutputEntity.getClientId());

				clientOptional.ifPresent(clientEntity -> {
					Client client = clientConverter.clientEntityToClient(clientEntity);
					ClientSummary clientSummary = new ClientSummary(client.getClientId(), client.getBusinessName());
					returnRemissionOutput.setClient(clientSummary);
				});

				Optional<UserEntity> userOptional = userRepository.findById(returnRemissionOutputEntity.getUserId());

				userOptional.ifPresent(userEntity -> {
					UserModel user = userConverter.userEntityToUser(userEntity, false);
					returnRemissionOutput.setUser(user);
				});

				List<ReturnProductOutputEntity> returnProductOutputEntityList = returnProductOutputRepository
						.findListByReturnROId(returnROId);
				List<ReturnProductOutput> returnProductOutputList = new ArrayList<>();

				List<Long> productIds = returnProductOutputEntityList.stream()
						.map(ReturnProductOutputEntity::getProductId).collect(Collectors.toList());
				List<ProductEntity> productList = productRepository.findAllById(productIds);

				Map<Long, Product> productMap = productList.stream().map(productConverter::productEntityToProduct)
						.collect(Collectors.toMap(Product::getProductId, Function.identity()));

				for (ReturnProductOutputEntity returnProductOutputEntity : returnProductOutputEntityList) {
					Product product = productMap.get(returnProductOutputEntity.getProductId());
					ReturnProductOutput returnProductOutput = returnProductOutputConverter
							.entityToReturnProductOutput(returnProductOutputEntity);
					ProductOutputSummary productOutputSummary = new ProductOutputSummary(product.getProductId(),
							product.getSku(), product.getName(), null, product.getUnitSellingPrice(), product.getIva(),
							product.getUnitMeasure());
					returnProductOutput.setProduct(productOutputSummary);
					returnProductOutputList.add(returnProductOutput);
				}
				returnRemissionOutput.setProducts(returnProductOutputList);
				return returnRemissionOutput;
			}

			return null;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<ReturnROSummary> viewAllReturnRemissionOutput() {
		try {
			log.info("INIT viewAllReturnRemissionOutput()");
			List<ReturnROSummary> returnROSummaryList = returnRORepository.findAll().stream()
					.map(returnROEntity -> new ReturnROSummary(returnROEntity.getReturnROId(),
							returnROEntity.getFolio(), returnROEntity.getCreationDate(), returnROEntity.getTotal()))
					.collect(Collectors.toList());
			return returnROSummaryList;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<ReturnROSummary> searchReturnRemissionOutputByParams(ReturnROSearchParams returnROSearchParams) {
		try {
			log.info("INIT searchReturnRemissionOutputByParams()");
			return customDSLReturnRORepository.searchReturnROSummaryByParams(returnROSearchParams);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<ReturnProductOutput> searchReturnProductByFolioRO(String folio) {
		try {
			log.info("INIT searchReturnProductByFolioRO()");

			List<ReturnProductOutput> productOutputList = new ArrayList<>();

			List<Long> remissionOutputIdList = returnRORepository.findIdsByFolio(folio);
			List<ReturnProductOutputEntity> returnProductOutputEntityList = returnProductOutputRepository
					.findAllById(remissionOutputIdList);

			List<Long> productIds = returnProductOutputEntityList.stream().map(ReturnProductOutputEntity::getProductId)
					.collect(Collectors.toList());
			List<ProductEntity> productList = productRepository.findAllById(productIds);

			Map<Long, Product> productMap = productList.stream().map(productConverter::productEntityToProduct)
					.collect(Collectors.toMap(Product::getProductId, Function.identity()));

			for (ReturnProductOutputEntity returnProductOutputEntity : returnProductOutputEntityList) {
				Product product = productMap.get(returnProductOutputEntity.getProductId());
				ProductOutputSummary productOutputSummary = new ProductOutputSummary(product.getProductId(),
						product.getSku(), product.getName(), null, product.getUnitSellingPrice(), product.getIva(),
						product.getUnitMeasure());
				ReturnProductOutput returnProductOutput = returnProductOutputConverter
						.entityToReturnProductOutput(returnProductOutputEntity);
				returnProductOutput.setProduct(productOutputSummary);
				productOutputList.add(returnProductOutput);
			}

			return productOutputList;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}
}