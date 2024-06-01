package mx.com.desivecore.domain.quarantine.models;

public class ProductQuarantineSummary {

	private Long productQuarantineId;

	private String branchName;

	private String productName;

	private Long productId;

	private Double amount;

	public ProductQuarantineSummary(Long productQuarantineId, String branchName, String productName, Long productId,
			Double amount) {
		super();
		this.productQuarantineId = productQuarantineId;
		this.branchName = branchName;
		this.productName = productName;
		this.productId = productId;
		this.amount = amount;
	}

	public Long getProductQuarantineId() {
		return productQuarantineId;
	}

	public void setProductQuarantineId(Long productQuarantineId) {
		this.productQuarantineId = productQuarantineId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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
		return "ProductQuarantineSummary [productQuarantineId=" + productQuarantineId + ", branchName=" + branchName
				+ ", productName=" + productName + ", productId=" + productId + ", amount=" + amount + "]";
	}

}
