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
import mx.com.desivecore.domain.reports.models.RemissionOutputDetail;
import mx.com.desivecore.domain.reports.models.search.RemissionOutputParamsReport;
import mx.com.desivecore.infraestructure.branches.entities.QBranchEntity;
import mx.com.desivecore.infraestructure.clients.entities.QClientEntity;
import mx.com.desivecore.infraestructure.products.entities.QProductEntity;
import mx.com.desivecore.infraestructure.remissionOutput.entities.QProductOutputEntity;
import mx.com.desivecore.infraestructure.remissionOutput.entities.QRemissionOutputEntity;

@Log
@Repository
public class RemissionOutputDSLReportRepository extends QuerydslRepositorySupport {

	public RemissionOutputDSLReportRepository() {
		super(RemissionOutputDetail.class);
	}

	@Autowired
	EntityManager em;

	public List<RemissionOutputDetail> searchRemissionOutputSummaryByParams(
			RemissionOutputParamsReport remissionOutputParamsReport) {

		log.info("INIT searchRemissionOutputSummaryByParams()");
		JPAQuery<RemissionOutputDetail> query = new JPAQuery<>(em);

		QRemissionOutputEntity remissionOutput = QRemissionOutputEntity.remissionOutputEntity;
		QBranchEntity branch = QBranchEntity.branchEntity;
		QClientEntity client = QClientEntity.clientEntity;
		QProductOutputEntity productOutput = QProductOutputEntity.productOutputEntity;
		QProductEntity product = QProductEntity.productEntity;

		/**
		 * Start evaluation of tables
		 */
		query.from(remissionOutput);
		query.innerJoin(branch).on(remissionOutput.branchId.eq(branch.branchId));
		query.innerJoin(client).on(remissionOutput.clientId.eq(client.clientId));
		query.innerJoin(productOutput).on(productOutput.remissionOutputId.eq(remissionOutput.remissionOutputId));
		query.innerJoin(product).on(productOutput.productId.eq(product.productId));

		/**
		 * Start evaluation of search parameters
		 */
		if (remissionOutputParamsReport.getClient() != null) {
			if (remissionOutputParamsReport.getClient().getClientId() != null) {
				query.where(remissionOutput.clientId.eq(remissionOutputParamsReport.getClient().getClientId()));
			}
		}

		if (remissionOutputParamsReport.getProduct() != null) {
			if (remissionOutputParamsReport.getProduct().getProductId() != null) {
				query.where(productOutput.productId.eq(remissionOutputParamsReport.getProduct().getProductId()));
			}
		}

		if (remissionOutputParamsReport.getBranch() != null) {
			if (remissionOutputParamsReport.getBranch().getBranchId() != null) {
				query.where(remissionOutput.branchId.eq(remissionOutputParamsReport.getBranch().getBranchId()));
			}
		}

		if (remissionOutputParamsReport.getDateFrom() != null) {
			Date from = remissionOutputParamsReport.getDateFrom();
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

		if (remissionOutputParamsReport.getDateTo() != null) {
			Date to = remissionOutputParamsReport.getDateTo();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(to);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 0);
			to = calendar.getTime();
			log.info("SEARCH BY DATE TO: " + to.toString());
			query.where(remissionOutput.creationDate.loe(to));

		}

		/**
		 * Select fields
		 */
		query.select(Projections.constructor(RemissionOutputDetail.class, branch.name, client.businessName,
				remissionOutput.folio, product.name, remissionOutput.creationDate, productOutput.amount,
				productOutput.sellingPrice, productOutput.iva, productOutput.net, productOutput.total));

		return query.fetch();
	}
}
