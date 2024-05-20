package mx.com.desivecore.infraestructure.quote.converters;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.quote.models.QuoteOrder;
import mx.com.desivecore.infraestructure.quote.entities.QuoteOrderEntity;

@Component
public class QuoteOrderConverter {

	public QuoteOrderEntity quoteOrderToEntity(QuoteOrder quoteOrder) {

		QuoteOrderEntity quoteOrderEntity = new QuoteOrderEntity();

		quoteOrderEntity.setQuoteOrderId(quoteOrder.getQuoteOrderId());
		quoteOrderEntity.setFolio(quoteOrder.getFolio());
		quoteOrderEntity.setCreationDate(quoteOrder.getCreationDate());
		quoteOrderEntity.setEffectiveDate(quoteOrder.getEffectiveDate());
		quoteOrderEntity.setBranchId(quoteOrder.getBranch().getBranchId());
		quoteOrderEntity.setClientId(quoteOrder.getClient().getClientId());
		quoteOrderEntity.setUserId(quoteOrder.getUser().getUserId());
		quoteOrderEntity.setSubTotal(quoteOrder.getSubTotal());
		quoteOrderEntity.setIvaTotal(quoteOrder.getIvaTotal());
		quoteOrderEntity.setTotal(quoteOrder.getTotal());
		quoteOrderEntity.setObservation(quoteOrder.getObservation());
		quoteOrderEntity.setIsConverter(quoteOrder.getIsConverter());
		quoteOrderEntity.setIsEffective(quoteOrder.getIsEffective());

		return quoteOrderEntity;
	}

	public QuoteOrder entityToQuoteOrder(QuoteOrderEntity quoteOrderEntity) {

		QuoteOrder quoteOrder = new QuoteOrder();

		quoteOrder.setQuoteOrderId(quoteOrderEntity.getQuoteOrderId());
		quoteOrder.setFolio(quoteOrderEntity.getFolio());
		quoteOrder.setCreationDate(quoteOrderEntity.getCreationDate());
		quoteOrder.setEffectiveDate(quoteOrderEntity.getEffectiveDate());
		quoteOrder.setSubTotal(quoteOrderEntity.getSubTotal());
		quoteOrder.setIvaTotal(quoteOrderEntity.getIvaTotal());
		quoteOrder.setTotal(quoteOrderEntity.getTotal());
		quoteOrder.setObservation(quoteOrderEntity.getObservation());
		quoteOrder.setIsConverter(quoteOrderEntity.getIsConverter());
		quoteOrder.setIsEffective(quoteOrderEntity.getIsEffective());

		return quoteOrder;
	}

	public List<QuoteOrderEntity> quoteOrderListToEntityList(List<QuoteOrder> quoteOrderList) {
		return quoteOrderList.stream().map(this::quoteOrderToEntity).collect(Collectors.toList());
	}

	public List<QuoteOrder> entityListToQuoteOrderList(List<QuoteOrderEntity> quoteOrderEntityList) {
		return quoteOrderEntityList.stream().map(this::entityToQuoteOrder).collect(Collectors.toList());
	}
}
