package mx.com.desivecore.infraestructure.products.repositories;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;

import lombok.extern.java.Log;
import mx.com.desivecore.domain.products.models.ProductOutputSummary;
import mx.com.desivecore.infraestructure.products.entities.QProductAvailabilityEntity;
import mx.com.desivecore.infraestructure.products.entities.QProductEntity;

@Log
@Repository
public class CustomDSLProductRepository extends QuerydslRepositorySupport {

	public CustomDSLProductRepository() {
		super(ProductOutputSummary.class);
	}

	@Autowired
	EntityManager em;

	public List<ProductOutputSummary> findAllByBranchId(Long branchId) {

		log.info("INIT findAllByBranchId()");

		JPAQuery<ProductOutputSummary> query = new JPAQuery<>(em);

		QProductEntity product = QProductEntity.productEntity;
		QProductAvailabilityEntity productAvailability = QProductAvailabilityEntity.productAvailabilityEntity;

		/**
		 * Start evaluation of tables
		 */
		query.from(product);
		query.innerJoin(productAvailability).on(product.productId.eq(productAvailability.productId));
		/**
		 * Start evaluation of search parameters
		 */
		query.where(productAvailability.branchId.eq(branchId));
		query.where(product.status.eq(true));
		/**
		 * Select fields
		 */
		query.select(Projections.constructor(ProductOutputSummary.class, product.productId, product.sku, product.name,
				productAvailability.amount, product.unitSellingPrice, product.iva, product.unitMeasure));

		return query.fetch();

	}

	public ProductOutputSummary findByProductIdAndBranchId(Long branchId, Long productId) {
		log.info("INIT findByProductIdAndBranchId()");
		JPAQuery<ProductOutputSummary> query = new JPAQuery<>(em);

		QProductEntity product = QProductEntity.productEntity;
		QProductAvailabilityEntity productAvailability = QProductAvailabilityEntity.productAvailabilityEntity;

		/**
		 * Start evaluation of tables
		 */
		query.from(product);
		query.innerJoin(productAvailability).on(product.productId.eq(productAvailability.productId));
		/**
		 * Start evaluation of search parameters
		 */
		query.where(productAvailability.branchId.eq(branchId));
		query.where(product.productId.eq(productId));
		/**
		 * Select fields
		 */
		query.select(Projections.constructor(ProductOutputSummary.class, product.productId, product.sku, product.name,
				productAvailability.amount, product.unitSellingPrice, product.iva, product.unitMeasure));

		return query.fetch().get(0);
	}

}
