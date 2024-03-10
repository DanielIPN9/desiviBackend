package mx.com.desivecore.infraestructure.remissionOutput.converters;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.remissionOutput.models.ProductOutput;
import mx.com.desivecore.infraestructure.remissionOutput.entities.ProductOutputEntity;

@Component
public class ProductOutputConverter {

	public ProductOutputEntity productOutputToEntity(ProductOutput productOutput, Long remissionOutputId) {

		ProductOutputEntity productOutputEntity = new ProductOutputEntity();

		productOutputEntity.setId(productOutput.getId());
		productOutputEntity.setRemissionOutputId(remissionOutputId);
		productOutputEntity.setProductId(productOutput.getProduct().getProductId());
		productOutputEntity.setAmount(productOutput.getAmount());
		productOutputEntity.setUnitMeasure(productOutput.getUnitMeasure());
		productOutputEntity.setSellingPrice(productOutput.getSellingPrice());
		productOutputEntity.setIva(productOutput.getIva());
		productOutputEntity.setNet(productOutput.getNet());
		productOutputEntity.setTotal(productOutput.getTotal());

		return productOutputEntity;
	}

	public ProductOutput entityToProductOutput(ProductOutputEntity productOutputEntity) {

		ProductOutput productOutput = new ProductOutput();

		productOutput.setId(productOutputEntity.getId());
		productOutput.setAmount(productOutputEntity.getAmount());
		productOutput.setUnitMeasure(productOutputEntity.getUnitMeasure());
		productOutput.setSellingPrice(productOutputEntity.getSellingPrice());
		productOutput.setIva(productOutputEntity.getIva());
		productOutput.setNet(productOutputEntity.getNet());
		productOutput.setTotal(productOutputEntity.getTotal());

		return productOutput;
	}
}
