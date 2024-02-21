package mx.com.desivecore.domain.remissionEntry.models;

import mx.com.desivecore.domain.products.models.Product;

public class ProductEntry {

	private Long id;

	private Product product;

	private Double amount;

	private String unitMeasure;

	private Double purchaseUnitPrice;

	private Double iva;

	private Double net;

	private Double total;

	public void generateRemissionSummary() {
		net = amount * purchaseUnitPrice;
		net = (double) Math.round(net * 100) / 100;
		total = net * (1 + iva);
		total = (double) Math.round(total * 100) / 100;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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
		return "ProductEntry [id=" + id + ", product=" + product + ", amount=" + amount + ", unitMeasure=" + unitMeasure
				+ ", purchaseUnitPrice=" + purchaseUnitPrice + ", iva=" + iva + ", net=" + net + ", total=" + total
				+ "]";
	}

}
