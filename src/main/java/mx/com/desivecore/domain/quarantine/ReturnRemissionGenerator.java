package mx.com.desivecore.domain.quarantine;

import java.util.List;
import java.util.stream.Collectors;

import mx.com.desivecore.domain.quarantine.models.ProductQuarantine;
import mx.com.desivecore.domain.returnRemissionEntry.models.ReturnRemissionEntry;

/**
 * Clase generadora de datos operativos
 * 
 */
public class ReturnRemissionGenerator {

	/**
	 * Método que genera la lista de objetos {@link ProductQuarantine} para ser
	 * procesados y actualizar el infventario de cuarentena con base en las
	 * devoluciones de remisión de entrada
	 * 
	 * @param returnRemissionEntry objeto con los datos de devolución de remisión de
	 *                             entrada
	 * @return List<ProductQuarantine>
	 */
	public List<ProductQuarantine> generateByRemissionEntry(ReturnRemissionEntry returnRemissionEntry) {
		List<ProductQuarantine> productQuarantineList = returnRemissionEntry.getProducts().stream()
				.map(returnProductEntry -> new ProductQuarantine(returnRemissionEntry.getBranch().getBranchId(),
						returnProductEntry.getProduct().getProductId(), returnProductEntry.getAmountReturn()))
				.collect(Collectors.toList());
		return productQuarantineList;
	}

}
