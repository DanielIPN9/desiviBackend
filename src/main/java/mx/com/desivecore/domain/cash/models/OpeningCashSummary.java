package mx.com.desivecore.domain.cash.models;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import mx.com.desivecore.commons.constants.OpeningCashStatusEnum;

public class OpeningCashSummary {

	private Long openingCashId;

	private Date creationDate;

	private String branchName;

	private String userEmail;

	private Boolean isActive;

	private String status;

	public OpeningCashSummary() {
		super();
	}

	public OpeningCashSummary(Long openingCashId, Date creationDate, String branchName, String userEmail,
			Boolean isActive) {
		super();
		this.openingCashId = openingCashId;
		this.creationDate = creationDate;
		this.branchName = branchName;
		this.userEmail = userEmail;
		this.isActive = isActive;
		this.status = evaluateStatusByCreationDate(creationDate);
	}

	public OpeningCashSummary(OpeningCash openingCash) {
		this.openingCashId = openingCash.getOpeningCashId();
		this.creationDate = openingCash.getCreationDate();
		this.branchName = openingCash.getBranch().getName();
		this.userEmail = openingCash.getUserEmail();
		this.isActive = openingCash.getIsActive();
		this.status = evaluateStatusByCreationDate(openingCash.getCreationDate());
	}

	public OpeningCashSummary(String branchName, String userEmail, Boolean isActive, String status) {
		this.openingCashId = null;
		this.creationDate = null;
		this.branchName = branchName;
		this.userEmail = userEmail;
		this.isActive = isActive;
		this.status = status;
	}

	public static String evaluateStatusByCreationDate(Date creationDate) {
		LocalDate currentDay = LocalDate.now();
		LocalDate ldOpeningCash = creationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		if (ldOpeningCash.equals(currentDay))
			return OpeningCashStatusEnum.ACTIVE.toString();
		if (ldOpeningCash.isBefore(currentDay))
			return OpeningCashStatusEnum.EXPIRED.toString();
		return OpeningCashStatusEnum.EMPTY.toString();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "OpeningCashSummary [openingCashId=" + openingCashId + ", creationDate=" + creationDate + ", branchName="
				+ branchName + ", userEmail=" + userEmail + ", isActive=" + isActive + ", status=" + status + "]";
	}
}