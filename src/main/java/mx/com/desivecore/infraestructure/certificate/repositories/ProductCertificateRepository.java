package mx.com.desivecore.infraestructure.certificate.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.certificate.entities.ProductCertificateEntity;

@Repository
public interface ProductCertificateRepository extends JpaRepository<ProductCertificateEntity, Long> {

	@Query("SELECT pc FROM ProductCertificateEntity pc WHERE pc.remissionEntryId=:remissionId AND pc.productId=:productId")
	Optional<ProductCertificateEntity> findByRemissionIdAndProductId(@Param("remissionId") Long remissionId,
			@Param("productId") Long productId);

	@Query("SELECT pc FROM ProductCertificateEntity pc WHERE pc.remissionEntryId=:remissionId AND pc.productId=:productId AND pc.certificateId!=:certificateId")
	Optional<ProductCertificateEntity> findByRemissionIdAndProductIdAndCertificateIdNot(
			@Param("remissionId") Long remissionId, @Param("productId") Long productId,
			@Param("certificateId") Long certificateId);

}
