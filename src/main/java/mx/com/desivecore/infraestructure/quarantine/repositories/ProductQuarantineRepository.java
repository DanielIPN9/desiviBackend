package mx.com.desivecore.infraestructure.quarantine.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.quarantine.entities.ProductQuarantineEntity;

@Repository
public interface ProductQuarantineRepository extends JpaRepository<ProductQuarantineEntity, Long> {

	@Query("SELECT pq FROM ProductQuarantineEntity pq WHERE pq.branchId=:branchId AND pq.productId=:productId")
	Optional<ProductQuarantineEntity> frindByBranchIdAndProductId(@Param("branchId") Long branchId,
			@Param("productId") Long productId);

}
