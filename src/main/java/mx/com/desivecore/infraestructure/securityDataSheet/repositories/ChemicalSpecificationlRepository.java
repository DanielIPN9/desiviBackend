package mx.com.desivecore.infraestructure.securityDataSheet.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.securityDataSheet.entities.ChemicalSpecificationEntity;

@Repository
public interface ChemicalSpecificationlRepository extends JpaRepository<ChemicalSpecificationEntity, Long>{
	
	@Query("SELECT cs FROM ChemicalSpecificationEntity cs WHERE cs.securityDataSheetId=:secId")
	List<ChemicalSpecificationEntity> findAllBySecDataSheetId(@Param("secId") Long secId);

}
