package mx.com.desivecore.domain.returnRemissionEntry.models;

import mx.com.desivecore.domain.products.models.Product;
import mx.com.desivecore.domain.remissionEntry.models.ProductEntry;

public class ReturnProductEntry {

	private Product product;

	private Double amountReturn;

	private String unitMeasure;

	private Double purchaseUnitPrice;

	private Double iva;

	private Double net;

	private Double total;

	public ReturnProductEntry() {
		super();
	}

	public ReturnProductEntry(ProductEntry productEntry) {

		this.product = productEntry.getProduct();
		this.amountReturn = productEntry.getAmount();
		this.unitMeasure = productEntry.getUnitMeasure();
		this.purchaseUnitPrice = productEntry.getPurchaseUnitPrice();
		this.iva = productEntry.getIva();

		Double net = productEntry.getAmount() * productEntry.getPurchaseUnitPrice();
		net = (double) Math.round(net * 100) / 100;

		Double total = net * (1 + productEntry.getIva());
		total = (double) Math.round(total * 100) / 100;

		this.net = net;
		this.total = total;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

	public Double getPurchaseUnitPrice() {
		return purchaseUnitPrice;
	}

	public void setPurchaseUnitPrice(Double purchaseUnitPrice) {
		this.purchaseUnitPrice = purchaseUnitPrice;
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

	@Override
	public String toString() {
		return "ReturnProductEntry [product=" + product + ", amountReturn=" + amountReturn + ", unitMeasure="
				+ unitMeasure + ", purchaseUnitPrice=" + purchaseUnitPrice + ", iva=" + iva + ", net=" + net
				+ ", total=" + total + "]";
	}

}
