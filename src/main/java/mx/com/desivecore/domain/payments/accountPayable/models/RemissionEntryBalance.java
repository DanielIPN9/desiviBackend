package mx.com.desivecore.domain.payments.accountPayable.models;

import java.util.Date;

import mx.com.desivecore.domain.remissionEntry.models.RemissionEntry;
import mx.com.desivecore.domain.suppliers.models.Supplier;

public class RemissionEntryBalance {

	private Long remissionEntryId;

	private String folio;

	private String supplierName;

	private Double amountTotal;

	private Double balanceDue;

	private String paymentSate;

	private Date creationDate;

	public RemissionEntryBalance(Long remissionEntryId, String folio, String supplierName, Double amountTotal,
			Double balanceDue, Date creationDate, String paymentSate) {
		super();
		this.remissionEntryId = remissionEntryId;
		this.folio = folio;
		this.supplierName = supplierName;
		this.amountTotal = amountTotal;
		this.balanceDue = balanceDue;
		this.creationDate = creationDate;
		this.paymentSate = paymentSate;
	}

	public RemissionEntryBalance() {
		super();
	}

	public RemissionEntryBalance(RemissionEntry remissionEntry, Supplier supplier) {
		this.remissionEntryId = remissionEntry.getRemissionEntryId();
		this.folio = remissionEntry.getFolio();
		this.supplierName = supplier.getBusinessName();
		this.amountTotal = remissionEntry.getRemissionTotal();
		this.balanceDue = remissionEntry.getBalanceDue();
		this.paymentSate = remissionEntry.getPaymentStatus();
		this.creationDate = remissionEntry.getCreationDate();
	}

	public Long getRemissionEntryId() {
		return remissionEntryId;
	}

	public void setRemissionEntryId(Long remissionEntryId) {
		this.remissionEntryId = remissionEntryId;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Double getAmountTotal() {
		return amountTotal;
	}

	public void setAmountTotal(Double amountTotal) {
		this.amountTotal = amountTotal;
	}

	public Double getBalanceDue() {
		return balanceDue;
	}

	public void setBalanceDue(Double balanceDue) {
		this.balanceDue = balanceDue;
	}

	public String getPaymentSate() {
		return paymentSate;
	}

	public void setPaymentSate(String paymentSate) {
		this.paymentSate = paymentSate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "RemissionEntryBalance [remissionEntryId=" + remissionEntryId + ", folio=" + folio + ", supplierName="
				+ supplierName + ", amountTotal=" + amountTotal + ", balanceDue=" + balanceDue + ", paymentSate="
				+ paymentSate + ", creationDate=" + creationDate + "]";
	}

}
