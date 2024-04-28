package mx.com.desivecore.infraestructure.products.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.products.entities.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

	@Query("SELECT p FROM ProductEntity p WHERE p.sku=:sku")
	Optional<ProductEntity> findBySku(@Param("sku") String sku);

	@Query("SELECT p FROM ProductEntity p WHERE p.sku=:sku AND p.status=:active")
	Optional<ProductEntity> findBySkuAndStatus(@Param("sku") String sku, @Param("active") boolean active);

	@Query("SELECT p FROM ProductEntity p WHERE p.sku=:sku AND p.productId!=:id")
	Optional<ProductEntity> findBySkuAndIdNot(@Param("sku") String sku, @Param("id") Long id);

	@Query("SELECT p FROM ProductEntity p WHERE p.sku=:sku AND p.productId!=:id AND p.status=:active")
	Optional<ProductEntity> findBySkuAndIdNotAndStatus(@Param("sku") String sku, @Param("id") Long id,
			@Param("active") boolean active);

	@Transactional
	@Modifying
	@Query("UPDATE ProductEntity p SET p.status=:status WHERE p.productId =:id")
	int enableById(@Param("id") Long id, @Param("status") boolean status);

	@Query("SELECT p FROM ProductEntity p WHERE p.status=:status")
	List<ProductEntity> findAllByStatus(@Param("status") boolean status);

}
