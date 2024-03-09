package mx.com.desivecore.domain.certificate.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.com.desivecore.domain.remissionEntry.models.ProductEntry;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntry;

public class ProductCertificate {

	private Long certificateId;

	private Long remissionEntryId;

	private Long productId;

	private String productName;

	private String sku;

	private String clientName;

	private String lot;

	private Date creationDate;

	private List<CertificateDetail> certificateDetail;

	public ProductCertificate() {
		super();
	}

	public ProductCertificate(RemissionEntry remissionEntry, Long productId) {
		this.certificateId = null;
		this.productId = productId;
		this.remissionEntryId = remissionEntry.getRemissionEntryId();
		for (ProductEntry productEntry : remissionEntry.getProducts()) {
			if (productEntry.getProduct().getProductId() == productId) {
				this.productName = productEntry.getProduct().getName();
				this.sku = productEntry.getProduct().getSku();
				break;
			}
		}
		this.clientName = "";
		this.lot = this.sku + "-";
		this.creationDate = null;
		this.certificateDetail = new ArrayList<>();
	}

	public Long getCertificateId() {
		return certificateId;
	}

	public void setCertificateId(Long certificateId) {
		this.certificateId = certificateId;
	}

	public Long getRemissionEntryId() {
		return remissionEntryId;
	}

	public void setRemissionEntryId(Long remissionEntryId) {
		this.remissionEntryId = remissionEntryId;
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

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public List<CertificateDetail> getCertificateDetail() {
		return certificateDetail;
	}

	public void setCertificateDetail(List<CertificateDetail> certificateDetail) {
		this.certificateDetail = certificateDetail;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	
	@Override
	public String toString() {
		return "ProductCertificate [certificateId=" + certificateId + ", remissionEntryId=" + remissionEntryId
				+ ", productId=" + productId + ", productName=" + productName + ", sku=" + sku + ", clientName="
				+ clientName + ", lot=" + lot + ", creationDate=" + creationDate + ", certificateDetail="
				+ certificateDetail + "]";
	}

}
