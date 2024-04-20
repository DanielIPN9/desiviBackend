package mx.com.desivecore.domain.productTag.models;

import java.util.Date;

import mx.com.desivecore.domain.branches.models.BranchPhone;
import mx.com.desivecore.domain.branches.models.BranchSummary;
import mx.com.desivecore.domain.products.models.Product;

public class ProductTag {

	private Long tagId;

	private Product product;

	private String lot;

	private Date creationDate;

	private Double netWeight;

	private String um;

	private BranchSummary branch;

	private BranchPhone phone;

	private String urlSite;

	private String phoneNumber;

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

	public Double getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}

	public String getUm() {
		return um;
	}

	public void setUm(String um) {
		this.um = um;
	}

	public BranchSummary getBranch() {
		return branch;
	}

	public void setBranch(BranchSummary branch) {
		this.branch = branch;
	}

	public BranchPhone getPhone() {
		return phone;
	}

	public void setPhone(BranchPhone phone) {
		this.phone = phone;
	}

	public String getUrlSite() {
		return urlSite;
	}

	public void setUrlSite(String urlSite) {
		this.urlSite = urlSite;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "ProductTag [tagId=" + tagId + ", product=" + product + ", lot=" + lot + ", creationDate=" + creationDate
				+ ", netWeight=" + netWeight + ", um=" + um + ", branch=" + branch + ", phone=" + phone + ", urlSite="
				+ urlSite + ", phoneNumber=" + phoneNumber + "]";
	}

}
