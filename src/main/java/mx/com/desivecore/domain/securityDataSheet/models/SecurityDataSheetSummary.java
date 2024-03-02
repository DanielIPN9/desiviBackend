package mx.com.desivecore.domain.securityDataSheet.models;

public class SecurityDataSheetSummary {

	private Long securityDataSheetId;

	private String productSku;

	private String productName;

	private String tradeName;

	public SecurityDataSheetSummary(Long securityDataSheetId, String productSku, String productName, String tradeName) {
		super();
		this.securityDataSheetId = securityDataSheetId;
		this.productSku = productSku;
		this.productName = productName;
		this.tradeName = tradeName;
	}

	public Long getSecurityDataSheetId() {
		return securityDataSheetId;
	}

	public void setSecurityDataSheetId(Long securityDataSheetId) {
		this.securityDataSheetId = securityDataSheetId;
	}

	public String getProductSku() {
		return productSku;
	}

	public void setProductSku(String productSku) {
		this.productSku = productSku;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	@Override
	public String toString() {
		return "SecurityDataSheetSummary [securityDataSheetId=" + securityDataSheetId + ", productSku=" + productSku
				+ ", productName=" + productName + ", tradeName=" + tradeName + "]";
	}

}
