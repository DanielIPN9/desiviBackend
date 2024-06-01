package mx.com.desivecore.domain.quarantine.models;

public class ProductQuarantine {

	private Long productQuarantineId;

	private Long branchId;

	private Long productId;

	private Double amount;

	public ProductQuarantine() {
		super();
	}

	public ProductQuarantine(Long branchId, Long productId, Double amount) {
		super();
		this.branchId = branchId;
		this.productId = productId;
		this.amount = amount;
	}

	public Long getProductQuarantineId() {
		return productQuarantineId;
	}

	public void setProductQuarantineId(Long productQuarantineId) {
		this.productQuarantineId = productQuarantineId;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
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
		return "ProductQuarantine [productQuarantineId=" + productQuarantineId + ", branchId=" + branchId
				+ ", productId=" + productId + ", amount=" + amount + "]";
	}

}
