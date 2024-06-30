package mx.com.desivecore.domain.reports.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EntryMovementRecordSummary {

	private String branchName;

	private String creationDate;

	private String description;

	private String accountingType;

	private Double amount;

	public EntryMovementRecordSummary(String branchName, Date creationDate, String description, String accountingType,
			Double amount) {
		super();
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		this.branchName = branchName;
		this.creationDate = simpleDateFormat.format(creationDate);
		this.description = description;
		this.accountingType = accountingType;
		this.amount = amount;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAccountingType() {
		return accountingType;
	}

	public void setAccountingType(String accountingType) {
		this.accountingType = accountingType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "EntryMovementRecordSummary [branchName=" + branchName + ", creationDate=" + creationDate
				+ ", description=" + description + ", accountingType=" + accountingType + ", amount=" + amount + "]";
	}

}
