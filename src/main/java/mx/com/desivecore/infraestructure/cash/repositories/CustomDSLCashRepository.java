package mx.com.desivecore.infraestructure.cash.repositories;

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
import mx.com.desivecore.domain.cash.models.CashSearchParams;
import mx.com.desivecore.domain.cash.models.OpeningCashSummary;
import mx.com.desivecore.infraestructure.branches.entities.QBranchEntity;
import mx.com.desivecore.infraestructure.cash.entities.QOpeningCashEntity;

@Log
@Repository
public class CustomDSLCashRepository extends QuerydslRepositorySupport {

	public CustomDSLCashRepository() {
		super(OpeningCashSummary.class);
	}

	@Autowired
	EntityManager em;

	public List<OpeningCashSummary> searchOpeningCashByParams(CashSearchParams cashSearchParams) {

		log.info("INIT searchOpeningCashByParams()");

		JPAQuery<OpeningCashSummary> query = new JPAQuery<>(em);

		QOpeningCashEntity openingCashEntity = QOpeningCashEntity.openingCashEntity;
		QBranchEntity branch = QBranchEntity.branchEntity;

		/**
		 * Start evaluation of tables
		 */
		query.from(openingCashEntity);
		query.innerJoin(branch).on(openingCashEntity.branchId.eq(branch.branchId));

		/**
		 * Start evaluation of search parameters
		 */
		if (cashSearchParams.getBranch() != null) {
			if (cashSearchParams.getBranch().getBranchId() != null) {
				query.where(openingCashEntity.branchId.eq(cashSearchParams.getBranch().getBranchId()));
			}
		}

		if (cashSearchParams.getUserEmail() != null) {
			if (!cashSearchParams.getUserEmail().isEmpty()) {
				query.where(openingCashEntity.userEmail.eq(cashSearchParams.getUserEmail()));
			}
		}

		if (cashSearchParams.getCreationDate() != null) {
			Date from = cashSearchParams.getCreationDate();
			log.info("SEARCH BY DATE FROM 1: " + from.toString());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(from);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);

			from = calendar.getTime();
			log.info("SEARCH BY DATE FROM: " + from.toString());

			query.where(openingCashEntity.creationDate.goe(from));
		}

		/**
		 * Select fields
		 */
		query.select(Projections.constructor(OpeningCashSummary.class, openingCashEntity.openingCashId,
				openingCashEntity.creationDate, branch.name, openingCashEntity.userEmail, openingCashEntity.isActive));

		return query.orderBy(openingCashEntity.creationDate.asc()).fetch();
	}

}
