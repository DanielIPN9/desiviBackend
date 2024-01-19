package mx.com.desivecore.infraestructure.security.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.security.entities.PermissionEntity;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long>{
	
	@Query("SELECT DISTINCT new PermissionEntity(p.id, p.name) FROM PermissionEntity p INNER JOIN p.roles r INNER JOIN r.users u WHERE u.id=:id")
	List<PermissionEntity> findAllByUserId(@Param("userId") Long userId);

}
