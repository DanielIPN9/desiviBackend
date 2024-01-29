package mx.com.desivecore.infraestructure.products.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.products.entities.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

	@Query("SELECT p FROM ProductEntity p WHERE p.sku=:sku")
	Optional<ProductEntity> findBySku(@Param("sku") String sku);

	@Query("SELECT p FROM ProductEntity p WHERE p.sku=:sku AND p.productId!=:id")
	Optional<ProductEntity> findBySkuAndIdNot(@Param("sku") String sku, @Param("id") Long id);

}
