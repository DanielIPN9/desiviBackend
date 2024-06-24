package mx.com.desivecore.domain.payments.accountPayable.models;

import java.util.List;

import mx.com.desivecore.domain.remissionEntry.models.RemissionEntry;
import mx.com.desivecore.domain.suppliers.models.Supplier;

public class RemissionEntryGlobalBalance {

	private Long remissionEntryId;

	private String folio;

	private String supplierName;

	private Double amountTotal;

	private Double balanceDue;

	private String paymentState;

	private List<AccountPayable> paymentDetail;

	public RemissionEntryGlobalBalance() {
		super();
	}

	public RemissionEntryGlobalBalance(RemissionEntry remissionEntry, Supplier supplier,
			List<AccountPayable> paymentDetail) {
		super();
		this.remissionEntryId = remissionEntry.getRemissionEntryId();
		this.folio = remissionEntry.getFolio();
		this.supplierName = supplier.getBusinessName();
		this.amountTotal = remissionEntry.getRemissionTotal();
		this.balanceDue = remissionEntry.getBalanceDue();
		this.paymentState = remissionEntry.getPaymentStatus();
		this.paymentDetail = paymentDetail;
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

	public String getPaymentState() {
		return paymentState;
	}

	public void setPaymentState(String paymentState) {
		this.paymentState = paymentState;
	}

	public List<AccountPayable> getPaymentDetail() {
		return paymentDetail;
	}

	public void setPaymentDetail(List<AccountPayable> paymentDetail) {
		this.paymentDetail = paymentDetail;
	}

	@Override
	public String toString() {
		return "RemissionEntryGlobalBalance [remissionEntryId=" + remissionEntryId + ", folio=" + folio
				+ ", supplierName=" + supplierName + ", amountTotal=" + amountTotal + ", balanceDue=" + balanceDue
				+ ", paymentState=" + paymentState + ", paymentDetail=" + paymentDetail + "]";
	}

}
