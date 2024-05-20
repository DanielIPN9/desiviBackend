package mx.com.desivecore.infraestructure.quote.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.quote.entities.QuoteProductEntity;

@Repository
public interface QuoteProductRepository extends JpaRepository<QuoteProductEntity, Long> {

	@Query("SELECT qp FROM QuoteProductEntity qp WHERE qp.quoteOrderId=:quoteId")
	List<QuoteProductEntity> findProductByQuoteOrderId(@Param("quoteId") Long quoteId);

}
