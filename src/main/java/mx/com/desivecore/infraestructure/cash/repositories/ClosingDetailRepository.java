package mx.com.desivecore.infraestructure.cash.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.cash.entities.ClosingDetailEntity;

@Repository
public interface ClosingDetailRepository extends JpaRepository<ClosingDetailEntity, Long> {

	@Query("SELECT cd FROM ClosingDetailEntity cd WHERE cd.closingCashId=:closingId")
	List<ClosingDetailEntity> findAllByClosingId(@Param("closingId") Long closingId);

}
