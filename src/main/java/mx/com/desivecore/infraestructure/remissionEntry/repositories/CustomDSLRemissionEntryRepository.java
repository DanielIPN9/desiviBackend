package mx.com.desivecore.infraestructure.remissionEntry.repositories;

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
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntrySummary;
import mx.com.desivecore.domain.remissionEntry.models.RemissionSearchParams;
import mx.com.desivecore.infraestructure.branches.entities.QBranchEntity;
import mx.com.desivecore.infraestructure.remissionEntry.entities.QRemissionEntryEntity;
import mx.com.desivecore.infraestructure.suppliers.entities.QSupplierEntity;

@Log
@Repository
public class CustomDSLRemissionEntryRepository extends QuerydslRepositorySupport {

	public CustomDSLRemissionEntryRepository() {
		super(RemissionEntrySummary.class);
	}

	@Autowired
	EntityManager em;

	public List<RemissionEntrySummary> searchRemissionEntryByParams(RemissionSearchParams remissionSearchParams) {

		log.info("INIT searchRemissionEntryByParams()");

		JPAQuery<RemissionEntrySummary> query = new JPAQuery<>(em);

		QRemissionEntryEntity remissionEntry = QRemissionEntryEntity.remissionEntryEntity;
		QSupplierEntity supplier = QSupplierEntity.supplierEntity;
		QBranchEntity branch = QBranchEntity.branchEntity;

		/**
		 * Start evaluation of tables
		 */
		query.from(remissionEntry);
		query.innerJoin(supplier).on(remissionEntry.supplierId.eq(supplier.supplierId));
		query.innerJoin(branch).on(remissionEntry.branchId.eq(branch.branchId));

		/**
		 * Start evaluation of search parameters
		 */

		if (remissionSearchParams.getFolio() != null) {
			if (!remissionSearchParams.getFolio().isEmpty()) {
				query.where(remissionEntry.folio.eq(remissionSearchParams.getFolio()));
			}
		}

		if (remissionSearchParams.getSupplier() != null) {
			if (remissionSearchParams.getSupplier().getSupplierId() != null)
				query.where(remissionEntry.supplierId.eq(remissionSearchParams.getSupplier().getSupplierId()));
		}

		if (remissionSearchParams.getBranch() != null) {
			if (remissionSearchParams.getBranch().getBranchId() != null)
				query.where(remissionEntry.branchId.eq(remissionSearchParams.getBranch().getBranchId()));
		}

		if (remissionSearchParams.getCreationDate() != null) {
			Date from = remissionSearchParams.getCreationDate();
			log.info("SEARCH BY DATE FROM 1: " + from.toString());
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

		/**
		 * Select fields
		 */
		query.select(Projections.constructor(RemissionEntrySummary.class, remissionEntry.remissionEntryId,
				remissionEntry.folio, remissionEntry.creationDate, remissionEntry.requestDate, supplier.businessName,
				branch.name, remissionEntry.remissionTotal));

		return query.orderBy(remissionEntry.creationDate.asc()).limit(40).fetch();
	}
}
