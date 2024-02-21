package mx.com.desivecore.infraestructure.products.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.products.entities.ProductAvailabilityEntity;

@Repository
public interface ProductAvailabilityRepository extends JpaRepository<ProductAvailabilityEntity, Long> {

	@Query("SELECT pa FROM ProductAvailabilityEntity pa WHERE pa.productId=:producId")
	List<ProductAvailabilityEntity> findByProductId(@Param("producId") Long producId);

	@Query("SELECT pa FROM ProductAvailabilityEntity pa WHERE pa.branchId=:branchId AND pa.productId=:productId")
	Optional<ProductAvailabilityEntity> findByBranchIdAndProductId(@Param("branchId") Long branchId,
			@Param("productId") Long productId);

}
