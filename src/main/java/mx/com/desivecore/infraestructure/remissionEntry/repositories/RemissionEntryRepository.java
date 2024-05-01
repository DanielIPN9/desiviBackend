package mx.com.desivecore.infraestructure.remissionEntry.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.remissionEntry.entities.RemissionEntryEntity;

@Repository
public interface RemissionEntryRepository extends JpaRepository<RemissionEntryEntity, Long> {

	@Query("SELECT re FROM RemissionEntryEntity re WHERE re.userId=:userId")
	List<RemissionEntryEntity> findAllByUserId(@Param("userId") Long userId);

	@Transactional
	@Modifying
	@Query("UPDATE RemissionEntryEntity re SET re.status=:status WHERE re.remissionEntryId=:id")
	int changeRemissionStatusById(@Param("status") boolean status, @Param("id") Long id);

}
