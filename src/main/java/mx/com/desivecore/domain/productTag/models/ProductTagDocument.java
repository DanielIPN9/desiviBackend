package mx.com.desivecore.domain.productTag.models;

import java.io.InputStream;
import java.text.SimpleDateFormat;

public class ProductTagDocument {

	private InputStream tag1;

	private InputStream tag2;

	private InputStream tag3;

	private InputStream tag4;

	private String productName;

	private Double netWeight;

	private String um;

	private String urlSite;

	private String fullAddress;

	private String phoneNumber;

	private String lot;

	private String inputDate;

	public ProductTagDocument() {
		super();
	}

	public ProductTagDocument(ProductTag productTag) {
		super();
		this.productName = productTag.getProduct().getName();
		this.netWeight = productTag.getNetWeight();
		this.urlSite = productTag.getUrlSite();
		this.fullAddress = productTag.getFullAddress();
		this.phoneNumber = productTag.getPhoneNumber();
		this.lot = productTag.getLot();
		this.um = productTag.getUm();
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		this.inputDate = simpleDateFormat.format(productTag.getCreationDate());
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}

	public String getUrlSite() {
		return urlSite;
	}

	public void setUrlSite(String urlSite) {
		this.urlSite = urlSite;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public String getInputDate() {
		return inputDate;
	}

	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}

	public String getUm() {
		return um;
	}

	public void setUm(String um) {
		this.um = um;
	}

	public InputStream getTag1() {
		return tag1;
	}

	public void setTag1(InputStream tag1) {
		this.tag1 = tag1;
	}

	public InputStream getTag2() {
		return tag2;
	}

	public void setTag2(InputStream tag2) {
		this.tag2 = tag2;
	}

	public InputStream getTag3() {
		return tag3;
	}

	public void setTag3(InputStream tag3) {
		this.tag3 = tag3;
	}

	public InputStream getTag4() {
		return tag4;
	}

	public void setTag4(InputStream tag4) {
		this.tag4 = tag4;
	}

	@Override
	public String toString() {
		return "ProductTagDocument [productName=" + productName + ", netWeight=" + netWeight + ", um=" + um
				+ ", urlSite=" + urlSite + ", fullAddress=" + fullAddress + ", phoneNumber=" + phoneNumber + ", lot="
				+ lot + ", inputDate=" + inputDate + "]";
	}

}
