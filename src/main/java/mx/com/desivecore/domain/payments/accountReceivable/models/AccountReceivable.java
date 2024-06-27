package mx.com.desivecore.domain.payments.accountReceivable.models;

import java.util.Date;

import mx.com.desivecore.domain.cash.models.AccountingType;

public class AccountReceivable {

	private Long paymentId;

	private Long remissionOutputId;

	private AccountingType accountingType;

	private Double paymentAmount;

	private Date creationDate;

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public Long getRemissionOutputId() {
		return remissionOutputId;
	}

	public void setRemissionOutputId(Long remissionOutputId) {
		this.remissionOutputId = remissionOutputId;
	}

	public AccountingType getAccountingType() {
		return accountingType;
	}

	public void setAccountingType(AccountingType accountingType) {
		this.accountingType = accountingType;
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
		return "AccountReceivable [paymentId=" + paymentId + ", remissionOutputId=" + remissionOutputId
				+ ", accountingType=" + accountingType + ", paymentAmount=" + paymentAmount + ", creationDate="
				+ creationDate + "]";
	}

}
