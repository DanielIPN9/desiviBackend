package mx.com.desivecore.infraestructure.cash.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.cash.entities.OpeningCashEntity;

@Repository
public interface OpeningCashRepository extends JpaRepository<OpeningCashEntity, Long> {

	@Query("SELECT oc FROM OpeningCashEntity oc WHERE oc.isActive=:active AND oc.userId=:userId")
	Optional<OpeningCashEntity> findByActiveAndUserId(@Param("active") Boolean active, @Param("userId") Long userId);

	@Transactional
	@Modifying
	@Query("UPDATE OpeningCashEntity oc SET oc.isActive=:active, oc.closingCashId=:closingId  WHERE oc.openingCashId=:openingCashId ")
	int changeOpeningActiveAndClosingIdStateById(@Param("active") Boolean active,
			@Param("openingCashId") Long openingCashId, @Param("closingId") Long closingId);

}
