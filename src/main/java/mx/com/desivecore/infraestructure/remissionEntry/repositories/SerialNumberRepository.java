package mx.com.desivecore.infraestructure.remissionEntry.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.remissionEntry.entities.SerialNumberEntity;

@Repository
public interface SerialNumberRepository extends JpaRepository<SerialNumberEntity, Long> {

	@Query("SELECT sn FROM SerialNumberEntity sn WHERE sn.code=:code")
	Optional<SerialNumberEntity> findByCode(@Param("code") String code);

}
