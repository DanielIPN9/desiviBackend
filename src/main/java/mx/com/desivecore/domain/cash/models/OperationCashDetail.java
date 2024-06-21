package mx.com.desivecore.domain.cash.models;

import java.util.Date;
import java.util.List;

import mx.com.desivecore.domain.branches.models.BranchSummary;

public class OperationCashDetail {

	private Long openingCashId;

	private Date creationDate;

	private BranchSummary branch;

	private String userEmail;

	private List<EntryMovementRecord> entryMovementList;

	private List<ExitMovementRecord> exitMovementList;

	private List<PaymentMovementRecord> paymentMovementList;

	private ClosingCash closingCash;

	private String currency;

	private Double amountEntryTotal;

	private Double amountExitTotal;

	private Double amountPaymentTotal;

	private Double amountTotal;

	public OperationCashDetail(OpeningCash openingCash) {

		this.openingCashId = openingCash.getOpeningCashId();
		this.creationDate = openingCash.getCreationDate();
		this.branch = openingCash.getBranch();
		this.userEmail = openingCash.getUserEmail();
		this.currency = openingCash.getCurrency();

	}

	public Long getOpeningCashId() {
		return openingCashId;
	}

	public void setOpeningCashId(Long openingCashId) {
		this.openingCashId = openingCashId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public BranchSummary getBranch() {
		return branch;
	}

	public void setBranch(BranchSummary branch) {
		this.branch = branch;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public List<EntryMovementRecord> getEntryMovementList() {
		return entryMovementList;
	}

	public void setEntryMovementList(List<EntryMovementRecord> entryMovementList) {
		this.entryMovementList = entryMovementList;
	}

	public List<ExitMovementRecord> getExitMovementList() {
		return exitMovementList;
	}

	public void setExitMovementList(List<ExitMovementRecord> exitMovementList) {
		this.exitMovementList = exitMovementList;
	}

	public List<PaymentMovementRecord> getPaymentMovementList() {
		return paymentMovementList;
	}

	public void setPaymentMovementList(List<PaymentMovementRecord> paymentMovementList) {
		this.paymentMovementList = paymentMovementList;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getAmountEntryTotal() {
		return amountEntryTotal;
	}

	public void setAmountEntryTotal(Double amountEntryTotal) {
		this.amountEntryTotal = amountEntryTotal;
	}

	public Double getAmountExitTotal() {
		return amountExitTotal;
	}

	public void setAmountExitTotal(Double amountExitTotal) {
		this.amountExitTotal = amountExitTotal;
	}

	public Double getAmountPaymentTotal() {
		return amountPaymentTotal;
	}

	public void setAmountPaymentTotal(Double amountPaymentTotal) {
		this.amountPaymentTotal = amountPaymentTotal;
	}

	public Double getAmountTotal() {
		return amountTotal;
	}

	public void setAmountTotal(Double amountTotal) {
		this.amountTotal = amountTotal;
	}

	public ClosingCash getClosingCash() {
		return closingCash;
	}

	public void setClosingCash(ClosingCash closingCash) {
		this.closingCash = closingCash;
	}

	@Override
	public String toString() {
		return "OperationCashDetail [openingCashId=" + openingCashId + ", creationDate=" + creationDate + ", branch="
				+ branch + ", userEmail=" + userEmail + ", entryMovementList=" + entryMovementList
				+ ", exitMovementList=" + exitMovementList + ", paymentMovementList=" + paymentMovementList
				+ ", closingCash=" + closingCash + ", currency=" + currency + ", amountEntryTotal=" + amountEntryTotal
				+ ", amountExitTotal=" + amountExitTotal + ", amountPaymentTotal=" + amountPaymentTotal
				+ ", amountTotal=" + amountTotal + "]";
	}

}
