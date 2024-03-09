package mx.com.desivecore.domain.certificate.models;

import mx.com.desivecore.domain.products.models.Product;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntrySummary;

public class RemissionSummary {

	private Long remissionEntryId;

	private Long productId;

	private String remissionFolio;

	private String productName;

	private String sku;

	private Long certificateId;

	public RemissionSummary(RemissionEntrySummary remissionEntrySummary, Product product, Long certificateId) {
		this.remissionEntryId = remissionEntrySummary.getRemissionEntryId();
		this.productId = product.getProductId();
		this.remissionFolio = remissionEntrySummary.getFolio();
		this.productName = product.getName();
		this.sku = product.getSku();
		this.certificateId = certificateId;
	}

	public Long getRemissionEntryId() {
		return remissionEntryId;
	}

	public void setRemissionEntryId(Long remissionEntryId) {
		this.remissionEntryId = remissionEntryId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getRemissionFolio() {
		return remissionFolio;
	}

	public void setRemissionFolio(String remissionFolio) {
		this.remissionFolio = remissionFolio;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Long getCertificateId() {
		return certificateId;
	}

	public void setCertificateId(Long certificateId) {
		this.certificateId = certificateId;
	}

	@Override
	public String toString() {
		return "RemissionSummary [remissionEntryId=" + remissionEntryId + ", productId=" + productId
				+ ", remissionFolio=" + remissionFolio + ", productName=" + productName + ", sku=" + sku
				+ ", certificateId=" + certificateId + "]";
	}

}
