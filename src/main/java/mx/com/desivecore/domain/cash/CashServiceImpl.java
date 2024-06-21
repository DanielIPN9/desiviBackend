package mx.com.desivecore.domain.cash;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.constants.OpeningCashStatusEnum;
import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.domain.branches.models.BranchSummary;
import mx.com.desivecore.domain.branches.ports.BranchPersistencePort;
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
import mx.com.desivecore.domain.cash.ports.CashServicePort;
import mx.com.desivecore.domain.users.models.UserModel;
import mx.com.desivecore.domain.users.ports.UserPersistencePort;
import mx.com.desivecore.infraestructure.configuration.exceptions.InternalError;
import mx.com.desivecore.infraestructure.configuration.exceptions.ValidationError;

@Log
@Service
public class CashServiceImpl implements CashServicePort {

	@Autowired
	private UserPersistencePort userPersistencePort;

	@Autowired
	private CashPersistencePort cashPersistencePort;

	@Autowired
	private BranchPersistencePort branchPersistencePort;

	private CashValidator cashValidator = new CashValidator();

	private MovementValidator movementValidator = new MovementValidator();

	@Override
	public ResponseModel viewOpeningCashSummaryByUserLogged(String userEmail) {
		UserModel user = Optional.ofNullable(userPersistencePort.findUserByEmail(userEmail, true))
				.orElseThrow(() -> new ValidationError("Usuario no encontrado"));
		OpeningCashSummary openingCashSummary = cashPersistencePort.findLastOpeningCashByUserId(user.getUserId());
		if (openingCashSummary == null) {
			openingCashSummary = new OpeningCashSummary(user.getBranch().getName(), userEmail, false,
					OpeningCashStatusEnum.EMPTY.toString());
			return new ResponseModel(openingCashSummary);
		}
		return new ResponseModel(openingCashSummary);
	}

	@Override
	public ResponseModel createOpeningCash(OpeningCash openingCash, String userEmail) {

		UserModel user = Optional.ofNullable(userPersistencePort.findUserByEmail(userEmail, true))
				.orElseThrow(() -> new ValidationError("Usuario no encontrado"));
		OpeningCashSummary openingCashSummary = cashPersistencePort.findLastOpeningCashByUserId(user.getUserId());

		String validations = cashValidator.validOperativeData(openingCash, openingCashSummary);
		if (!validations.isEmpty())
			throw new ValidationError(validations);

		log.info("GENERATE OPENING CASH");
		openingCash = new OpeningCash(user, user.getBranch(), openingCash);
		Boolean createOpening = cashPersistencePort.createOpeningCash(openingCash);
		if (!createOpening)
			throw new InternalError();

		return new ResponseModel(createOpening);
	}

	@Override
	public ResponseModel createEntryMovementCash(EntryMovementRecord entryMovementRecord, String userEmail) {

		UserModel user = Optional.ofNullable(userPersistencePort.findUserByEmail(userEmail, true))
				.orElseThrow(() -> new ValidationError("Usuario no encontrado"));
		OpeningCashSummary openingCashSummary = cashPersistencePort.findLastOpeningCashByUserId(user.getUserId());

		String validations = movementValidator.validOperativeDataToEntryMovement(entryMovementRecord,
				openingCashSummary);
		if (!validations.isEmpty())
			throw new ValidationError(validations);

		entryMovementRecord.setOpeningCashId(openingCashSummary.getOpeningCashId());
		Boolean createEntry = cashPersistencePort.createEntryMovementCash(entryMovementRecord);

		return new ResponseModel(createEntry);
	}

	@Override
	public ResponseModel createExitMovementCash(ExitMovementRecord exitMovementRecord, String userEmail) {

		UserModel user = Optional.ofNullable(userPersistencePort.findUserByEmail(userEmail, true))
				.orElseThrow(() -> new ValidationError("Usuario no encontrado"));
		OpeningCashSummary openingCashSummary = cashPersistencePort.findLastOpeningCashByUserId(user.getUserId());

		String validations = movementValidator.validOperativeDataToExitMovement(exitMovementRecord, openingCashSummary);
		if (!validations.isEmpty())
			throw new ValidationError(validations);

		exitMovementRecord.setOpeningCashId(openingCashSummary.getOpeningCashId());
		Boolean createExit = cashPersistencePort.createExitMovementCash(exitMovementRecord);

		return new ResponseModel(createExit);
	}

	@Override
	public ResponseModel searchRemissionOutputSummaryByFolio(String remissionOutputFolio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseModel createPaymentMovementCash(PaymentMovementRecord paymentMovementRecord, String userEmail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseModel generateClosingSummaryByUserLogged(String userEmail) {
		UserModel user = Optional.ofNullable(userPersistencePort.findUserByEmail(userEmail, true))
				.orElseThrow(() -> new ValidationError("Usuario no encontrado"));
		List<AccountingType> accountingTypeList = Optional.ofNullable(cashPersistencePort.viewAllAccountingTypeList())
				.orElseThrow(() -> new InternalError());
		log.info("GENERATE SUMMARY");
		ClosingCash closingCash = ClosingCash.generateNewSummary(user, accountingTypeList);
		return new ResponseModel(closingCash);
	}

	@Override
	public ResponseModel createClosingCash(ClosingCash closingCash, String userEmail) {
		UserModel user = Optional.ofNullable(userPersistencePort.findUserByEmail(userEmail, true))
				.orElseThrow(() -> new ValidationError("Usuario no encontrado"));
		OpeningCashSummary openingCashSummary = cashPersistencePort.findLastOpeningCashByUserId(user.getUserId());

		String validations = cashValidator.validClosingOperativeData(closingCash, openingCashSummary);
		if (!validations.isEmpty())
			throw new ValidationError(validations);

		log.info("GENERATE FINAL SUMMARY");
		closingCash.setBranch(new BranchSummary(user.getBranch().getBranchId(), user.getBranch().getName()));
		closingCash.setUserEmail(user.getEmail());
		Double amountTotal = closingCash.getClosingDetail().stream().mapToDouble(ClosingDetail::getAmount).sum();
		closingCash.setAmountTotal(amountTotal);

		Long closingId = cashPersistencePort.createClosingCash(closingCash);
		if (closingId == null)
			throw new InternalError();

		log.info("UPDATE OPENIONG STATE");
		Boolean updateOpening = cashPersistencePort
				.closeOpeningOperationByClosingAndId(openingCashSummary.getOpeningCashId(), false, closingId);
		if (!updateOpening)
			throw new InternalError();

		return new ResponseModel(true);
	}

	@Override
	public ResponseModel viewOpeningCashByParams(CashSearchParams cashSearchParams) {
		List<OpeningCashSummary> openingCashSummaries = Optional
				.ofNullable(cashPersistencePort.viewOpeningCashByParams(cashSearchParams)).orElse(new ArrayList<>());
		return new ResponseModel(openingCashSummaries);
	}

	@Override
	public ResponseModel viewMovementOpeningCashById(Long openingCashId) {
		OperationCashDetail operationCashDetail = Optional
				.ofNullable(cashPersistencePort.viewMovementOpeningCashById(openingCashId))
				.orElseThrow(() -> new ValidationError("Registro no encontrado"));

		Double entryTotal = operationCashDetail.getEntryMovementList().stream()
				.mapToDouble(EntryMovementRecord::getAmount).sum();
		entryTotal = (double) Math.round(entryTotal * 100) / 100;

		Double exitTotal = operationCashDetail.getExitMovementList().stream().mapToDouble(ExitMovementRecord::getAmount)
				.sum();
		exitTotal = (double) Math.round(exitTotal * 100) / 100;

		Double paymentTotal = operationCashDetail.getPaymentMovementList().stream()
				.mapToDouble(PaymentMovementRecord::getAmount).sum();
		paymentTotal = (double) Math.round(paymentTotal * 100) / 100;

		Double cashTotal = entryTotal + paymentTotal - exitTotal;
		cashTotal = (double) Math.round(cashTotal * 100) / 100;

		operationCashDetail.setAmountEntryTotal(entryTotal);
		operationCashDetail.setAmountExitTotal(exitTotal);
		operationCashDetail.setAmountPaymentTotal(paymentTotal);

		operationCashDetail.setAmountTotal(cashTotal);

		return new ResponseModel(operationCashDetail);
	}

	@Override
	public ResponseModel viewAllBranchActive() {
		List<Branch> branchList = branchPersistencePort.findAllBranch();
		if (branchList == null) {
			log.warning("EMPTY DATA");
			return new ResponseModel(new ArrayList<>());
		}
		List<BranchSummary> branchSummaryList = branchList.stream()
				.map(branch -> new BranchSummary(branch.getBranchId(), branch.getName())).collect(Collectors.toList());
		return new ResponseModel(branchSummaryList);
	}

	@Override
	public ResponseModel viewAllAccountingType() {
		List<AccountingType> accountingTypeList = cashPersistencePort.viewAllAccountingTypeList();
		if (accountingTypeList == null)
			return new ResponseModel(new ArrayList<>());
		return new ResponseModel(accountingTypeList);
	}

}
