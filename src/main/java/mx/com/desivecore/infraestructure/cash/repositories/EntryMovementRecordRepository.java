package mx.com.desivecore.infraestructure.cash.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.cash.entities.EntryMovementRecordEntity;

@Repository
public interface EntryMovementRecordRepository extends JpaRepository<EntryMovementRecordEntity, Long> {
	
	@Query("SELECT emr FROM EntryMovementRecordEntity emr WHERE emr.openingCashId=:openingId")
	List<EntryMovementRecordEntity> findAllByOpeningId(@Param("openingId") Long openingId);

}
