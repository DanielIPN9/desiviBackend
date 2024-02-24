package mx.com.desivecore.domain.productTag.models;

public class ProductTagSummary {

	private Long tagId;

	private String lot;

	private String productName;

	public ProductTagSummary(Long tagId, String lot, String productName) {
		super();
		this.tagId = tagId;
		this.lot = lot;
		this.productName = productName;
	}

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Override
	public String toString() {
		return "ProductTagSummary [tagId=" + tagId + ", lot=" + lot + ", productName=" + productName + "]";
	}

}
