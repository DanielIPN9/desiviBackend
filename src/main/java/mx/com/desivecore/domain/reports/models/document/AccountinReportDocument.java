package mx.com.desivecore.domain.reports.models.document;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mx.com.desivecore.domain.reports.models.AccountPayableSummary;
import mx.com.desivecore.domain.reports.models.AccountReceivableSummary;
import mx.com.desivecore.domain.reports.models.EntryMovementRecordSummary;
import mx.com.desivecore.domain.reports.models.ExitMovementRecordSummary;
import mx.com.desivecore.domain.reports.models.search.AccountingReportParams;

public class AccountinReportDocument {

	private InputStream logo;

	private String reportDate;

	private String dateFrom;

	private String dateTo;

	private Double incomeTotal;

	private Double expenseTotal;

	private Double profitTotal;

	private List<EntryMovementRecordSummary> entryCashMovementList;

	private List<ExitMovementRecordSummary> exitCashMovementRecordList;

	private List<AccountPayableSummary> accountPayableList;

	private List<AccountReceivableSummary> accountReceivableList;

	public AccountinReportDocument(AccountingReportParams accountingReportSearchParams, Double incomeTotal,
			Double expenseTotal, Double profitTotal, List<EntryMovementRecordSummary> entryCashMovementList,
			List<ExitMovementRecordSummary> exitCashMovementRecordList, List<AccountPayableSummary> accountPayableList,
			List<AccountReceivableSummary> accountReceivableList) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		this.reportDate = simpleDateFormat.format(new Date());
		this.dateFrom = simpleDateFormat.format(accountingReportSearchParams.getDateFrom());
		this.dateTo = simpleDateFormat.format(accountingReportSearchParams.getDateTo());
		this.incomeTotal = incomeTotal;
		this.expenseTotal = expenseTotal;
		this.profitTotal = profitTotal;
		this.entryCashMovementList = entryCashMovementList;
		this.exitCashMovementRecordList = exitCashMovementRecordList;
		this.accountPayableList = accountPayableList;
		this.accountReceivableList = accountReceivableList;
	}

	public InputStream getLogo() {
		return logo;
	}

	public void setLogo(InputStream logo) {
		this.logo = logo;
	}

	public String getReportDate() {
		return reportDate;
	}

	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public Double getIncomeTotal() {
		return incomeTotal;
	}

	public void setIncomeTotal(Double incomeTotal) {
		this.incomeTotal = incomeTotal;
	}

	public Double getExpenseTotal() {
		return expenseTotal;
	}

	public void setExpenseTotal(Double expenseTotal) {
		this.expenseTotal = expenseTotal;
	}

	public Double getProfitTotal() {
		return profitTotal;
	}

	public void setProfitTotal(Double profitTotal) {
		this.profitTotal = profitTotal;
	}

	public List<EntryMovementRecordSummary> getEntryCashMovementList() {
		return entryCashMovementList;
	}

	public void setEntryCashMovementList(List<EntryMovementRecordSummary> entryCashMovementList) {
		this.entryCashMovementList = entryCashMovementList;
	}

	public List<ExitMovementRecordSummary> getExitCashMovementRecordList() {
		return exitCashMovementRecordList;
	}

	public void setExitCashMovementRecordList(List<ExitMovementRecordSummary> exitCashMovementRecordList) {
		this.exitCashMovementRecordList = exitCashMovementRecordList;
	}

	public List<AccountPayableSummary> getAccountPayableList() {
		return accountPayableList;
	}

	public void setAccountPayableList(List<AccountPayableSummary> accountPayableList) {
		this.accountPayableList = accountPayableList;
	}

	public List<AccountReceivableSummary> getAccountReceivableList() {
		return accountReceivableList;
	}

	public void setAccountReceivableList(List<AccountReceivableSummary> accountReceivableList) {
		this.accountReceivableList = accountReceivableList;
	}

	@Override
	public String toString() {
		return "AccountinReportDocument [logo=" + logo + ", reportDate=" + reportDate + ", dateFrom=" + dateFrom
				+ ", dateTo=" + dateTo + ", incomeTotal=" + incomeTotal + ", expenseTotal=" + expenseTotal
				+ ", profitTotal=" + profitTotal + ", entryCashMovementList=" + entryCashMovementList
				+ ", exitCashMovementRecordList=" + exitCashMovementRecordList + ", accountPayableList="
				+ accountPayableList + ", accountReceivableList=" + accountReceivableList + "]";
	}

}
