package mx.com.desivecore.infraestructure.quarantine.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.quarantine.entities.QuarantineActionEntity;

@Repository
public interface QuarantineActionRepository extends JpaRepository<QuarantineActionEntity, Long> {

}
