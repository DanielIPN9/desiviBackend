package mx.com.desivecore.domain.reports.models;

public class ProductDetail {

	private String branchName;

	private String productName;

	private String unitMeasure;

	private Double amount;

	private Double unitPurchasePrice;

	private Double unitSellingPrice;

	private Double iva;

	private Double minAvailability;

	private String alertMessage;

	private Double subTotalPurchase;

	private Double totalPurchase;

	private Double subTotalSelling;

	private Double totalSelling;

	public void generateSummary() {
		
		alertMessage = amount <= minAvailability ? "ALERTADO" : " ";

		subTotalPurchase = amount * unitPurchasePrice;
		subTotalPurchase = (double) Math.round(subTotalPurchase * 100) / 100;

		totalPurchase = amount * (1 + iva);
		totalPurchase = (double) Math.round(totalPurchase * 100) / 100;

		subTotalSelling = amount * unitSellingPrice;
		subTotalSelling = (double) Math.round(subTotalSelling * 100) / 100;

		totalSelling = amount * (1 + iva);
		totalSelling = (double) Math.round(totalSelling * 100) / 100;
	}

	public ProductDetail(String branchName, String productName, String unitMeasure, Double amount,
			Double unitPurchasePrice, Double unitSellingPrice, Double iva, Double minAvailability) {
		super();
		this.branchName = branchName;
		this.productName = productName;
		this.unitMeasure = unitMeasure;
		this.amount = amount;
		this.unitPurchasePrice = unitPurchasePrice;
		this.unitSellingPrice = unitSellingPrice;
		this.iva = iva;
		this.minAvailability = minAvailability;
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

	public String getUnitMeasure() {
		return unitMeasure;
	}

	public void setUnitMeasure(String unitMeasure) {
		this.unitMeasure = unitMeasure;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getUnitPurchasePrice() {
		return unitPurchasePrice;
	}

	public void setUnitPurchasePrice(Double unitPurchasePrice) {
		this.unitPurchasePrice = unitPurchasePrice;
	}

	public Double getUnitSellingPrice() {
		return unitSellingPrice;
	}

	public void setUnitSellingPrice(Double unitSellingPrice) {
		this.unitSellingPrice = unitSellingPrice;
	}

	public Double getIva() {
		return iva;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}

	public Double getSubTotalPurchase() {
		return subTotalPurchase;
	}

	public void setSubTotalPurchase(Double subTotalPurchase) {
		this.subTotalPurchase = subTotalPurchase;
	}

	public Double getTotalPurchase() {
		return totalPurchase;
	}

	public void setTotalPurchase(Double totalPurchase) {
		this.totalPurchase = totalPurchase;
	}

	public Double getSubTotalSelling() {
		return subTotalSelling;
	}

	public void setSubTotalSelling(Double subTotalSelling) {
		this.subTotalSelling = subTotalSelling;
	}

	public Double getTotalSelling() {
		return totalSelling;
	}

	public void setTotalSelling(Double totalSelling) {
		this.totalSelling = totalSelling;
	}

	public Double getMinAvailability() {
		return minAvailability;
	}

	public void setMinAvailability(Double minAvailability) {
		this.minAvailability = minAvailability;
	}

	public String getAlertMessage() {
		return alertMessage;
	}

	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}

	@Override
	public String toString() {
		return "ProductDetail [branchName=" + branchName + ", productName=" + productName + ", unitMeasure="
				+ unitMeasure + ", amount=" + amount + ", unitPurchasePrice=" + unitPurchasePrice
				+ ", unitSellingPrice=" + unitSellingPrice + ", iva=" + iva + ", minAvailability=" + minAvailability
				+ ", subTotalPurchase=" + subTotalPurchase + ", totalPurchase=" + totalPurchase + ", subTotalSelling="
				+ subTotalSelling + ", totalSelling=" + totalSelling + "]";
	}

}
