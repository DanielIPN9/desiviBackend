package mx.com.desivecore.infraestructure.returnRemissionEntry.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.returnRemissionEntry.entities.ReturnRemissionEntryEntity;

@Repository
public interface ReturnRERepository extends JpaRepository<ReturnRemissionEntryEntity, Long> {

	List<ReturnRemissionEntryEntity> findByFolio(String folio);
	
	@Query("SELECT rre.returnREId FROM ReturnRemissionEntryEntity rre WHERE rre.folio = :folio")
	List<Long> findIdsByFolio(@Param("folio") String folio);

}
