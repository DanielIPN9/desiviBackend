package mx.com.desivecore.domain.reports.models;

import java.util.List;

public class InventorySearch {

	private String branchName;

	private List<ProductDetail> productDetail;

	public InventorySearch(String branchName, List<ProductDetail> productDetailSummary) {
		this.branchName = branchName;
		productDetail = productDetailSummary;
		for (ProductDetail productDetail : productDetail) {
			productDetail.generateSummary();
		}
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public List<ProductDetail> getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(List<ProductDetail> productDetail) {
		this.productDetail = productDetail;
	}

	@Override
	public String toString() {
		return "InventorySearch [branchName=" + branchName + ", productDetail=" + productDetail + "]";
	}

}