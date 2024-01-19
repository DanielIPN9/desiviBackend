package mx.com.desivecore.infraestructure.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.security.entities.RolEntity;

@Repository
public interface RoleRepository extends JpaRepository<RolEntity, Long> {
	
	Optional<RolEntity> findById(Long id);

}
