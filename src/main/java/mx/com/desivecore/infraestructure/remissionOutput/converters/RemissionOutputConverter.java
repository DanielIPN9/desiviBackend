package mx.com.desivecore.infraestructure.remissionOutput.converters;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.remissionOutput.models.RemissionOutput;
import mx.com.desivecore.infraestructure.remissionOutput.entities.RemissionOutputEntity;

@Component
public class RemissionOutputConverter {

	public RemissionOutputEntity remissionOutputToEntity(RemissionOutput remissionOutput) {

		RemissionOutputEntity remissionOutputEntity = new RemissionOutputEntity();

		remissionOutputEntity.setRemissionOutputId(remissionOutput.getRemissionOutputId());
		remissionOutputEntity.setFolio(remissionOutput.getFolio());
		remissionOutputEntity.setCreationDate(remissionOutput.getCreationDate());
		remissionOutputEntity.setRequestDay(remissionOutput.getRequestDay());
		remissionOutputEntity.setBranchId(remissionOutput.getBranch().getBranchId());
		remissionOutputEntity.setClientId(remissionOutput.getClient().getClientId());
		remissionOutputEntity.setUserId(remissionOutput.getUser().getUserId());
		remissionOutputEntity.setIvaTotal(remissionOutput.getIvaTotal());
		remissionOutputEntity.setRemissionTotal(remissionOutput.getRemissionTotal());
		remissionOutputEntity.setStatus(remissionOutput.isStatus());

		return remissionOutputEntity;
	}

	public RemissionOutput entityToRemissionOutput(RemissionOutputEntity remissionOutputEntity) {

		RemissionOutput remissionOutput = new RemissionOutput();

		remissionOutput.setRemissionOutputId(remissionOutputEntity.getRemissionOutputId());
		remissionOutput.setFolio(remissionOutputEntity.getFolio());
		remissionOutput.setCreationDate(remissionOutputEntity.getCreationDate());
		remissionOutput.setRequestDay(remissionOutputEntity.getRequestDay());
		remissionOutput.setIvaTotal(remissionOutputEntity.getIvaTotal());
		remissionOutput.setRemissionTotal(remissionOutputEntity.getRemissionTotal());
		remissionOutput.setStatus(remissionOutputEntity.getStatus());

		return remissionOutput;
	}

}
