package mx.com.desivecore.domain.certificate.models.document;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import mx.com.desivecore.domain.certificate.models.CertificateDetail;
import mx.com.desivecore.domain.certificate.models.ProductCertificate;

public class CertificateDocument {

	private InputStream logo;

	private InputStream sing;

	private String productName;

	private String sku;

	private String creationDate;

	private String lot;

	private String clientName;

	private String description;

	private List<CertificateDetailDocument> certificateDetail;

	public CertificateDocument(ProductCertificate productCertificate) {

		this.productName = productCertificate.getProductName();
		this.sku = productCertificate.getSku();

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		this.creationDate = simpleDateFormat.format(productCertificate.getCreationDate());

		this.lot = productCertificate.getLot();
		this.clientName = productCertificate.getClientName();

		this.description = productCertificate.getDescription() == null ? " " : productCertificate.getDescription();

		certificateDetail = new ArrayList<>();

		for (CertificateDetail certificateD : productCertificate.getCertificateDetail()) {
			certificateDetail.add(new CertificateDetailDocument(certificateD));
		}

	}

	public InputStream getLogo() {
		return logo;
	}

	public void setLogo(InputStream logo) {
		this.logo = logo;
	}

	public InputStream getSing() {
		return sing;
	}

	public void setSing(InputStream sing) {
		this.sing = sing;
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

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public List<CertificateDetailDocument> getCertificateDetail() {
		return certificateDetail;
	}

	public void setCertificateDetail(List<CertificateDetailDocument> certificateDetail) {
		this.certificateDetail = certificateDetail;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "CertificateDocument [logo=" + logo + ", sing=" + sing + ", productName=" + productName + ", sku=" + sku
				+ ", creationDate=" + creationDate + ", lot=" + lot + ", clientName=" + clientName + ", description="
				+ description + ", certificateDetail=" + certificateDetail + "]";
	}

}
