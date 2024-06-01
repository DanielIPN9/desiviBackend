package mx.com.desivecore.domain.quarantine.models;

import mx.com.desivecore.domain.branches.models.BranchSummary;
import mx.com.desivecore.domain.products.models.ProductSummary;

public class QuarantineSearchParams {

	private BranchSummary branch;

	private ProductSummary product;

	public BranchSummary getBranch() {
		return branch;
	}

	public void setBranch(BranchSummary branch) {
		this.branch = branch;
	}

	public ProductSummary getProduct() {
		return product;
	}

	public void setProduct(ProductSummary product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "QuarantineSearchParams [branch=" + branch + ", product=" + product + "]";
	}

}
