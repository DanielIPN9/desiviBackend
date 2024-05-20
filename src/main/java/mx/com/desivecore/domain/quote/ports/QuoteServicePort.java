package mx.com.desivecore.domain.quote.ports;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.quote.models.QuoteOrder;
import mx.com.desivecore.domain.quote.models.QuoteSearchParams;

public interface QuoteServicePort {
	
	ResponseModel createQuoteOrder(QuoteOrder quoteOrder);
	
	ResponseModel viewQouteOrderById(Long quoteId);
	
	ResponseModel searchQuoteOrderByParams(QuoteSearchParams quoteSearchParams);
	
	ResponseModel updateQuoteOrder(QuoteOrder quoteOrder);
	
	ResponseModel generateQuoteOrderDocumentById(Long quoteId);
	
	ResponseModel covertQuoteOrder(Long quoteId);
	
	ResponseModel viewClientActiveList();
	
	ResponseModel viewBranchActiveList();
	
	ResponseModel viewProductActiveList();

}
