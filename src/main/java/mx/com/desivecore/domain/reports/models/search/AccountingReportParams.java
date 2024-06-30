package mx.com.desivecore.domain.reports.models.search;

import java.util.Date;

import mx.com.desivecore.domain.branches.models.Branch;

public class AccountingReportParams {

	private Date dateFrom;

	private Date dateTo;

	private String format;

	private Branch branch;

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	@Override
	public String toString() {
		return "AccountingReportSearchParams [dateFrom=" + dateFrom + ", dateTo=" + dateTo + ", format=" + format
				+ ", branch=" + branch + "]";
	}

}
