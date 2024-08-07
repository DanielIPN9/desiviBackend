package mx.com.desivecore.infraestructure.returnRemissionOutput.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.returnRemissionOutput.entities.ReturnRemissionOutputEntity;

@Repository
public interface ReturnRORepository extends JpaRepository<ReturnRemissionOutputEntity, Long> {

	List<ReturnRemissionOutputEntity> findByFolio(String folio);

	@Query("SELECT rro.returnROId FROM ReturnRemissionOutputEntity rro WHERE rro.folio = :folio")
	List<Long> findIdsByFolio(@Param("folio") String folio);
}
