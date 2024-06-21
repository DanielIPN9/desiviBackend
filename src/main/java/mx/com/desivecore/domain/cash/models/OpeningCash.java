package mx.com.desivecore.domain.cash.models;

import java.util.Date;

import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.domain.branches.models.BranchSummary;
import mx.com.desivecore.domain.users.models.UserModel;

public class OpeningCash {

	private Long openingCashId;

	private Date creationDate;

	private BranchSummary branch;

	private String userEmail;

	private Long userId;

	private String currency;

	private Double amount;

	private Boolean isActive;

	private Long closingCashId;

	public OpeningCash() {
		super();
	}

	public OpeningCash(UserModel user, Branch branch, OpeningCash openingCash) {
		this.openingCashId = null;
		this.creationDate = new Date();
		this.branch = new BranchSummary(branch.getBranchId(), branch.getName());
		this.userEmail = user.getEmail();
		this.userId = user.getUserId();
		this.currency = openingCash.getCurrency();
		this.amount = openingCash.getAmount();
		this.isActive = true;
		this.closingCashId = null;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Long getClosingCashId() {
		return closingCashId;
	}

	public void setClosingCashId(Long closingCashId) {
		this.closingCashId = closingCashId;
	}

	@Override
	public String toString() {
		return "OpeningCash [openingCashId=" + openingCashId + ", creationDate=" + creationDate + ", branch=" + branch
				+ ", userEmail=" + userEmail + ", userId=" + userId + ", currency=" + currency + ", amount=" + amount
				+ ", isActive=" + isActive + ", closingCashId=" + closingCashId + "]";
	}

}