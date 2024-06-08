package mx.com.desivecore.infraestructure.returnRemissionOutput.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.returnRemissionOutput.entities.ReturnProductOutputEntity;

@Repository
public interface ReturnProductOutputRepository extends JpaRepository<ReturnProductOutputEntity, Long> {

	@Query("SELECT rpo FROM ReturnProductOutputEntity rpo WHERE rpo.returnROId=:id")
	List<ReturnProductOutputEntity> findListByReturnROId(@Param("id") Long id);
}
