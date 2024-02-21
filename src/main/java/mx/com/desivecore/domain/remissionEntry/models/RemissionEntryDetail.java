package mx.com.desivecore.domain.remissionEntry.models;

public class RemissionEntryDetail {

	private Double amount;

	private String unitMeasure;

	private String sku;

	private String productName;

	private Double unitPrice;

	private Double subTotal;

	public RemissionEntryDetail(ProductEntry reEntry) {

		amount = reEntry.getAmount();
		unitMeasure = reEntry.getUnitMeasure();
		sku = reEntry.getProduct().getSku();
		productName = reEntry.getProduct().getName();
		unitPrice = reEntry.getPurchaseUnitPrice();
		subTotal = reEntry.getNet();
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

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	@Override
	public String toString() {
		return "RemissionEntryDetail [amount=" + amount + ", unitMeasure=" + unitMeasure + ", sku=" + sku
				+ ", productName=" + productName + ", unitPrice=" + unitPrice + ", subTotal=" + subTotal + "]";
	}

}
