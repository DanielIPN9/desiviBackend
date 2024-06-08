package mx.com.desivecore.infraestructure.returnRemissionOutput.converters;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.returnRemissionOutput.models.ReturnProductOutput;
import mx.com.desivecore.infraestructure.returnRemissionOutput.entities.ReturnProductOutputEntity;

@Component
public class ReturnProductOutputConverter {

	public ReturnProductOutputEntity returnProductOutputToEntity(ReturnProductOutput returnProductOutput,
			Long returnROId) {
		ReturnProductOutputEntity returnProductOutputEntity = new ReturnProductOutputEntity();

		returnProductOutputEntity.setReturnROId(returnROId);
		returnProductOutputEntity.setProductId(returnProductOutput.getProduct().getProductId());
		returnProductOutputEntity.setAmountReturn(returnProductOutput.getAmountReturn());
		returnProductOutputEntity.setUnitMeasure(returnProductOutput.getUnitMeasure());
		returnProductOutputEntity.setSellingUnitPrice(returnProductOutput.getSellingUnitPrice());
		returnProductOutputEntity.setIva(returnProductOutput.getIva());
		returnProductOutputEntity.setNet(returnProductOutput.getNet());
		returnProductOutputEntity.setTotal(returnProductOutput.getTotal());

		return returnProductOutputEntity;
	}

	public ReturnProductOutput entityToReturnProductOutput(ReturnProductOutputEntity returnProductOutputEntity) {

		ReturnProductOutput returnProductOutput = new ReturnProductOutput();

		returnProductOutput.setAmountReturn(returnProductOutputEntity.getAmountReturn());
		returnProductOutput.setUnitMeasure(returnProductOutputEntity.getUnitMeasure());
		returnProductOutput.setSellingUnitPrice(returnProductOutputEntity.getSellingUnitPrice());
		returnProductOutput.setIva(returnProductOutputEntity.getIva());
		returnProductOutput.setNet(returnProductOutputEntity.getNet());
		returnProductOutput.setTotal(returnProductOutputEntity.getTotal());

		return returnProductOutput;
	}
}
