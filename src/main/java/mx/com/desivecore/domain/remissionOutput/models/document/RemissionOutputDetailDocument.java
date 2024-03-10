package mx.com.desivecore.domain.remissionOutput.models.document;

import mx.com.desivecore.domain.remissionOutput.models.ProductOutput;

public class RemissionOutputDetailDocument {

	private String sku;

	private String productName;

	private Double amount;

	private String unitMeasure;

	private Double sellingPrice;

	private Double subTotal;

	public RemissionOutputDetailDocument(ProductOutput productOutput) {
		sku = productOutput.getProduct().getSku();
		productName = productOutput.getProduct().getProductName();
		amount = productOutput.getAmount();
		unitMeasure = productOutput.getUnitMeasure();
		sellingPrice = productOutput.getSellingPrice();
		subTotal = productOutput.getNet();
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

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	@Override
	public String toString() {
		return "RemissionOutputDetailDocument [sku=" + sku + ", productName=" + productName + ", amount=" + amount
				+ ", unitMeasure=" + unitMeasure + ", sellingPrice=" + sellingPrice + ", subTotal=" + subTotal + "]";
	}

}
