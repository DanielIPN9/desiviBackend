package mx.com.desivecore.infraestructure.remissionEntry.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.remissionEntry.entities.ProductEntryEntity;

@Repository
public interface ProductEntryRepository extends JpaRepository<ProductEntryEntity, Long> {
	
	@Query("SELECT pe FROM ProductEntryEntity pe WHERE pe.remissionEntryId=:remissionId")
	List<ProductEntryEntity> findAllByRemissionId(@Param("remissionId") Long remissionId);

	
}
