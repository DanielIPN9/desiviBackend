package mx.com.desivecore.infraestructure.users.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.users.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	@Query("SELECT u FROM UserEntity u WHERE u.email=:email")
	Optional<UserEntity> findByEmail(@Param("email") String email);

	@Query("SELECT u FROM UserEntity u WHERE u.email=:email AND u.userId!=:id")
	Optional<UserEntity> findByEmailAndIdNot(@Param("email") String email, @Param("id") Long id);
	
	@Transactional
	@Modifying
	@Query("UPDATE UserEntity u SET u.status=:status WHERE u.userId =:id")
	int enableById(@Param("id") Long id, @Param("status") boolean status);
}
