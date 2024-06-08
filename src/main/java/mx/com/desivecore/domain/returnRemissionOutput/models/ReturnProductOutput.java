package mx.com.desivecore.domain.returnRemissionOutput.models;

import mx.com.desivecore.domain.products.models.ProductOutputSummary;
import mx.com.desivecore.domain.remissionOutput.models.ProductOutput;

public class ReturnProductOutput {

	private ProductOutputSummary product;

	private Double amountReturn;

	private String unitMeasure;

	private Double sellingUnitPrice;

	private Double iva;

	private Double net;

	private Double total;

	public ReturnProductOutput() {
		super();
	}

	public ReturnProductOutput(ProductOutput productOutput) {
		this.product = productOutput.getProduct();
		this.amountReturn = productOutput.getAmount();
		this.unitMeasure = productOutput.getUnitMeasure();
		this.sellingUnitPrice = productOutput.getSellingPrice();
		this.iva = productOutput.getIva();

		Double net = productOutput.getAmount() * productOutput.getSellingPrice();
		net = (double) Math.round(net * 100) / 100;

		Double total = net * (1 + productOutput.getIva());
		total = (double) Math.round(total * 100) / 100;

		this.net = net;
		this.total = total;
	}

	public Double getAmountReturn() {
		return amountReturn;
	}

	public void setAmountReturn(Double amountReturn) {
		this.amountReturn = amountReturn;
	}

	public String getUnitMeasure() {
		return unitMeasure;
	}

	public void setUnitMeasure(String unitMeasure) {
		this.unitMeasure = unitMeasure;
	}

	public Double getSellingUnitPrice() {
		return sellingUnitPrice;
	}

	public void setSellingUnitPrice(Double sellingUnitPrice) {
		this.sellingUnitPrice = sellingUnitPrice;
	}

	public Double getIva() {
		return iva;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}

	public Double getNet() {
		return net;
	}

	public void setNet(Double net) {
		this.net = net;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public ProductOutputSummary getProduct() {
		return product;
	}

	public void setProduct(ProductOutputSummary product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "ReturnProductOutput [product=" + product + ", amountReturn=" + amountReturn + ", unitMeasure="
				+ unitMeasure + ", sellingUnitPrice=" + sellingUnitPrice + ", iva=" + iva + ", net=" + net + ", total="
				+ total + "]";
	}

}
