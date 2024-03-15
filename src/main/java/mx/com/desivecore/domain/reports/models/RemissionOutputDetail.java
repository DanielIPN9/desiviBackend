package mx.com.desivecore.domain.reports.models;

import java.util.Date;

public class RemissionOutputDetail {

	private String branchName;

	private String clientName;

	private String folio;

	private String productName;

	private Date outputDate;

	private Double amount;

	private Double unitPrice;

	private Double iva;

	private Double subTotal;

	private Double total;

	public RemissionOutputDetail(String branchName, String clientName, String folio, String productName,
			Date outputDate, Double amount, Double unitPrice, Double iva, Double subTotal, Double total) {
		super();
		this.branchName = branchName;
		this.clientName = clientName;
		this.folio = folio;
		this.productName = productName;
		this.outputDate = outputDate;
		this.amount = amount;
		this.unitPrice = unitPrice;
		this.iva = iva;
		this.subTotal = subTotal;
		this.total = total;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getOutputDate() {
		return outputDate;
	}

	public void setOutputDate(Date outputDate) {
		this.outputDate = outputDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getIva() {
		return iva;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "RemissionOutputDetail [branchName=" + branchName + ", clientName=" + clientName + ", folio=" + folio
				+ ", productName=" + productName + ", outputDate=" + outputDate + ", amount=" + amount + ", unitPrice="
				+ unitPrice + ", iva=" + iva + ", subTotal=" + subTotal + ", total=" + total + "]";
	}

}
