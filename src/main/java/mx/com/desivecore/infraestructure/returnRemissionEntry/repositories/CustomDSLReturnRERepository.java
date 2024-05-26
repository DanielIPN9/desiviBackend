package mx.com.desivecore.infraestructure.returnRemissionEntry.repositories;

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
import mx.com.desivecore.domain.returnRemissionEntry.models.ReturnRESearchParams;
import mx.com.desivecore.domain.returnRemissionEntry.models.ReturnRESummary;
import mx.com.desivecore.infraestructure.branches.entities.QBranchEntity;
import mx.com.desivecore.infraestructure.returnRemissionEntry.entities.QReturnRemissionEntryEntity;
import mx.com.desivecore.infraestructure.suppliers.entities.QSupplierEntity;

@Log
@Repository
public class CustomDSLReturnRERepository extends QuerydslRepositorySupport {

	public CustomDSLReturnRERepository() {
		super(ReturnRESummary.class);
	}

	@Autowired
	EntityManager em;

	public List<ReturnRESummary> seachReturnRESummaryByParams(ReturnRESearchParams returnRESearchParams) {
		log.info("INIT seachReturnRESummaryByParams()");

		JPAQuery<ReturnRESummary> query = new JPAQuery<>(em);

		QReturnRemissionEntryEntity returnRemissionEntry = QReturnRemissionEntryEntity.returnRemissionEntryEntity;
		QSupplierEntity supplier = QSupplierEntity.supplierEntity;
		QBranchEntity branch = QBranchEntity.branchEntity;

		/**
		 * Start evaluation of tables
		 */
		query.from(returnRemissionEntry);
		query.innerJoin(supplier).on(returnRemissionEntry.supplierId.eq(supplier.supplierId));
		query.innerJoin(branch).on(returnRemissionEntry.branchId.eq(branch.branchId));

		/**
		 * Start evaluation of search parameters
		 */

		if (returnRESearchParams.getFolio() != null) {
			if (!returnRESearchParams.getFolio().isEmpty()) {
				query.where(returnRemissionEntry.folio.eq(returnRESearchParams.getFolio()));
			}
		}

		if (returnRESearchParams.getSupplier() != null) {
			if (returnRESearchParams.getSupplier().getSupplierId() != null)
				query.where(returnRemissionEntry.supplierId.eq(returnRESearchParams.getSupplier().getSupplierId()));
		}

		if (returnRESearchParams.getBranch() != null) {
			if (returnRESearchParams.getBranch().getBranchId() != null)
				query.where(returnRemissionEntry.branchId.eq(returnRESearchParams.getBranch().getBranchId()));
		}

		if (returnRESearchParams.getCreationDate() != null) {
			Date from = returnRESearchParams.getCreationDate();
			log.info("SEARCH BY DATE FROM 1: " + from.toString());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(from);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);

			from = calendar.getTime();
			log.info("SEARCH BY DATE FROM: " + from.toString());

			query.where(returnRemissionEntry.creationDate.goe(from));

		}

		/**
		 * Select fields
		 */
		query.select(Projections.constructor(ReturnRESummary.class, returnRemissionEntry.returnREId,
				returnRemissionEntry.folio, returnRemissionEntry.creationDate, returnRemissionEntry.total));

		return query.orderBy(returnRemissionEntry.creationDate.asc()).limit(50).fetch();
	}
}
