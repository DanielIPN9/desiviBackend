package mx.com.desivecore.infraestructure.payments.accountPayable.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.payments.accountPayable.entities.AccountPayableEntity;

@Repository
public interface AccountingPayableRepository extends JpaRepository<AccountPayableEntity, Long> {

	@Query("SELECT ap FROM AccountPayableEntity ap WHERE ap.remissionEntryId=:id")
	List<AccountPayableEntity> findAllByRemissionEntryId(@Param("id") Long id);

}
