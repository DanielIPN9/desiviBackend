package mx.com.desivecore.infraestructure.quarantine.repositories;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.quarantine.models.ProductQuarantineSummary;
import mx.com.desivecore.domain.quarantine.models.QuarantineSearchParams;
import mx.com.desivecore.infraestructure.branches.entities.QBranchEntity;
import mx.com.desivecore.infraestructure.products.entities.QProductEntity;
import mx.com.desivecore.infraestructure.quarantine.entities.QProductQuarantineEntity;

@Log
@Repository
public class CustomDSLProductQuarantineRepository extends QuerydslRepositorySupport {

	public CustomDSLProductQuarantineRepository() {
		super(ProductQuarantineSummary.class);
	}

	@Autowired
	EntityManager em;

	public List<ProductQuarantineSummary> seachQuarantineStatusByParams(QuarantineSearchParams quarantineSearchParams) {

		log.info("INIT seachQuarantineStatusByParams()");
		JPAQuery<ProductQuarantineSummary> query = new JPAQuery<>(em);

		QProductQuarantineEntity productQuarantine = QProductQuarantineEntity.productQuarantineEntity;
		QProductEntity product = QProductEntity.productEntity;
		QBranchEntity branch = QBranchEntity.branchEntity;

		/**
		 * Start evaluation of tables
		 */
		query.from(productQuarantine);
		query.innerJoin(product).on(productQuarantine.productId.eq(product.productId));
		query.innerJoin(branch).on(productQuarantine.branchId.eq(branch.branchId));

		/**
		 * Start evaluation of search parameters
		 */
		if (quarantineSearchParams.getBranch() != null) {
			if (quarantineSearchParams.getBranch().getBranchId() != null) {
				query.where(productQuarantine.branchId.eq(quarantineSearchParams.getBranch().getBranchId()));
			}
		}

		if (quarantineSearchParams.getProduct() != null) {
			if (quarantineSearchParams.getProduct().getProductId() != null) {
				query.where(productQuarantine.productId.eq(quarantineSearchParams.getProduct().getProductId()));
			}
		}
		/**
		 * Select fields
		 */
		query.select(Projections.constructor(ProductQuarantineSummary.class, productQuarantine.productQuarantineId,
				branch.name, product.name, product.productId, productQuarantine.amount));

		return query.fetch();
	}
}
