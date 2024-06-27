package mx.com.desivecore.infraestructure.remissionOutput.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.remissionOutput.entities.RemissionOutputEntity;

@Repository
public interface RemissionOutputRepository extends JpaRepository<RemissionOutputEntity, Long> {

	@Transactional
	@Modifying
	@Query("UPDATE RemissionOutputEntity ro SET ro.status=:status WHERE ro.remissionOutputId=:id")
	int changeRemissionStatusById(@Param("status") boolean status, @Param("id") Long id);

	@Transactional
	@Modifying
	@Query("UPDATE RemissionOutputEntity ro SET ro.balanceDue=:balanceDue, ro.paymentStatus=:paymentStatus WHERE ro.remissionOutputId=:remissionOutputId")
	int updateBalanceAndPaymentStatusById(@Param("balanceDue") Double balanceDue,
			@Param("paymentStatus") String paymentStatus, @Param("remissionOutputId") Long remissionOutputId);

}
