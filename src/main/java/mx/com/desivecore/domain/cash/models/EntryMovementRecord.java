package mx.com.desivecore.domain.cash.models;

public class EntryMovementRecord {

	private Long openingCashId;

	private AccountingType accountingType;

	private String description;

	private String currency;

	private Double amount;

	public Long getOpeningCashId() {
		return openingCashId;
	}

	public void setOpeningCashId(Long openingCashId) {
		this.openingCashId = openingCashId;
	}

	public AccountingType getAccountingType() {
		return accountingType;
	}

	public void setAccountingType(AccountingType accountingType) {
		this.accountingType = accountingType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		return "EntryMovementRecord [openingCashId=" + openingCashId + ", accountingType=" + accountingType
				+ ", description=" + description + ", currency=" + currency + ", amount=" + amount + "]";
	}

}
