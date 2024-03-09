package mx.com.desivecore.infraestructure.certificate.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.certificate.entities.CertificateDetailEntity;

@Repository
public interface CertificateDetailRepository extends JpaRepository<CertificateDetailEntity, Long>{
	
	@Query("SELECT cd FROM CertificateDetailEntity cd WHERE cd.certificateId=:certificateId")
	List<CertificateDetailEntity> findAllByCertificateId(@Param("certificateId") Long certificateId);

}
