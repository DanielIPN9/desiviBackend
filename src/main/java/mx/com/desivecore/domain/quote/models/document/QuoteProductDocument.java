package mx.com.desivecore.domain.quote.models.document;

import mx.com.desivecore.domain.quote.models.QuoteProduct;

public class QuoteProductDocument {

	private Double amount;

	private String unitMeasure;

	private String description;

	private Double unitSellingPrice;

	private Double subTotal;

	public QuoteProductDocument(QuoteProduct quoteProduct) {

		this.amount = quoteProduct.getAmount();
		this.unitMeasure = quoteProduct.getUnitMeasure();
		this.description = quoteProduct.getProduct().getName() + "-" + quoteProduct.getProductDescription();
		this.unitSellingPrice = quoteProduct.getSellingPrice();
		this.subTotal = quoteProduct.getNet();
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getUnitSellingPrice() {
		return unitSellingPrice;
	}

	public void setUnitSellingPrice(Double unitSellingPrice) {
		this.unitSellingPrice = unitSellingPrice;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	@Override
	public String toString() {
		return "QuoteProductDocument [amount=" + amount + ", unitMeasure=" + unitMeasure + ", description="
				+ description + ", unitSellingPrice=" + unitSellingPrice + ", subTotal=" + subTotal + "]";
	}

}
