package mx.com.desivecore.infraestructure.returnRemissionOutput.repositories;

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
import mx.com.desivecore.domain.returnRemissionOutput.models.ReturnROSearchParams;
import mx.com.desivecore.domain.returnRemissionOutput.models.ReturnROSummary;
import mx.com.desivecore.infraestructure.branches.entities.QBranchEntity;
import mx.com.desivecore.infraestructure.clients.entities.QClientEntity;
import mx.com.desivecore.infraestructure.returnRemissionOutput.entities.QReturnRemissionOutputEntity;

@Log
@Repository
public class CustomDSLReturnRORepository extends QuerydslRepositorySupport {

	public CustomDSLReturnRORepository() {
		super(ReturnROSummary.class);
	}

	@Autowired
	EntityManager em;

	public List<ReturnROSummary> searchReturnROSummaryByParams(ReturnROSearchParams returnROSearchParams) {
		log.info("INIT searchReturnROSummaryByParams()");

		JPAQuery<ReturnROSummary> query = new JPAQuery<>(em);

		QReturnRemissionOutputEntity returnRemissionOutput = QReturnRemissionOutputEntity.returnRemissionOutputEntity;
		QClientEntity client = QClientEntity.clientEntity;
		QBranchEntity branch = QBranchEntity.branchEntity;

		/**
		 * Start evaluation of tables
		 */
		query.from(returnRemissionOutput);
		query.innerJoin(client).on(returnRemissionOutput.clientId.eq(client.clientId));
		query.innerJoin(branch).on(returnRemissionOutput.branchId.eq(branch.branchId));

		/**
		 * Start evaluation of search parameters
		 */
		if (returnROSearchParams.getFolio() != null) {
			if (!returnROSearchParams.getFolio().isEmpty()) {
				query.where(returnRemissionOutput.folio.eq(returnROSearchParams.getFolio()));
			}
		}

		if (returnROSearchParams.getClient() != null) {
			if (returnROSearchParams.getClient().getClientId() != null)
				query.where(returnRemissionOutput.clientId.eq(returnROSearchParams.getClient().getClientId()));
		}

		if (returnROSearchParams.getBranch() != null) {
			if (returnROSearchParams.getBranch().getBranchId() != null)
				query.where(returnRemissionOutput.branchId.eq(returnROSearchParams.getBranch().getBranchId()));
		}

		if (returnROSearchParams.getCreationDate() != null) {
			Date from = returnROSearchParams.getCreationDate();
			log.info("SEARCH BY DATE FROM 1: " + from.toString());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(from);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);

			from = calendar.getTime();
			log.info("SEARCH BY DATE FROM: " + from.toString());

			query.where(returnRemissionOutput.creationDate.goe(from));
		}

		/**
		 * Select fields
		 */
		query.select(Projections.constructor(ReturnROSummary.class, returnRemissionOutput.returnROId,
				returnRemissionOutput.folio, returnRemissionOutput.creationDate, returnRemissionOutput.total));

		return query.orderBy(returnRemissionOutput.creationDate.asc()).limit(50).fetch();
	}
}
