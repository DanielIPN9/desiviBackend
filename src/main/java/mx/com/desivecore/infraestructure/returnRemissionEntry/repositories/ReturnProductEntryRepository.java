package mx.com.desivecore.infraestructure.returnRemissionEntry.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.returnRemissionEntry.entities.ReturnProductEntryEntity;

@Repository
public interface ReturnProductEntryRepository extends JpaRepository<ReturnProductEntryEntity, Long> {

	@Query("SELECT rpe FROM ReturnProductEntryEntity rpe WHERE rpe.returnREId=:id")
	List<ReturnProductEntryEntity> findListByreturnREId(@Param("id") Long id);

}
