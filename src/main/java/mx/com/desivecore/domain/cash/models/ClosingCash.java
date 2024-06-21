package mx.com.desivecore.domain.cash.models;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import mx.com.desivecore.domain.branches.models.BranchSummary;
import mx.com.desivecore.domain.users.models.UserModel;

public class ClosingCash {

	private Long closingCashId;

	private Date creationDate;

	private BranchSummary branch;

	private String userEmail;

	private String currency;

	private Double amountTotal;

	private List<ClosingDetail> closingDetail;

	public ClosingCash() {
		super();
	}

	public static ClosingCash generateNewSummary(UserModel user, List<AccountingType> accountingTypeList) {
		ClosingCash closingCash = new ClosingCash();
		closingCash.setClosingCashId(null);
		closingCash.setCreationDate(new Date());
		closingCash.setBranch(new BranchSummary(user.getBranch().getBranchId(), user.getBranch().getName()));
		closingCash.setUserEmail(user.getEmail());
		closingCash.setCurrency("MXN");
		closingCash.setAmountTotal(0.0);
		List<ClosingDetail> closingDetail = accountingTypeList.stream()
				.map(accointingType -> ClosingDetail.generateNewSummary(accointingType)).collect(Collectors.toList());
		closingCash.setClosingDetail(closingDetail);
		return closingCash;
	}

	public Long getClosingCashId() {
		return closingCashId;
	}

	public void setClosingCashId(Long closingCashId) {
		this.closingCashId = closingCashId;
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getAmountTotal() {
		return amountTotal;
	}

	public void setAmountTotal(Double amountTotal) {
		this.amountTotal = amountTotal;
	}

	public List<ClosingDetail> getClosingDetail() {
		return closingDetail;
	}

	public void setClosingDetail(List<ClosingDetail> closingDetail) {
		this.closingDetail = closingDetail;
	}

	@Override
	public String toString() {
		return "ClosingCash [closingCashId=" + closingCashId + ", creationDate=" + creationDate + ", branch=" + branch
				+ ", userEmail=" + userEmail + ", currency=" + currency + ", amountTotal=" + amountTotal
				+ ", closingDetail=" + closingDetail + "]";
	}

}
