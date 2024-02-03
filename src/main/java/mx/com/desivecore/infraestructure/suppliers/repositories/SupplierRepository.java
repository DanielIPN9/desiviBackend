package mx.com.desivecore.infraestructure.suppliers.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.suppliers.entities.SupplierEntity;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {

	@Query("SELECT s FROM SupplierEntity s WHERE s.rfc=:rfc")
	Optional<SupplierEntity> findByRfc(@Param("rfc") String rfc);

	@Query("SELECT s FROM SupplierEntity s WHERE s.rfc=:rfc AND s.supplierId!=:id")
	Optional<SupplierEntity> findByRfcAndIdNot(@Param("rfc") String rfc, @Param("id") Long id);

	@Query("SELECT s FROM SupplierEntity s WHERE s.email=:email")
	Optional<SupplierEntity> findByEmail(@Param("email") String email);

	@Query("SELECT s FROM SupplierEntity s WHERE s.email=:email AND s.supplierId!=:id")
	Optional<SupplierEntity> findByEmailAndIdNot(@Param("email") String email, @Param("id") Long id);

	@Transactional
	@Modifying
	@Query("UPDATE SupplierEntity s SET s.status=:status WHERE s.supplierId =:id")
	int enableById(@Param("id") Long id, @Param("status") boolean status);

}
