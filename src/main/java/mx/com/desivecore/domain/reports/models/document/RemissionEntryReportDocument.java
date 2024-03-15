package mx.com.desivecore.domain.reports.models.document;

import java.io.InputStream;
import java.util.List;

import mx.com.desivecore.domain.reports.models.RemissionEntryDetail;
import mx.com.desivecore.domain.reports.models.RemissionEntrySearch;

public class RemissionEntryReportDocument {

	private InputStream logo;

	private String reportDate;

	List<RemissionEntryDetail> remissionDetail;

	private Double remissionSubTotal;

	private Double ivaTotal;

	private Double remissionTotal;

	public RemissionEntryReportDocument(RemissionEntrySearch remissionEntrySearch) {
		reportDate = remissionEntrySearch.getReportDate();
		remissionDetail = remissionEntrySearch.getRemissionDetail();
		remissionSubTotal = remissionEntrySearch.getRemissionSubTotal();
		ivaTotal = remissionEntrySearch.getIvaTotal();
		remissionTotal = remissionEntrySearch.getRemissionTotal();
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

	public List<RemissionEntryDetail> getRemissionDetail() {
		return remissionDetail;
	}

	public void setRemissionDetail(List<RemissionEntryDetail> remissionDetail) {
		this.remissionDetail = remissionDetail;
	}

	public Double getRemissionSubTotal() {
		return remissionSubTotal;
	}

	public void setRemissionSubTotal(Double remissionSubTotal) {
		this.remissionSubTotal = remissionSubTotal;
	}

	public Double getIvaTotal() {
		return ivaTotal;
	}

	public void setIvaTotal(Double ivaTotal) {
		this.ivaTotal = ivaTotal;
	}

	public Double getRemissionTotal() {
		return remissionTotal;
	}

	public void setRemissionTotal(Double remissionTotal) {
		this.remissionTotal = remissionTotal;
	}

	@Override
	public String toString() {
		return "RemissionEntryReportDocument [logo=" + logo + ", reportDate=" + reportDate + ", remissionDetail="
				+ remissionDetail + ", remissionSubTotal=" + remissionSubTotal + ", ivaTotal=" + ivaTotal
				+ ", remissionTotal=" + remissionTotal + "]";
	}

}
