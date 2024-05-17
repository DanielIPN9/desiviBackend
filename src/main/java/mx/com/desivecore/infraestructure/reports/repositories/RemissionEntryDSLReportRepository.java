package mx.com.desivecore.infraestructure.reports.repositories;

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
import mx.com.desivecore.domain.reports.models.RemissionEntryDetail;
import mx.com.desivecore.domain.reports.models.search.RemissionEntryParamsReport;
import mx.com.desivecore.infraestructure.branches.entities.QBranchEntity;
import mx.com.desivecore.infraestructure.products.entities.QProductEntity;
import mx.com.desivecore.infraestructure.remissionEntry.entities.QProductEntryEntity;
import mx.com.desivecore.infraestructure.remissionEntry.entities.QRemissionEntryEntity;
import mx.com.desivecore.infraestructure.suppliers.entities.QSupplierEntity;

@Log
@Repository
public class RemissionEntryDSLReportRepository extends QuerydslRepositorySupport {

	public RemissionEntryDSLReportRepository() {
		super(RemissionEntryDetail.class);
	}

	@Autowired
	EntityManager em;

	public List<RemissionEntryDetail> searchRemissionEntrySummartByParams(
			RemissionEntryParamsReport remissionEntryParamsReport) {

		log.info("INIT searchProductAvailabilityByBranchId()");
		JPAQuery<RemissionEntryDetail> query = new JPAQuery<>(em);

		QRemissionEntryEntity remissionEntry = QRemissionEntryEntity.remissionEntryEntity;
		QBranchEntity branch = QBranchEntity.branchEntity;
		QSupplierEntity supplier = QSupplierEntity.supplierEntity;
		QProductEntryEntity productEntry = QProductEntryEntity.productEntryEntity;
		QProductEntity product = QProductEntity.productEntity;

		/**
		 * Start evaluation of tables
		 */
		query.from(remissionEntry);
		query.innerJoin(branch).on(remissionEntry.branchId.eq(branch.branchId));
		query.innerJoin(supplier).on(remissionEntry.supplierId.eq(supplier.supplierId));
		query.innerJoin(productEntry).on(productEntry.remissionEntryId.eq(remissionEntry.remissionEntryId));
		query.innerJoin(product).on(productEntry.productId.eq(product.productId));

		/**
		 * Start evaluation of search parameters
		 */

		query.where(remissionEntry.status.eq(true));

		if (remissionEntryParamsReport.getSupplier() != null) {
			if (remissionEntryParamsReport.getSupplier().getSupplierId() != null) {
				query.where(remissionEntry.supplierId.eq(remissionEntryParamsReport.getSupplier().getSupplierId()));
			}
		}

		if (remissionEntryParamsReport.getProduct() != null) {
			if (remissionEntryParamsReport.getProduct().getProductId() != null) {
				query.where(productEntry.productId.eq(remissionEntryParamsReport.getProduct().getProductId()));
			}
		}

		if (remissionEntryParamsReport.getBranch() != null) {
			if (remissionEntryParamsReport.getBranch().getBranchId() != null) {
				query.where(remissionEntry.branchId.eq(remissionEntryParamsReport.getBranch().getBranchId()));
			}
		}

		if (remissionEntryParamsReport.getDateFrom() != null) {
			Date from = remissionEntryParamsReport.getDateFrom();
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

		if (remissionEntryParamsReport.getDateTo() != null) {
			Date to = remissionEntryParamsReport.getDateTo();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(to);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 0);
			to = calendar.getTime();
			log.info("SEARCH BY DATE TO: " + to.toString());
			query.where(remissionEntry.creationDate.loe(to));

		}

		/**
		 * Select fields
		 */
		query.select(Projections.constructor(RemissionEntryDetail.class, branch.name, supplier.businessName,
				remissionEntry.folio, product.name, remissionEntry.creationDate, productEntry.amount,
				productEntry.purchaseUnitPrice, productEntry.iva, productEntry.net, productEntry.total));

		return query.fetch();
	}
}
