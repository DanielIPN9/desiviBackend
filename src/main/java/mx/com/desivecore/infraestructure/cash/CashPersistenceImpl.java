package mx.com.desivecore.infraestructure.cash;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.domain.branches.models.BranchSummary;
import mx.com.desivecore.domain.cash.models.AccountingType;
import mx.com.desivecore.domain.cash.models.CashSearchParams;
import mx.com.desivecore.domain.cash.models.ClosingCash;
import mx.com.desivecore.domain.cash.models.ClosingDetail;
import mx.com.desivecore.domain.cash.models.EntryMovementRecord;
import mx.com.desivecore.domain.cash.models.ExitMovementRecord;
import mx.com.desivecore.domain.cash.models.OpeningCash;
import mx.com.desivecore.domain.cash.models.OpeningCashSummary;
import mx.com.desivecore.domain.cash.models.OperationCashDetail;
import mx.com.desivecore.domain.cash.models.PaymentMovementRecord;
import mx.com.desivecore.domain.cash.ports.CashPersistencePort;
import mx.com.desivecore.infraestructure.branches.converters.BranchConverter;
import mx.com.desivecore.infraestructure.branches.entities.BranchEntity;
import mx.com.desivecore.infraestructure.branches.repositories.BranchReposirory;
import mx.com.desivecore.infraestructure.cash.converters.CashConverter;
import mx.com.desivecore.infraestructure.cash.converters.MovementConverter;
import mx.com.desivecore.infraestructure.cash.entities.AccountingTypeEntity;
import mx.com.desivecore.infraestructure.cash.entities.ClosingCashEntity;
import mx.com.desivecore.infraestructure.cash.entities.ClosingDetailEntity;
import mx.com.desivecore.infraestructure.cash.entities.EntryMovementRecordEntity;
import mx.com.desivecore.infraestructure.cash.entities.ExitMovementRecordEntity;
import mx.com.desivecore.infraestructure.cash.entities.OpeningCashEntity;
import mx.com.desivecore.infraestructure.cash.repositories.AccountingTypeRepository;
import mx.com.desivecore.infraestructure.cash.repositories.ClosingCasgRepository;
import mx.com.desivecore.infraestructure.cash.repositories.ClosingDetailRepository;
import mx.com.desivecore.infraestructure.cash.repositories.CustomDSLCashRepository;
import mx.com.desivecore.infraestructure.cash.repositories.EntryMovementRecordRepository;
import mx.com.desivecore.infraestructure.cash.repositories.ExitMovementRecordRepository;
import mx.com.desivecore.infraestructure.cash.repositories.OpeningCashRepository;

@Log
@Service
public class CashPersistenceImpl implements CashPersistencePort {

	@Autowired
	private BranchReposirory branchReposirory;

	@Autowired
	private BranchConverter branchConverter;

	@Autowired
	private CashConverter cashConverter;

	@Autowired
	private MovementConverter movementConverter;

	@Autowired
	private OpeningCashRepository openingCashRepository;

	@Autowired
	private AccountingTypeRepository accountingTypeRepository;

	@Autowired
	private EntryMovementRecordRepository entryMovementRecordRepository;

	@Autowired
	private ExitMovementRecordRepository exitMovementRecordRepository;

	@Autowired
	private ClosingCasgRepository closingCasgRepository;

	@Autowired
	private ClosingDetailRepository closingDetailRepository;

	@Autowired
	private CustomDSLCashRepository customDSLCashRepository;

	@Override
	public OpeningCashSummary findLastOpeningCashByUserId(Long userId) {
		try {
			log.info("INIT findLastOpeningCashByUserId()");
			Optional<OpeningCashEntity> openingCashOptional = openingCashRepository.findByActiveAndUserId(true, userId);
			OpeningCashSummary openingCashSummary = null;
			if (openingCashOptional.isPresent()) {
				OpeningCash openingCash = cashConverter.entityToOpeningCash(openingCashOptional.get());
				Optional<BranchEntity> branchOptional = branchReposirory
						.findById(openingCashOptional.get().getBranchId());
				branchOptional.ifPresent(branchEntity -> {
					Branch branch = branchConverter.branchEntityToBranch(branchEntity);
					openingCash.setBranch(new BranchSummary(branch.getBranchId(), branch.getName()));
				});
				openingCashSummary = new OpeningCashSummary(openingCash);
			}
			return openingCashSummary;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public Boolean createOpeningCash(OpeningCash openingCash) {
		try {
			log.info("INIT createOpeningCash()");
			OpeningCashEntity openingCashEntity = cashConverter.openingCashToEntity(openingCash);
			openingCashRepository.save(openingCashEntity);
			return true;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return false;
		}
	}

	@Override
	public Boolean createEntryMovementCash(EntryMovementRecord entryMovementRecord) {
		try {
			log.info("INIT createEntryMovementCash()");
			EntryMovementRecordEntity entryMovementRecordEntity = movementConverter
					.entryMovementRecordToEntity(entryMovementRecord);
			entryMovementRecordRepository.save(entryMovementRecordEntity);
			return true;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return false;
		}
	}

	@Override
	public Boolean createExitMovementCash(ExitMovementRecord exitMovementRecord) {
		try {
			log.info("INIT createExitMovementCash()");
			ExitMovementRecordEntity exitMovementRecordEntity = movementConverter
					.exitMovementRecordToEntity(exitMovementRecord);
			exitMovementRecordRepository.save(exitMovementRecordEntity);
			return true;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return false;
		}
	}

	@Override
	public Boolean createPaymentMovementCash(PaymentMovementRecord paymentMovementRecord) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long createClosingCash(ClosingCash closingCash) {
		try {
			log.info("INIT createClosingCash()");
			ClosingCashEntity closingCashEntity = cashConverter.closingCashToEntity(closingCash);
			closingCashEntity = closingCasgRepository.save(closingCashEntity);
			Long closingCashId = closingCashEntity.getClosingCashId();

			List<ClosingDetailEntity> closingDetailEntityList = closingCash.getClosingDetail().stream()
					.map(closingDetail -> cashConverter.closingDetailToEntity(closingDetail, closingCashId))
					.collect(Collectors.toList());
			closingDetailRepository.saveAll(closingDetailEntityList);

			return closingCashId;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public Boolean closeOpeningOperationByClosingAndId(Long openingCashId, Boolean state, Long closingId) {
		try {
			log.info("INIT closeOpeningOperationById()");
			openingCashRepository.changeOpeningActiveAndClosingIdStateById(state, openingCashId, closingId);
			return true;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return false;
		}
	}

	@Override
	public List<OpeningCashSummary> viewOpeningCashByParams(CashSearchParams cashSearchParams) {
		try {
			log.info("INIT viewOpeningCashByParams()");
			return customDSLCashRepository.searchOpeningCashByParams(cashSearchParams);
		} catch (Exception e) {
			log.severe("EXCEPTION" + e.getMessage());
			return null;
		}
	}

	@Override
	public OperationCashDetail viewMovementOpeningCashById(Long openingCashId) {
		try {
			log.info("INIT viewMovementOpeningCashById()");
			Optional<OpeningCashEntity> openingCashOptional = openingCashRepository.findById(openingCashId);
			if (openingCashOptional.isPresent()) {

				log.info("CONVERT OPENING");
				OpeningCash openingCash = cashConverter.entityToOpeningCash(openingCashOptional.get());

				Optional<BranchEntity> branchOptional = branchReposirory
						.findById(openingCashOptional.get().getBranchId());
				branchOptional.ifPresent(branchEntity -> {
					Branch branch = branchConverter.branchEntityToBranch(branchEntity);
					openingCash.setBranch(new BranchSummary(branch.getBranchId(), branch.getName()));
				});

				OperationCashDetail operationCashDetail = new OperationCashDetail(openingCash);

				List<AccountingTypeEntity> accountingTypeEntities = accountingTypeRepository.findAll();
				List<AccountingType> accountingTypeList = accountingTypeEntities.stream()
						.map(accountingTypeEntity -> cashConverter.entityToAccountingType(accountingTypeEntity))
						.collect(Collectors.toList());

				log.info("CONVERT ENTRIES");
				List<EntryMovementRecordEntity> entryMovementRecordEntityList = entryMovementRecordRepository
						.findAllByOpeningId(openingCashId);
				List<EntryMovementRecord> entryMovementList = entryMovementRecordEntityList
						.stream().map(entryMovementEntity -> movementConverter
								.entityToEntryMovementRecord(entryMovementEntity, accountingTypeList))
						.collect(Collectors.toList());
				operationCashDetail.setEntryMovementList(entryMovementList);

				log.info("CONVERT EXITS");
				List<ExitMovementRecordEntity> exitMovementRecordEntityList = exitMovementRecordRepository
						.findAllByOpeningId(openingCashId);
				List<ExitMovementRecord> exitMovementList = exitMovementRecordEntityList
						.stream().map(exitMovementEntity -> movementConverter
								.entityToExitMovementRecord(exitMovementEntity, accountingTypeList))
						.collect(Collectors.toList());
				operationCashDetail.setExitMovementList(exitMovementList);

				log.info("CONVERT PAYMENTS");
				List<PaymentMovementRecord> paymentMovementList = new ArrayList<>();
				operationCashDetail.setPaymentMovementList(paymentMovementList);

				log.info("CONVERT CLOSING");
				if (openingCash.getClosingCashId() != null) {
					Optional<ClosingCashEntity> closingCashOptional = closingCasgRepository
							.findById(openingCash.getClosingCashId());
					ClosingCash closingCash = null;
					if (closingCashOptional.isPresent()) {
						closingCash = cashConverter.entityToClosingCash(closingCashOptional.get());
						List<ClosingDetailEntity> closingDetailEntityList = closingDetailRepository
								.findAllByClosingId(closingCash.getClosingCashId());
						List<ClosingDetail> closingDetaiList = closingDetailEntityList
								.stream().map(closingDetailEntity -> cashConverter
										.entityToClosingDetail(closingDetailEntity, accountingTypeList))
								.collect(Collectors.toList());
						closingCash.setClosingDetail(closingDetaiList);
					}

					operationCashDetail.setClosingCash(closingCash);
				}
				return operationCashDetail;

			}
			return null;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}

	@Override
	public List<AccountingType> viewAllAccountingTypeList() {
		try {
			log.info("INIT viewAllAccountingTypeList()");
			List<AccountingTypeEntity> accountingTypeEntities = accountingTypeRepository.findAll();
			List<AccountingType> accountingTypeList = accountingTypeEntities.stream()
					.map(accountingTypeEntity -> cashConverter.entityToAccountingType(accountingTypeEntity))
					.collect(Collectors.toList());
			return accountingTypeList;
		} catch (Exception e) {
			log.severe("EXCEPTION: " + e.getMessage());
			return null;
		}
	}
}