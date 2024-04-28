package mx.com.desivecore.infraestructure.clients.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.clients.entities.ClientEntity;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

	@Query("SELECT c FROM ClientEntity c WHERE c.email=:email")
	Optional<ClientEntity> findByEmail(@Param("email") String email);

	@Query("SELECT c FROM ClientEntity c WHERE c.email=:email AND c.clientId!=:clientId")
	Optional<ClientEntity> findByEmailAndIdNot(@Param("email") String email, @Param("clientId") Long clientId);

	@Query("SELECT c FROM ClientEntity c WHERE c.rfc=:rfc")
	Optional<ClientEntity> findByRfc(@Param("rfc") String rfc);

	@Query("SELECT c FROM ClientEntity c WHERE c.rfc=:rfc AND c.status=:active")
	Optional<ClientEntity> findByRfcAndStatus(@Param("rfc") String rfc, @Param("active") boolean active);

	@Query("SELECT c FROM ClientEntity c WHERE c.rfc=:rfc AND c.clientId!=:clientId")
	Optional<ClientEntity> findByRfcAndIdNot(@Param("rfc") String rfc, @Param("clientId") Long clientId);

	@Query("SELECT c FROM ClientEntity c WHERE c.rfc=:rfc AND c.clientId!=:clientId AND c.status=:active")
	Optional<ClientEntity> findByRfcAndIdNotAndStatus(@Param("rfc") String rfc, @Param("clientId") Long clientId,
			@Param("active") boolean active);

	@Transactional
	@Modifying
	@Query("UPDATE ClientEntity c SET c.status=:status WHERE c.clientId=:id")
	int enableById(@Param("id") Long id, @Param("status") boolean status);

	@Query("SELECT c FROM ClientEntity c WHERE c.status=:active")
	List<ClientEntity> findAllByState(@Param("active") boolean active);
}
