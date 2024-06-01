package mx.com.desivecore.infraestructure.quarantine;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.quarantine.models.ProductQuarantine;
import mx.com.desivecore.domain.quarantine.models.ProductQuarantineSummary;
import mx.com.desivecore.domain.quarantine.models.QuarantineAction;
import mx.com.desivecore.domain.quarantine.models.QuarantineSearchParams;
import mx.com.desivecore.domain.quarantine.ports.QuarantinePersistencePort;
import mx.com.desivecore.infraestructure.branches.entities.BranchEntity;
import mx.com.desivecore.infraestructure.branches.repositories.BranchReposirory;
import mx.com.desivecore.infraestructure.products.entities.ProductEntity;
import mx.com.desivecore.infraestructure.products.repositories.ProductRepository;
import mx.com.desivecore.infraestructure.quarantine.converters.ProductQuarantineConverter;
import mx.com.desivecore.infraestructure.quarantine.converters.QuarantineActionConverter;
import mx.com.desivecore.infraestructure.quarantine.entities.ProductQuarantineEntity;
import mx.com.desivecore.infraestructure.quarantine.entities.QuarantineActionEntity;
import mx.com.desivecore.infraestructure.quarantine.repositories.CustomDSLProductQuarantineRepository;
import mx.com.desivecore.infraestructure.quarantine.repositories.ProductQuarantineRepository;
import mx.com.desivecore.infraestructure.quarantine.repositories.QuarantineActionRepository;

@Log
@Service
public class QuarantinePersistenceImpl implements QuarantinePersistencePort {

	@Autowired
	private ProductQuarantineRepository productQuarantineRepository;

	@Autowired
	private ProductQuarantineConverter productQuarantineConverter;

	@Autowired
	private QuarantineActionRepository quarantineActionRepository;

	@Autowired
	private QuarantineActionConverter quarantineActionConverter;

	@Autowired
	private BranchReposirory branchReposirory;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CustomDSLProductQuarantineRepository customDSLProductQuarantineRepository;
	@Override
	public List<ProductQuarantineSummary> viewQuarantineStatusByParams(QuarantineSearchParams quarantineSearchParams) {
		try {
			log.info("INIT viewQuarantineStatusByParams()");
			return customDSLProductQuarantineRepository.seachQuarantineStatusByParams(quarantineSearchParams);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public ProductQuarantineSummary viewProductQuarantineDetailByQuarantineId(Long quarantineId) {
		try {
			log.info("INIT viewProductQuarantineDetailByQuarantineId()");
			return productQuarantineRepository.findById(quarantineId).flatMap(productQuarantineEntity -> {
				Optional<BranchEntity> branchEntity = branchReposirory.findById(productQuarantineEntity.getBranchId());
				Optional<ProductEntity> productOptional = productRepository
						.findById(productQuarantineEntity.getProductId());
				return branchEntity.flatMap(branch -> productOptional
						.map(product -> new ProductQuarantineSummary(productQuarantineEntity.getProductQuarantineId(),
								branch.getName(), product.getName(), productQuarantineEntity.getProductId(),
								productQuarantineEntity.getAmount())));
			}).orElse(null);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public ProductQuarantine viewProductQuarantineDetailByProductIdAndBranchId(Long productId, Long branchId) {
		try {
			log.info("INIT viewProductQuarantineDetailByProductIdAndBranchId()");
			return productQuarantineRepository.frindByBranchIdAndProductId(branchId, productId)
					.map(productQuarantineConverter::entityToProductQuarantine).orElse(null);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public Boolean updateProductQuarantine(ProductQuarantine productQuarantine) {
		try {
			log.info("INIT updateProductQuarantine()");
			ProductQuarantineEntity productQuarantineEntity = productQuarantineConverter
					.productQuarantineToEntity(productQuarantine);
			productQuarantineEntity = productQuarantineRepository.save(productQuarantineEntity);
			return true;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return false;
		}
	}

	@Transactional
	@Override
	public Boolean updateProductQuarantineList(List<ProductQuarantine> productQuarantineList) {
		try {
			log.info("INIT updateProductQuarantineList()");
			List<ProductQuarantineEntity> productQuarantineEntityList = productQuarantineConverter
					.productQuarantineListToEntityList(productQuarantineList);
			productQuarantineRepository.saveAll(productQuarantineEntityList);
			return true;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return false;
		}

	}

	@Override
	public List<ProductQuarantineSummary> viewAllQuarantineStatus() {
		try {
			log.info("INIT ProductQuarantineSummary()");
			QuarantineSearchParams quarantineSearchParams = new QuarantineSearchParams();
			return customDSLProductQuarantineRepository.seachQuarantineStatusByParams(quarantineSearchParams);
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<QuarantineAction> viewActionList() {
		try {
			List<QuarantineActionEntity> actionEntities = quarantineActionRepository.findAll();
			List<QuarantineAction> quarantineActionList = actionEntities.stream()
					.map(quarantineActionConverter::entityToQuarantineAction).collect(Collectors.toList());
			return quarantineActionList;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

}
