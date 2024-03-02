package mx.com.desivecore.infraestructure.securityDataSheet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.securityDataSheet.entities.ChemicalIdentificationEntity;

@Repository
public interface ChemicalIdentificationRepository extends JpaRepository<ChemicalIdentificationEntity, Long> {
	
	@Query("SELECT ci FROM ChemicalIdentificationEntity ci WHERE ci.securityDataSheetId=:secId")
	List<ChemicalIdentificationEntity> findAllBySecDataSheetId(@Param("secId") Long secId);

}
