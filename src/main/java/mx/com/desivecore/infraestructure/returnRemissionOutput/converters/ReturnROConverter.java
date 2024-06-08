package mx.com.desivecore.infraestructure.returnRemissionOutput.converters;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.returnRemissionOutput.models.ReturnRemissionOutput;
import mx.com.desivecore.infraestructure.returnRemissionOutput.entities.ReturnRemissionOutputEntity;

@Component
public class ReturnROConverter {

	public ReturnRemissionOutputEntity returnRemissionOutputToEntity(ReturnRemissionOutput returnRemissionOutput) {

		ReturnRemissionOutputEntity returnRemissionOutputEntity = new ReturnRemissionOutputEntity();

		returnRemissionOutputEntity.setReturnROId(returnRemissionOutput.getReturnROId());
		returnRemissionOutputEntity.setFolio(returnRemissionOutput.getFolio());
		returnRemissionOutputEntity.setCreationDate(returnRemissionOutput.getCreationDate());
		returnRemissionOutputEntity.setBranchId(returnRemissionOutput.getBranch().getBranchId());
		returnRemissionOutputEntity.setClientId(returnRemissionOutput.getClient().getClientId());
		returnRemissionOutputEntity.setUserId(returnRemissionOutput.getUser().getUserId());
		returnRemissionOutputEntity.setObserbations(returnRemissionOutput.getObserbations());
		returnRemissionOutputEntity.setIva(returnRemissionOutput.getIva());
		returnRemissionOutputEntity.setSubTotal(returnRemissionOutput.getSubTotal());
		returnRemissionOutputEntity.setTotal(returnRemissionOutput.getTotal());

		return returnRemissionOutputEntity;
	}

	public ReturnRemissionOutput entityToReturnRemissionOutput(
			ReturnRemissionOutputEntity returnRemissionOutputEntity) {

		ReturnRemissionOutput returnRemissionOutput = new ReturnRemissionOutput();

		returnRemissionOutput.setReturnROId(returnRemissionOutputEntity.getReturnROId());
		returnRemissionOutput.setFolio(returnRemissionOutputEntity.getFolio());
		returnRemissionOutput.setCreationDate(returnRemissionOutputEntity.getCreationDate());
		returnRemissionOutput.setObserbations(returnRemissionOutputEntity.getObserbations());
		returnRemissionOutput.setIva(returnRemissionOutputEntity.getIva());
		returnRemissionOutput.setSubTotal(returnRemissionOutputEntity.getSubTotal());
		returnRemissionOutput.setTotal(returnRemissionOutputEntity.getTotal());

		return returnRemissionOutput;
	}

}
