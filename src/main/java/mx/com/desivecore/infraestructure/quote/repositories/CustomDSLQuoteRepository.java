package mx.com.desivecore.infraestructure.quote.repositories;

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
import mx.com.desivecore.domain.quote.models.QuoteOrderSummary;
import mx.com.desivecore.domain.quote.models.QuoteSearchParams;
import mx.com.desivecore.infraestructure.branches.entities.QBranchEntity;
import mx.com.desivecore.infraestructure.clients.entities.QClientEntity;
import mx.com.desivecore.infraestructure.quote.entities.QQuoteOrderEntity;

@Log
@Repository
public class CustomDSLQuoteRepository extends QuerydslRepositorySupport {

	public CustomDSLQuoteRepository() {
		super(QuoteOrderSummary.class);
	}

	@Autowired
	EntityManager em;

	public List<QuoteOrderSummary> searchQuoteOrderByParams(QuoteSearchParams quoteSearchParams) {

		log.info("INIT searchQuoteOrderByParams()");
		JPAQuery<QuoteOrderSummary> query = new JPAQuery<>(em);

		QQuoteOrderEntity quoteOrder = QQuoteOrderEntity.quoteOrderEntity;
		QClientEntity client = QClientEntity.clientEntity;
		QBranchEntity branch = QBranchEntity.branchEntity;

		/**
		 * Start evaluation of tables
		 */
		query.from(quoteOrder);
		query.innerJoin(client).on(quoteOrder.clientId.eq(client.clientId));
		query.innerJoin(branch).on(quoteOrder.branchId.eq(branch.branchId));

		/**
		 * Start evaluation of search parameters
		 */
		if (quoteSearchParams.getFolio() != null) {
			if (!quoteSearchParams.getFolio().isEmpty()) {
				query.where(quoteOrder.folio.like("%" + quoteSearchParams.getFolio() + "%"));
			}
		}

		if (quoteSearchParams.getClient() != null) {
			if (quoteSearchParams.getClient().getClientId() != null)
				query.where(quoteOrder.clientId.eq(quoteSearchParams.getClient().getClientId()));
		}

		if (quoteSearchParams.getBranch() != null) {
			if (quoteSearchParams.getBranch().getBranchId() != null)
				query.where(quoteOrder.branchId.eq(quoteSearchParams.getBranch().getBranchId()));
		}

		if (quoteSearchParams.getCreationDate() != null) {
			Date from = quoteSearchParams.getCreationDate();
			log.info("SEARCH BY DATE FROM 1: " + from.toString());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(from);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);

			from = calendar.getTime();
			log.info("SEARCH BY DATE FROM: " + from.toString());

			query.where(quoteOrder.creationDate.goe(from));
		}

		/**
		 * Select fields
		 */
		query.select(Projections.constructor(QuoteOrderSummary.class, quoteOrder.quoteOrderId, quoteOrder.folio,
				quoteOrder.effectiveDate, quoteOrder.total, quoteOrder.isConverter, quoteOrder.isEffective,
				branch.name));

		return query.orderBy(quoteOrder.creationDate.asc()).fetch();
	}
}
