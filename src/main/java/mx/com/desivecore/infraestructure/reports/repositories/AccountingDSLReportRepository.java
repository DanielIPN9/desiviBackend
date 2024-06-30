package mx.com.desivecore.infraestructure.reports.repositories;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.reports.models.AccountPayableSummary;
import mx.com.desivecore.domain.reports.models.AccountReceivableSummary;
import mx.com.desivecore.domain.reports.models.EntryMovementRecordSummary;
import mx.com.desivecore.domain.reports.models.ExitMovementRecordSummary;
import mx.com.desivecore.domain.reports.models.search.AccountingReportParams;
import mx.com.desivecore.infraestructure.branches.entities.QBranchEntity;
import mx.com.desivecore.infraestructure.cash.entities.QAccountingTypeEntity;
import mx.com.desivecore.infraestructure.cash.entities.QEntryMovementRecordEntity;
import mx.com.desivecore.infraestructure.cash.entities.QExitMovementRecordEntity;
import mx.com.desivecore.infraestructure.cash.entities.QOpeningCashEntity;
import mx.com.desivecore.infraestructure.payments.accountPayable.entities.QAccountPayableEntity;
import mx.com.desivecore.infraestructure.payments.accountReceivable.entities.QAccountReceivableEntity;
import mx.com.desivecore.infraestructure.remissionEntry.entities.QRemissionEntryEntity;
import mx.com.desivecore.infraestructure.remissionOutput.entities.QRemissionOutputEntity;

@Log
@Repository
public class AccountingDSLReportRepository {

	@Autowired
	EntityManager em;

	public List<EntryMovementRecordSummary> searchEntryCashMovementByParams(
			AccountingReportParams accountingReportParams) {

		JPAQuery<EntryMovementRecordSummary> query = new JPAQuery<>(em);

		QOpeningCashEntity openingCash = QOpeningCashEntity.openingCashEntity;
		QEntryMovementRecordEntity entryMovement = QEntryMovementRecordEntity.entryMovementRecordEntity;
		QBranchEntity branch = QBranchEntity.branchEntity;
		QAccountingTypeEntity accountingType = QAccountingTypeEntity.accountingTypeEntity;

		/**
		 * Start evaluation of tables
		 */
		query.from(openingCash);
		query.innerJoin(entryMovement).on(entryMovement.openingCashId.eq(openingCash.openingCashId));
		query.innerJoin(branch).on(openingCash.branchId.eq(branch.branchId));
		query.innerJoin(accountingType).on(accountingType.code.eq(entryMovement.accountingType));

		/**
		 * Start evaluation of search parameters
		 */

		query.where(openingCash.branchId.eq(accountingReportParams.getBranch().getBranchId()));

		if (accountingReportParams.getDateFrom() != null) {
			Date from = accountingReportParams.getDateFrom();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(from);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			from = calendar.getTime();
			log.info("SEARCH BY DATE FROM: " + from.toString());
			query.where(openingCash.creationDate.goe(from));

		}

		if (accountingReportParams.getDateTo() != null) {
			Date to = accountingReportParams.getDateTo();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(to);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 0);
			to = calendar.getTime();
			log.info("SEARCH BY DATE TO: " + to.toString());
			query.where(openingCash.creationDate.loe(to));

		}

		/**
		 * Select fields
		 */
		query.select(Projections.constructor(EntryMovementRecordSummary.class, branch.name, openingCash.creationDate,
				entryMovement.description, accountingType.description, entryMovement.amount));

		return query.fetch();
	}

	public List<ExitMovementRecordSummary> searchExitCashMovementByParams(
			AccountingReportParams accountingReportParams) {

		JPAQuery<ExitMovementRecordSummary> query = new JPAQuery<>(em);

		QOpeningCashEntity openingCash = QOpeningCashEntity.openingCashEntity;
		QExitMovementRecordEntity exitMovement = QExitMovementRecordEntity.exitMovementRecordEntity;
		QBranchEntity branch = QBranchEntity.branchEntity;
		QAccountingTypeEntity accountingType = QAccountingTypeEntity.accountingTypeEntity;

		/**
		 * Start evaluation of tables
		 */
		query.from(openingCash);
		query.innerJoin(exitMovement).on(exitMovement.openingCashId.eq(openingCash.openingCashId));
		query.innerJoin(branch).on(openingCash.branchId.eq(branch.branchId));
		query.innerJoin(accountingType).on(accountingType.code.eq(exitMovement.accountingType));

		/**
		 * Start evaluation of search parameters
		 */

		query.where(openingCash.branchId.eq(accountingReportParams.getBranch().getBranchId()));

		if (accountingReportParams.getDateFrom() != null) {
			Date from = accountingReportParams.getDateFrom();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(from);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			from = calendar.getTime();
			log.info("SEARCH BY DATE FROM: " + from.toString());
			query.where(openingCash.creationDate.goe(from));

		}

		if (accountingReportParams.getDateTo() != null) {
			Date to = accountingReportParams.getDateTo();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(to);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 0);
			to = calendar.getTime();
			log.info("SEARCH BY DATE TO: " + to.toString());
			query.where(openingCash.creationDate.loe(to));

		}

		/**
		 * Select fields
		 */
		query.select(Projections.constructor(ExitMovementRecordSummary.class, branch.name, openingCash.creationDate,
				exitMovement.authorityUser, exitMovement.description, accountingType.description, exitMovement.amount));

		return query.fetch();
	}

	public List<AccountPayableSummary> searchAccountPayableByParams(AccountingReportParams accountingReportParams) {
		JPAQuery<AccountPayableSummary> query = new JPAQuery<>(em);

		QAccountPayableEntity accountPayable = QAccountPayableEntity.accountPayableEntity;
		QRemissionEntryEntity remissionEntry = QRemissionEntryEntity.remissionEntryEntity;
		QBranchEntity branch = QBranchEntity.branchEntity;
		QAccountingTypeEntity accountingType = QAccountingTypeEntity.accountingTypeEntity;

		/**
		 * Start evaluation of tables
		 */
		query.from(accountPayable);
		query.innerJoin(remissionEntry).on(accountPayable.remissionEntryId.eq(remissionEntry.remissionEntryId));
		query.innerJoin(branch).on(remissionEntry.branchId.eq(branch.branchId));
		query.innerJoin(accountingType).on(accountingType.code.eq(accountPayable.accountType));

		/**
		 * Start evaluation of search parameters
		 */

		query.where(remissionEntry.status.eq(true));
		query.where(remissionEntry.branchId.eq(accountingReportParams.getBranch().getBranchId()));

		if (accountingReportParams.getDateFrom() != null) {
			Date from = accountingReportParams.getDateFrom();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(from);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			from = calendar.getTime();
			log.info("SEARCH BY DATE FROM: " + from.toString());
			query.where(accountPayable.creationDate.goe(from));

		}

		if (accountingReportParams.getDateTo() != null) {
			Date to = accountingReportParams.getDateTo();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(to);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 0);
			to = calendar.getTime();
			log.info("SEARCH BY DATE TO: " + to.toString());
			query.where(accountPayable.creationDate.loe(to));

		}

		/**
		 * Select fields
		 */
		query.select(Projections.constructor(AccountPayableSummary.class, branch.name, remissionEntry.folio,
				accountPayable.creationDate, accountPayable.accountType, accountPayable.paymentAmount));

		return query.fetch();
	}

	public List<AccountReceivableSummary> searchAccountReceivableByParams(
			AccountingReportParams accountingReportParams) {

		JPAQuery<AccountReceivableSummary> query = new JPAQuery<>(em);

		QAccountReceivableEntity accountReceivable = QAccountReceivableEntity.accountReceivableEntity;
		QRemissionOutputEntity remissionOutput = QRemissionOutputEntity.remissionOutputEntity;
		QBranchEntity branch = QBranchEntity.branchEntity;
		QAccountingTypeEntity accountingType = QAccountingTypeEntity.accountingTypeEntity;

		/**
		 * Start evaluation of tables
		 */
		query.from(accountReceivable);
		query.innerJoin(remissionOutput).on(accountReceivable.remissionOutputId.eq(remissionOutput.remissionOutputId));
		query.innerJoin(branch).on(remissionOutput.branchId.eq(branch.branchId));
		query.innerJoin(accountingType).on(accountingType.code.eq(accountReceivable.accountType));

		/**
		 * Start evaluation of search parameters
		 */

		query.where(remissionOutput.status.eq(true));
		query.where(remissionOutput.branchId.eq(accountingReportParams.getBranch().getBranchId()));

		if (accountingReportParams.getDateFrom() != null) {
			Date from = accountingReportParams.getDateFrom();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(from);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			from = calendar.getTime();
			log.info("SEARCH BY DATE FROM: " + from.toString());
			query.where(accountReceivable.creationDate.goe(from));

		}

		if (accountingReportParams.getDateTo() != null) {
			Date to = accountingReportParams.getDateTo();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(to);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 0);
			to = calendar.getTime();
			log.info("SEARCH BY DATE TO: " + to.toString());
			query.where(accountReceivable.creationDate.loe(to));

		}

		/**
		 * Select fields
		 */
		query.select(Projections.constructor(AccountReceivableSummary.class, branch.name, remissionOutput.folio,
				accountReceivable.creationDate, accountReceivable.accountType, accountReceivable.paymentAmount));

		return query.fetch();

	}

}
