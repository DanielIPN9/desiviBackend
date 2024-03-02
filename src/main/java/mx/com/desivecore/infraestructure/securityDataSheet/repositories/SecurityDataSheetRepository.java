package mx.com.desivecore.infraestructure.securityDataSheet.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.securityDataSheet.entities.SecurityDataSheetEntity;

@Repository
public interface SecurityDataSheetRepository extends JpaRepository<SecurityDataSheetEntity, Long> {

	@Query("SELECT sdh FROM SecurityDataSheetEntity sdh WHERE sdh.productId=:productId")
	Optional<SecurityDataSheetEntity> findByProductId(@Param("productId") Long productId);

	@Query("SELECT sdh FROM SecurityDataSheetEntity sdh WHERE sdh.productId=:productId AND sdh.securityDataSheetId!=:secId")
	Optional<SecurityDataSheetEntity> findByProductIdAndSecDataSheetIdNot(@Param("productId") Long productId,
			@Param("secId") Long secId);

}
