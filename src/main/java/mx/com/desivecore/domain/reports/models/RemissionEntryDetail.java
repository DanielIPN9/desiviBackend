package mx.com.desivecore.domain.reports.models;

import java.util.Date;

public class RemissionEntryDetail {

	private String branchName;

	private String supplierName;

	private String folio;

	private String productName;

	private Date inputDate;

	private Double amount;

	private Double unitPrice;

	private Double iva;

	private Double subTotal;

	private Double total;

	public RemissionEntryDetail(String branchName, String supplierName, String folio, String productName,
			Date inputDate, Double amount, Double unitPrice, Double iva, Double subTotal, Double total) {
		super();
		this.branchName = branchName;
		this.supplierName = supplierName;
		this.folio = folio;
		this.productName = productName;
		this.inputDate = inputDate;
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

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
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
		return "RemissionEntryDetail [branchName=" + branchName + ", supplierName=" + supplierName + ", folio=" + folio
				+ ", productName=" + productName + ", inputDate=" + inputDate + ", amount=" + amount + ", unitPrice="
				+ unitPrice + ", iva=" + iva + ", subTotal=" + subTotal + ", total=" + total + "]";
	}

}