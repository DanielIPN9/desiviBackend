package mx.com.desivecore.domain.reports.models.document;

import java.io.InputStream;
import java.util.List;

import mx.com.desivecore.domain.reports.models.RemissionOutputDetail;
import mx.com.desivecore.domain.reports.models.RemissionOutputSearch;

public class RemissionOutputReportDocument {

	private InputStream logo;

	private String reportDate;

	private List<RemissionOutputDetail> remissionDetail;

	private Double remissionSubTotal;

	private Double ivaTotal;

	private Double remissionTotal;

	public RemissionOutputReportDocument(RemissionOutputSearch remissionOutputSearch) {
		reportDate = remissionOutputSearch.getReportDate();
		remissionDetail = remissionOutputSearch.getRemissionDetail();
		remissionSubTotal = remissionOutputSearch.getRemissionSubTotal();
		ivaTotal = remissionOutputSearch.getIvaTotal();
		remissionTotal = remissionOutputSearch.getRemissionTotal();
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

	public List<RemissionOutputDetail> getRemissionDetail() {
		return remissionDetail;
	}

	public void setRemissionDetail(List<RemissionOutputDetail> remissionDetail) {
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
		return "RemissionOutputReportDocument [logo=" + logo + ", reportDate=" + reportDate + ", remissionDetail="
				+ remissionDetail + ", remissionSubTotal=" + remissionSubTotal + ", ivaTotal=" + ivaTotal
				+ ", remissionTotal=" + remissionTotal + "]";
	}

}
