package mx.com.desivecore.infraestructure.productTag.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.productTag.entities.ProductTagEntity;

@Repository
public interface ProductTagRepository extends JpaRepository<ProductTagEntity, Long>{

}
