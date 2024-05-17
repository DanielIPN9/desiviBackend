package mx.com.desivecore.domain.reports.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RemissionEntrySearch {

	private String reportDate;

	private List<RemissionEntryDetail> remissionDetail;

	private Double remissionSubTotal;

	private Double ivaTotal;

	private Double remissionTotal;

	public void generateSummary(List<RemissionEntryDetail> remissionDetailSearch) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		reportDate = simpleDateFormat.format(new Date());

		remissionSubTotal = 0.0;
		ivaTotal = 0.0;
		remissionTotal = 0.0;

		for (RemissionEntryDetail remissionEntryDetail : remissionDetailSearch) {
			remissionSubTotal += remissionEntryDetail.getSubTotal();
			ivaTotal += remissionEntryDetail.getIva() * remissionEntryDetail.getSubTotal();
			remissionTotal += remissionEntryDetail.getTotal();
		}

		remissionSubTotal = (double) Math.round(remissionSubTotal * 100) / 100;
		ivaTotal = (double) Math.round(ivaTotal * 100) / 100;
		remissionTotal = (double) Math.round(remissionTotal * 100) / 100;

		remissionDetail = remissionDetailSearch;
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
		return "RemissionEntrySearch [reportDate=" + reportDate + ", remissionDetail=" + remissionDetail
				+ ", remissionSubTotal=" + remissionSubTotal + ", ivaTotal=" + ivaTotal + ", remissionTotal="
				+ remissionTotal + "]";
	}

}
