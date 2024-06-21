package mx.com.desivecore.infraestructure.cash.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.cash.entities.ExitMovementRecordEntity;

@Repository
public interface ExitMovementRecordRepository extends JpaRepository<ExitMovementRecordEntity, Long>{
	
	@Query("SELECT emr FROM ExitMovementRecordEntity emr WHERE emr.openingCashId=:openingId")
	List<ExitMovementRecordEntity> findAllByOpeningId(@Param("openingId") Long openingId);

}
