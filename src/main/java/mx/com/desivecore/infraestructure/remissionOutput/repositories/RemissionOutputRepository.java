package mx.com.desivecore.infraestructure.remissionOutput.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.remissionOutput.entities.RemissionOutputEntity;

@Repository
public interface RemissionOutputRepository extends JpaRepository<RemissionOutputEntity, Long> {

}
