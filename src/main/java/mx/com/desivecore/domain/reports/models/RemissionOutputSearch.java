package mx.com.desivecore.domain.reports.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RemissionOutputSearch {

	private String reportDate;

	private List<RemissionOutputDetail> remissionDetail;

	private Double remissionSubTotal;

	private Double ivaTotal;

	private Double remissionTotal;
	
	public void generateSummary(List<RemissionOutputDetail> remissionDetailSearch) {
		
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		reportDate = simpleDateFormat.format(new Date());
		
		remissionSubTotal = 0.0;
		ivaTotal = 0.0;
		remissionTotal = 0.0;
		
		for (RemissionOutputDetail remissionOutputDetail : remissionDetailSearch) {
			remissionSubTotal += remissionOutputDetail.getSubTotal();
			ivaTotal += remissionOutputDetail.getIva();
			remissionTotal += remissionOutputDetail.getTotal();
		}
		
		remissionSubTotal = (double) Math.round(remissionSubTotal * 100) / 100;
		ivaTotal = (double) Math.round(ivaTotal * 100) / 100;
		ivaTotal = (double) Math.round(ivaTotal * 100) / 100;
		
		remissionDetail = remissionDetailSearch;
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
		return "RemissionOutputSearch [reportDate=" + reportDate + ", remissionDetail=" + remissionDetail
				+ ", remissionSubTotal=" + remissionSubTotal + ", ivaTotal=" + ivaTotal + ", remissionTotal="
				+ remissionTotal + "]";
	}

}
