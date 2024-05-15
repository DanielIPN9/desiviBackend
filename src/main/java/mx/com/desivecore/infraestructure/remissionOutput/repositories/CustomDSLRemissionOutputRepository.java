package mx.com.desivecore.infraestructure.remissionOutput.repositories;

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
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutputSearchParams;
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutputSummary;
import mx.com.desivecore.infraestructure.branches.entities.QBranchEntity;
import mx.com.desivecore.infraestructure.clients.entities.QClientEntity;
import mx.com.desivecore.infraestructure.remissionOutput.entities.QRemissionOutputEntity;

@Log
@Repository
public class CustomDSLRemissionOutputRepository extends QuerydslRepositorySupport {

	public CustomDSLRemissionOutputRepository() {
		super(RemissionOutputSummary.class);
	}

	@Autowired
	EntityManager em;

	public List<RemissionOutputSummary> searchRemissionOutputByParams(
			RemissionOutputSearchParams remissionOutputSearchParams) {

		log.info("INIT searchRemissionOutputByParams()");
		JPAQuery<RemissionOutputSummary> query = new JPAQuery<>(em);

		QRemissionOutputEntity remissionOutput = QRemissionOutputEntity.remissionOutputEntity;
		QClientEntity client = QClientEntity.clientEntity;
		QBranchEntity branch = QBranchEntity.branchEntity;

		/**
		 * Start evaluation of tables
		 */
		query.from(remissionOutput);
		query.innerJoin(client).on(remissionOutput.clientId.eq(client.clientId));
		query.innerJoin(branch).on(remissionOutput.branchId.eq(branch.branchId));

		/**
		 * Start evaluation of search parameters
		 */

		query.where(remissionOutput.status.eq(true));

		if (remissionOutputSearchParams.getFolio() != null) {
			if (!remissionOutputSearchParams.getFolio().isEmpty()) {
				query.where(remissionOutput.folio.eq(remissionOutputSearchParams.getFolio()));
			}
		}

		if (remissionOutputSearchParams.getClient() != null) {
			if (remissionOutputSearchParams.getClient().getClientId() != null)
				query.where(remissionOutput.clientId.eq(remissionOutputSearchParams.getClient().getClientId()));
		}

		if (remissionOutputSearchParams.getBranch() != null) {
			if (remissionOutputSearchParams.getBranch().getBranchId() != null)
				query.where(remissionOutput.branchId.eq(remissionOutputSearchParams.getBranch().getBranchId()));
		}

		if (remissionOutputSearchParams.getCreationDate() != null) {
			Date from = remissionOutputSearchParams.getCreationDate();
			log.info("SEARCH BY DATE FROM 1: " + from.toString());
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

		/**
		 * Select fields
		 */
		query.select(Projections.constructor(RemissionOutputSummary.class, remissionOutput.remissionOutputId,
				remissionOutput.folio, remissionOutput.creationDate, remissionOutput.requestDay, client.businessName,
				branch.name, remissionOutput.remissionTotal, remissionOutput.status));

		return query.orderBy(remissionOutput.creationDate.asc()).fetch();
	}

	public List<RemissionOutputSummary> findRemissionOutputByUserId(Long userId) {

		log.info("INIT searchRemissionOutputByParams()");
		JPAQuery<RemissionOutputSummary> query = new JPAQuery<>(em);

		QRemissionOutputEntity remissionOutput = QRemissionOutputEntity.remissionOutputEntity;
		QClientEntity client = QClientEntity.clientEntity;
		QBranchEntity branch = QBranchEntity.branchEntity;

		/**
		 * Start evaluation of tables
		 */
		query.from(remissionOutput);
		query.innerJoin(client).on(remissionOutput.clientId.eq(client.clientId));
		query.innerJoin(branch).on(remissionOutput.branchId.eq(branch.branchId));

		/**
		 * Start evaluation of search parameters
		 */
		query.where(remissionOutput.userId.eq(userId));
		/**
		 * Select fields
		 */
		query.select(Projections.constructor(RemissionOutputSummary.class, remissionOutput.remissionOutputId,
				remissionOutput.folio, remissionOutput.creationDate, remissionOutput.requestDay, client.businessName,
				branch.name, remissionOutput.remissionTotal));

		return query.orderBy(remissionOutput.creationDate.asc()).limit(40).fetch();
	}

}
