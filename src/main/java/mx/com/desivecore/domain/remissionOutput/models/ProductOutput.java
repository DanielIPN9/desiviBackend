package mx.com.desivecore.domain.remissionOutput.models;

import mx.com.desivecore.domain.products.models.Product;
import mx.com.desivecore.domain.products.models.ProductOutputSummary;
import mx.com.desivecore.domain.quote.models.QuoteProduct;

public class ProductOutput {

	private Long id;

	private ProductOutputSummary product;

	private String productDescription;

	private Double amount;

	private String unitMeasure;

	private Double sellingPrice;

	private Double iva;

	private Double net;

	private Double total;

	public ProductOutput() {
		super();
	}

	public ProductOutput(QuoteProduct quoteProduct) {
		Product product = quoteProduct.getProduct();
		this.product = new ProductOutputSummary(product.getProductId(), product.getSku(), product.getName(), null, null,
				null, product.getUnitMeasure());
		this.productDescription = quoteProduct.getProductDescription();
		this.amount = quoteProduct.getAmount();
		this.unitMeasure = quoteProduct.getUnitMeasure();
		this.sellingPrice = quoteProduct.getSellingPrice();
		this.iva = quoteProduct.getIva();
		this.net = quoteProduct.getNet();
		this.total = quoteProduct.getTotal();
	}

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

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	@Override
	public String toString() {
		return "ProductOutput [id=" + id + ", product=" + product + ", productDescription=" + productDescription
				+ ", amount=" + amount + ", unitMeasure=" + unitMeasure + ", sellingPrice=" + sellingPrice + ", iva="
				+ iva + ", net=" + net + ", total=" + total + "]";
	}

}
