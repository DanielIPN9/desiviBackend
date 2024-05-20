package mx.com.desivecore.domain.quote;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mx.com.desivecore.commons.utils.StringUtil;
import mx.com.desivecore.domain.quote.models.QuoteOrder;
import mx.com.desivecore.domain.quote.models.QuoteProduct;
import mx.com.desivecore.domain.remissionOutput.models.ProductOutput;
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutput;
import mx.com.desivecore.domain.remissionOutput.ports.RemissionOutputPersistencePort;

public class QuoteBusinessConvert {

	private static final String SERIAL_NUMBER_CODE_RO = "RO";

	public RemissionOutput generateRemissionOutPut(QuoteOrder quoteOrder,
			RemissionOutputPersistencePort remissionOutputPersistencePort, StringUtil stringUtil) {

		BigInteger serialNumber = remissionOutputPersistencePort.getConsecutiveByCode(SERIAL_NUMBER_CODE_RO);
		String serial = String.valueOf(serialNumber.longValue());
		serial = stringUtil.autocompleteSpace(serial, 5, false);

		RemissionOutput remissionOutput = new RemissionOutput();

		Calendar calendar = Calendar.getInstance();
		remissionOutput.setCreationDate(calendar.getTime());

		remissionOutput.setFolio("RS DIV " + calendar.get(Calendar.DAY_OF_MONTH) + "-" + serial);
		remissionOutput.setRequestDay(calendar.getTime());
		remissionOutput.setBranch(quoteOrder.getBranch());
		remissionOutput.setClient(quoteOrder.getClient());
		remissionOutput.setUser(quoteOrder.getUser());
		remissionOutput.setIvaTotal(quoteOrder.getIvaTotal());
		remissionOutput.setRemissionTotal(quoteOrder.getTotal());
		remissionOutput.setStatus(true);

		List<ProductOutput> productOutputList = new ArrayList<>();
		for (QuoteProduct quoteProduct : quoteOrder.getProducts()) {
			productOutputList.add(new ProductOutput(quoteProduct));
		}

		remissionOutput.setProducts(productOutputList);

		return remissionOutput;
	}

}
