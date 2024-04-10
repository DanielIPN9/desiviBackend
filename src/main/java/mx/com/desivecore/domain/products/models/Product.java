package mx.com.desivecore.domain.products.models;

import java.util.List;

public class Product {

	private Long productId;

	private String sku;

	private String name;

	private String unitMeasure;

	private Double iva;

	private Double unitSellingPrice;

	private Double unitPurchasePrice;

	private List<ProductAvailability> availability;

	private boolean status;

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

	public Double getIva() {
		return iva;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}

	public Double getUnitSellingPrice() {
		return unitSellingPrice;
	}

	public void setUnitSellingPrice(Double unitSellingPrice) {
		this.unitSellingPrice = unitSellingPrice;
	}

	public Double getUnitPurchasePrice() {
		return unitPurchasePrice;
	}

	public void setUnitPurchasePrice(Double unitPurchasePrice) {
		this.unitPurchasePrice = unitPurchasePrice;
	}

	public List<ProductAvailability> getAvailability() {
		return availability;
	}

	public void setAvailability(List<ProductAvailability> availability) {
		this.availability = availability;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", sku=" + sku + ", name=" + name + ", unitMeasure=" + unitMeasure
				+ ", iva=" + iva + ", unitSellingPrice=" + unitSellingPrice + ", unitPurchasePrice=" + unitPurchasePrice
				+ ", availability=" + availability + ", status=" + status + "]";
	}

}
