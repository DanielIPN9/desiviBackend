package mx.com.desivecore.infraestructure.reports.repositories;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.reports.models.ProductDetail;
import mx.com.desivecore.infraestructure.branches.entities.QBranchEntity;
import mx.com.desivecore.infraestructure.products.entities.QProductAvailabilityEntity;
import mx.com.desivecore.infraestructure.products.entities.QProductEntity;

@Log
@Repository
public class InventoryDSLReportRepository extends QuerydslRepositorySupport {

	public InventoryDSLReportRepository() {
		super(ProductDetail.class);
	}

	@Autowired
	EntityManager em;

	public List<ProductDetail> searchProductAvailabilityByBranchId(Long branchId) {

		log.info("INIT searchProductAvailabilityByBranchId()");
		JPAQuery<ProductDetail> query = new JPAQuery<>(em);

		QProductAvailabilityEntity productAvailability = QProductAvailabilityEntity.productAvailabilityEntity;
		QBranchEntity branch = QBranchEntity.branchEntity;
		QProductEntity product = QProductEntity.productEntity;

		/**
		 * Start evaluation of tables
		 */
		query.from(productAvailability);
		query.innerJoin(branch).on(productAvailability.branchId.eq(branch.branchId));
		query.innerJoin(product).on(productAvailability.productId.eq(product.productId));

		/**
		 * Start evaluation of search parameters
		 */
		query.where(productAvailability.branchId.eq(branchId));
		/**
		 * Select fields
		 */
		query.select(Projections.constructor(ProductDetail.class, branch.name, product.name, product.unitMeasure,
				productAvailability.amount, product.unitPurchasePrice, product.unitSellingPrice, product.iva,
				product.minAvailability));

		return query.fetch();

	}

}
