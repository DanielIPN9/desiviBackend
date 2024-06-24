package mx.com.desivecore.infraestructure.payments.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.payments.entities.PaymentStateEntity;

@Repository
public interface PaymentStateRepository extends JpaRepository<PaymentStateEntity, Long> {

}
