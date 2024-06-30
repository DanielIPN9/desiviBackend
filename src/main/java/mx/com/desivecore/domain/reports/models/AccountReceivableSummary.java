package mx.com.desivecore.domain.reports.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountReceivableSummary {

	private String branchName;

	private String remissionOutputFolio;

	private String creationDate;

	private String accountingType;

	private Double amount;

	public AccountReceivableSummary(String branchName, String remissionOutputFolio, Date creationDate,
			String accountingType, Double amount) {
		super();
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		this.branchName = branchName;
		this.remissionOutputFolio = remissionOutputFolio;
		this.creationDate = simpleDateFormat.format(creationDate);
		this.accountingType = accountingType;
		this.amount = amount;
	}

	public String getRemissionOutputFolio() {
		return remissionOutputFolio;
	}

	public void setRemissionOutputFolio(String remissionOutputFolio) {
		this.remissionOutputFolio = remissionOutputFolio;
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
		return "AccountReceivableSummary [branchName=" + branchName + ", remissionOutputFolio=" + remissionOutputFolio
				+ ", creationDate=" + creationDate + ", accountingType=" + accountingType + ", amount=" + amount + "]";
	}

}