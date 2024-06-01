package mx.com.desivecore.domain.products.models;

public class ProductSummary {

	private Long productId;

	private String sku;

	private String name;

	private String unitMeasure;

	public ProductSummary() {
		super();
	}

	public ProductSummary(Long productId, String sku, String name, String unitMeasure) {
		super();
		this.productId = productId;
		this.sku = sku;
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnitMeasure() {
		return unitMeasure;
	}

	public void setUnitMeasure(String unitMeasure) {
		this.unitMeasure = unitMeasure;
	}

	@Override
	public String toString() {
		return "ProductSummary [productId=" + productId + ", sku=" + sku + ", name=" + name + ", unitMeasure="
				+ unitMeasure + "]";
	}

}
