package mx.com.desivecore.domain.products.models;

import mx.com.desivecore.domain.branches.models.Branch;

public class ProductAvailability {

	private Long id;

	private Branch branch;

	private Long productId;

	private Double amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "ProductAvailability [id=" + id + ", branch=" + branch + ", productId=" + productId + ", amount="
				+ amount + "]";
	}

}
