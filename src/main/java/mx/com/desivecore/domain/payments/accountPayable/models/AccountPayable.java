package mx.com.desivecore.domain.payments.accountPayable.models;

import java.util.Date;

import mx.com.desivecore.domain.cash.models.AccountingType;

public class AccountPayable {

	private Long paymentId;

	private Long remissionEntryId;

	private AccountingType accountType;

	private Double paymentAmount;

	private Date creationDate;

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public Long getRemissionEntryId() {
		return remissionEntryId;
	}

	public void setRemissionEntryId(Long remissionEntryId) {
		this.remissionEntryId = remissionEntryId;
	}

	public AccountingType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountingType accountType) {
		this.accountType = accountType;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "AccountPayable [paymentId=" + paymentId + ", remissionEntryId=" + remissionEntryId + ", accountType="
				+ accountType + ", paymentAmount=" + paymentAmount + ", creationDate=" + creationDate + "]";
	}

}
