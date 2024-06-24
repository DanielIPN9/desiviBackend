package mx.com.desivecore.infraestructure.payments.accountPayable.repositories;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.payments.accountPayable.models.RemissionEntryBalance;
import mx.com.desivecore.domain.payments.accountPayable.models.RemissionEntryBalanceSearch;
import mx.com.desivecore.infraestructure.remissionEntry.entities.QRemissionEntryEntity;
import mx.com.desivecore.infraestructure.suppliers.entities.QSupplierEntity;

@Log
@Repository
public class CustomDSLAccountingPayableRepository extends QuerydslRepositorySupport {

	public CustomDSLAccountingPayableRepository() {
		super(RemissionEntryBalance.class);
	}

	@Autowired
	EntityManager em;

	public List<RemissionEntryBalance> searchREBalanceByParams(
			RemissionEntryBalanceSearch remissionEntryBalanceSearch) {
		log.info("searchREBalanceByParams()");

		JPAQuery<RemissionEntryBalance> query = new JPAQuery<>(em);

		QRemissionEntryEntity remissionEntry = QRemissionEntryEntity.remissionEntryEntity;
		QSupplierEntity supplier = QSupplierEntity.supplierEntity;

		/**
		 * Start evaluation of tables
		 */
		query.from(remissionEntry);
		query.innerJoin(supplier).on(remissionEntry.supplierId.eq(supplier.supplierId));

		/**
		 * Start evaluation of search parameters
		 */
		query.where(remissionEntry.status.eq(true));

		if (remissionEntryBalanceSearch.getPaymentState() != null) {
			if (remissionEntryBalanceSearch.getPaymentState().getCode() != null) {
				query.where(remissionEntry.paymentStatus.eq(remissionEntryBalanceSearch.getPaymentState().getCode()));
			}
		}
		if (remissionEntryBalanceSearch.getFolio() != null) {
			if (!remissionEntryBalanceSearch.getFolio().isEmpty()) {
				query.where(remissionEntry.folio.eq(remissionEntryBalanceSearch.getFolio()));
			}
		}

		if (remissionEntryBalanceSearch.getSupplier() != null) {
			if (remissionEntryBalanceSearch.getSupplier().getSupplierId() != null) {
				query.where(remissionEntry.supplierId.eq(remissionEntryBalanceSearch.getSupplier().getSupplierId()));
			}
		}

		if (remissionEntryBalanceSearch.getDateFrom() != null) {
			Date from = remissionEntryBalanceSearch.getDateFrom();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(from);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			from = calendar.getTime();
			log.info("SEARCH BY DATE FROM: " + from.toString());
			query.where(remissionEntry.creationDate.goe(from));
		}

		if (remissionEntryBalanceSearch.getDateTo() != null) {
			Date to = remissionEntryBalanceSearch.getDateTo();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(to);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 59);
			to = calendar.getTime();
			log.info("SEARCH BY DATE TO: " + to.toString());
			query.where(remissionEntry.creationDate.loe(to));
		}

		/**
		 * Select fields
		 */
		query.select(Projections.constructor(RemissionEntryBalance.class, remissionEntry.remissionEntryId,
				remissionEntry.folio, supplier.businessName, remissionEntry.remissionTotal, remissionEntry.balanceDue,
				remissionEntry.creationDate, remissionEntry.paymentStatus));

		return query.fetch();
	}
}
