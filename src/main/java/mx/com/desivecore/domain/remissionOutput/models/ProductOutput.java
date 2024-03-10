package mx.com.desivecore.domain.remissionOutput.models;

import mx.com.desivecore.domain.products.models.ProductOutputSummary;

public class ProductOutput {

	private Long id;

	private ProductOutputSummary product;

	private Double amount;

	private String unitMeasure;

	private Double sellingPrice;

	private Double iva;

	private Double net;

	private Double total;

	public void generateRemissionSummary() {
		net = amount * sellingPrice;
		net = (double) Math.round(net * 100) / 100;
		total = net * (1 + iva);
		total = (double) Math.round(total * 100) / 100;
	}

	public ProductOutputSummary getProduct() {
		return product;
	}

	public void setProduct(ProductOutputSummary product) {
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

	public Double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ProductOutput [product=" + product + ", amount=" + amount + ", unitMeasure=" + unitMeasure
				+ ", sellingPrice=" + sellingPrice + ", iva=" + iva + ", net=" + net + ", total=" + total + "]";
	}

}
