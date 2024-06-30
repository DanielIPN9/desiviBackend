package mx.com.desivecore.domain.reports.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountPayableSummary {

	private String branchName;

	private String remissionEntryFolio;

	private String creationDate;

	private String accountingType;

	private Double amount;

	public AccountPayableSummary(String branchName, String remissionEntryFolio, Date creationDate,
			String accountingType, Double amount) {
		super();
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		this.branchName = branchName;
		this.remissionEntryFolio = remissionEntryFolio;
		this.creationDate = simpleDateFormat.format(creationDate);
		this.accountingType = accountingType;
		this.amount = amount;
	}

	public String getRemissionEntryFolio() {
		return remissionEntryFolio;
	}

	public void setRemissionEntryFolio(String remissionEntryFolio) {
		this.remissionEntryFolio = remissionEntryFolio;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
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

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	@Override
	public String toString() {
		return "AccountPayableSummary [branchName=" + branchName + ", remissionEntryFolio=" + remissionEntryFolio
				+ ", creationDate=" + creationDate + ", accountingType=" + accountingType + ", amount=" + amount + "]";
	}

}