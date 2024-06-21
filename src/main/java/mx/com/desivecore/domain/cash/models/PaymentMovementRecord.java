package mx.com.desivecore.domain.cash.models;

import mx.com.desivecore.domain.remissionOutput.models.RemissionOutputSummary;

public class PaymentMovementRecord {

	private Long openingCashId;

	private RemissionOutputSummary remissionOutput;

	private String accountingType;

	private String currency;

	private Double amount;

	public Long getOpeningCashId() {
		return openingCashId;
	}

	public void setOpeningCashId(Long openingCashId) {
		this.openingCashId = openingCashId;
	}

	public RemissionOutputSummary getRemissionOutput() {
		return remissionOutput;
	}

	public void setRemissionOutput(RemissionOutputSummary remissionOutput) {
		this.remissionOutput = remissionOutput;
	}

	public String getAccountingType() {
		return accountingType;
	}

	public void setAccountingType(String accountingType) {
		this.accountingType = accountingType;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "PaymentMovementRecord [openingCashId=" + openingCashId + ", remissionOutput=" + remissionOutput
				+ ", accountingType=" + accountingType + ", currency=" + currency + ", amount=" + amount + "]";
	}

}
