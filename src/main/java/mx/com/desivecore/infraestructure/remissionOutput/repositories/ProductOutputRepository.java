package mx.com.desivecore.infraestructure.remissionOutput.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.remissionOutput.entities.ProductOutputEntity;

@Repository
public interface ProductOutputRepository extends JpaRepository<ProductOutputEntity, Long> {

	@Query("SELECT po FROM ProductOutputEntity po WHERE po.remissionOutputId=:remissionId")
	List<ProductOutputEntity> findAllByRemissionId(@Param("remissionId") Long remissionId);
}
