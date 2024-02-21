package mx.com.desivecore.infraestructure.remissionEntry.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.remissionEntry.entities.RemissionEntryHistoryEntity;

@Repository
public interface RemissionEntryHistoryRepository extends JpaRepository<RemissionEntryHistoryEntity, Long> {

	@Query("SELECT reh FROM RemissionEntryHistoryEntity reh WHERE reh.remissionEntryId=:remissionEntryId")
	List<RemissionEntryHistoryEntity> findALLByRemissionEntryId(@Param("remissionEntryId") Long remisionEntryId);

}
