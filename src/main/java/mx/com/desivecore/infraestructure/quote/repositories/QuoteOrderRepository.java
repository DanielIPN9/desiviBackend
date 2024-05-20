package mx.com.desivecore.infraestructure.quote.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.desivecore.infraestructure.quote.entities.QuoteOrderEntity;

@Repository
public interface QuoteOrderRepository extends JpaRepository<QuoteOrderEntity, Long> {

	@Transactional
	@Modifying
	@Query("UPDATE QuoteOrderEntity qo SET qo.isConverter=:status WHERE qo.quoteOrderId=:quoteId")
	int changeQuoteStatusConverterById(@Param("quoteId") Long quoteId, @Param("status") boolean status);

	@Transactional
	@Modifying
	@Query("UPDATE QuoteOrderEntity qo SET qo.isEffective=:status WHERE qo.quoteOrderId=:quoteId")
	int changeEffectiveStatusById(@Param("quoteId") Long quoteId, @Param("status") boolean status);
}
