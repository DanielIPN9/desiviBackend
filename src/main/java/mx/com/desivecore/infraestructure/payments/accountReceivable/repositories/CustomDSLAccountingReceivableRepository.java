package mx.com.desivecore.infraestructure.payments.accountReceivable.repositories;

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
import mx.com.desivecore.domain.payments.accountReceivable.models.RemissionOutputBalance;
import mx.com.desivecore.domain.payments.accountReceivable.models.RemissionOutputBalanceSearch;
import mx.com.desivecore.infraestructure.clients.entities.QClientEntity;
import mx.com.desivecore.infraestructure.remissionOutput.entities.QRemissionOutputEntity;

@Log
@Repository
public class CustomDSLAccountingReceivableRepository extends QuerydslRepositorySupport {

	public CustomDSLAccountingReceivableRepository() {
		super(RemissionOutputBalance.class);
	}

	@Autowired
	EntityManager em;

	public List<RemissionOutputBalance> searchROBalanceByParams(
			RemissionOutputBalanceSearch remissionOutputBalanceSearch) {
		log.info("INIT searchROBalanceByParams()");

		JPAQuery<RemissionOutputBalance> query = new JPAQuery<>(em);

		QRemissionOutputEntity remissionOutput = QRemissionOutputEntity.remissionOutputEntity;
		QClientEntity client = QClientEntity.clientEntity;

		/**
		 * Start evaluation of tables
		 */
		query.from(remissionOutput);
		query.innerJoin(client).on(remissionOutput.clientId.eq(client.clientId));

		/**
		 * Start evaluation of search parameters
		 */
		query.where(remissionOutput.status.eq(true));

		if (remissionOutputBalanceSearch.getClient() != null) {
			if (remissionOutputBalanceSearch.getClient().getClientId() != null) {
				query.where(remissionOutput.clientId.eq(remissionOutputBalanceSearch.getClient().getClientId()));
			}
		}

		if (remissionOutputBalanceSearch.getPaymentState() != null) {
			if (remissionOutputBalanceSearch.getPaymentState().getCode() != null) {
				query.where(remissionOutput.paymentStatus.eq(remissionOutputBalanceSearch.getPaymentState().getCode()));
			}
		}

		if (remissionOutputBalanceSearch.getFolio() != null) {
			if (!remissionOutputBalanceSearch.getFolio().isEmpty()) {
				query.where(remissionOutput.folio.eq(remissionOutputBalanceSearch.getFolio()));
			}
		}

		if (remissionOutputBalanceSearch.getDateFrom() != null) {
			Date from = remissionOutputBalanceSearch.getDateFrom();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(from);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			from = calendar.getTime();
			log.info("SEARCH BY DATE FROM: " + from.toString());
			query.where(remissionOutput.creationDate.goe(from));
		}

		if (remissionOutputBalanceSearch.getDateTo() != null) {
			Date to = remissionOutputBalanceSearch.getDateTo();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(to);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 59);
			to = calendar.getTime();
			log.info("SEARCH BY DATE TO: " + to.toString());
			query.where(remissionOutput.creationDate.loe(to));
		}

		/**
		 * Select fields
		 */
		query.select(Projections.constructor(RemissionOutputBalance.class, remissionOutput.remissionOutputId,
				remissionOutput.folio, client.businessName, remissionOutput.remissionTotal, remissionOutput.balanceDue,
				remissionOutput.paymentStatus, remissionOutput.creationDate));
		return query.fetch();
	}
}
