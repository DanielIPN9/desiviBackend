package mx.com.desivecore.infraestructure.cash.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.cash.entities.AccountingTypeEntity;

@Repository
public interface AccountingTypeRepository extends JpaRepository<AccountingTypeEntity, Long>{

}
