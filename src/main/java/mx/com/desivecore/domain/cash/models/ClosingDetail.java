package mx.com.desivecore.domain.cash.models;

public class ClosingDetail {

	private Long closingCashId;

	private AccountingType accountingType;

	private String currency;

	private Double amount;

	public static ClosingDetail generateNewSummary(AccountingType accountingType) {
		ClosingDetail closingDetail = new ClosingDetail();
		closingDetail.setAccountingType(accountingType);
		closingDetail.setCurrency("MXN");
		closingDetail.setAmount(0.0);
		return closingDetail;
	}

	public Long getClosingCashId() {
		return closingCashId;
	}

	public void setClosingCashId(Long closingCashId) {
		this.closingCashId = closingCashId;
	}

	public AccountingType getAccountingType() {
		return accountingType;
	}

	public void setAccountingType(AccountingType accountingType) {
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
		return "ClosingDetail [closingCashId=" + closingCashId + ", accountingType=" + accountingType + ", currency="
				+ currency + ", amount=" + amount + "]";
	}

}
