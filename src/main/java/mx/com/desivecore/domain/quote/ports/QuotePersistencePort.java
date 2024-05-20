package mx.com.desivecore.domain.quote.ports;

import java.math.BigInteger;
import java.util.List;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.quote.models.QuoteOrder;
import mx.com.desivecore.domain.quote.models.QuoteOrderSummary;
import mx.com.desivecore.domain.quote.models.QuoteSearchParams;
import mx.com.desivecore.domain.quote.models.document.QuoteOrderDocument;

public interface QuotePersistencePort {

	QuoteOrder saveQuoteOrder(QuoteOrder quoteOrder);

	BigInteger getConsecutiveByCode(String code);

	QuoteOrder viewQouteOrderById(Long quoteId);

	List<QuoteOrderSummary> searchQuoteOrderByParams(QuoteSearchParams quoteSearchParams);

	ResponseModel generateQuoteOrderDocument(QuoteOrderDocument quoteOrderDocument);

	boolean changeQuoteEffectiveStatusById(Long quoteId, boolean status);
	
	boolean changeQuoteConvertStatusById(Long quoteId, boolean status);

}
