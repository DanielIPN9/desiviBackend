package mx.com.desivecore.infraestructure.payments.accountReceivable.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.payments.accountReceivable.entities.AccountReceivableEntity;

@Repository
public interface AccountingReceivableRepository extends JpaRepository<AccountReceivableEntity, Long> {

	@Query("SELECT ar FROM AccountReceivableEntity ar WHERE ar.remissionOutputId=:id")
	List<AccountReceivableEntity> findAllByRemissionOutputId(@Param("id") Long id);
}
