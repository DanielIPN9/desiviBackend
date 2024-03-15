package mx.com.desivecore.domain.reports.models.document;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mx.com.desivecore.domain.reports.models.ProductDetail;

public class InventoryReportDocument {

	private InputStream logo;

	private String reportDate;

	private List<ProductDetail> productDetail;

	public InventoryReportDocument(List<ProductDetail> productDetailSummary) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		reportDate = simpleDateFormat.format(new Date());
		this.productDetail = productDetailSummary;
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

	public List<ProductDetail> getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(List<ProductDetail> productDetail) {
		this.productDetail = productDetail;
	}

	@Override
	public String toString() {
		return "InventoryReportDocument [logo=" + logo + ", reportDate=" + reportDate + ", productDetail="
				+ productDetail + "]";
	}

}
