package mx.com.desivecore.domain.products.models;

public class ProductOutputSummary {

	private Long productId;

	private String sku;

	private String productName;

	private Double amount;

	private Double sellingUnitPrice;

	private Double iva;

	private String unitMeasure;
	
	public ProductOutputSummary(Long productId, String sku, String productName, Double amount, Double sellingUnitPrice,
			Double iva, String unitMeasure) {
		super();
		this.productId = productId;
		this.sku = sku;
		this.productName = productName;
		this.amount = amount;
		this.sellingUnitPrice = sellingUnitPrice;
		this.iva = iva;
		this.unitMeasure = unitMeasure;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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

	public String getUnitMeasure() {
		return unitMeasure;
	}

	public void setUnitMeasure(String unitMeasure) {
		this.unitMeasure = unitMeasure;
	}

	@Override
	public String toString() {
		return "ProductOutputSummary [productId=" + productId + ", sku=" + sku + ", productName=" + productName
				+ ", amount=" + amount + ", sellingUnitPrice=" + sellingUnitPrice + ", iva=" + iva + ", unitMeasure="
				+ unitMeasure + "]";
	}



}
