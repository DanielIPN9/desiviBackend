package mx.com.desivecore.domain.quarantine.models;

import mx.com.desivecore.domain.branches.models.BranchSummary;
import mx.com.desivecore.domain.products.models.ProductSummary;

public class ProductQuarantineAction {

	private BranchSummary branch;

	private ProductSummary product;

	private Double amount;

	private QuarantineAction action;

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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public QuarantineAction getAction() {
		return action;
	}

	public void setAction(QuarantineAction action) {
		this.action = action;
	}

	@Override
	public String toString() {
		return "ProductQuarantineAction [branch=" + branch + ", product=" + product + ", amount=" + amount + ", action="
				+ action + "]";
	}

}
