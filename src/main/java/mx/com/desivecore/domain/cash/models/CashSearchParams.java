package mx.com.desivecore.domain.cash.models;

import java.util.Date;

import mx.com.desivecore.domain.branches.models.BranchSummary;

public class CashSearchParams {

	private Date creationDate;

	private BranchSummary branch;

	private String userEmail;

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

	@Override
	public String toString() {
		return "CashSearchParams [creationDate=" + creationDate + ", branch=" + branch + ", userEmail=" + userEmail
				+ "]";
	}

}
