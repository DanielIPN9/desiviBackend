package mx.com.desivecore.domain.quote.models;

import java.util.Date;

public class QuoteOrderSummary {

	private Long quoteOrderId;

	private String folio;

	private Date effectiveDate;

	private String branch;

	private Double total;

	private Boolean isConverter;

	private Boolean isEffective;

	public QuoteOrderSummary(Long quoteOrderId, String folio, Date effectiveDate, Double total, Boolean isConverter,
			Boolean isEffective, String branch) {
		super();
		this.quoteOrderId = quoteOrderId;
		this.folio = folio;
		this.effectiveDate = effectiveDate;
		this.total = total;
		this.isConverter = isConverter;
		this.isEffective = isEffective;
		this.branch = branch;
	}

	public Long getQuoteOrderId() {
		return quoteOrderId;
	}

	public void setQuoteOrderId(Long quoteOrderId) {
		this.quoteOrderId = quoteOrderId;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Boolean getIsConverter() {
		return isConverter;
	}

	public void setIsConverter(Boolean isConverter) {
		this.isConverter = isConverter;
	}

	public Boolean getIsEffective() {
		return isEffective;
	}

	public void setIsEffective(Boolean isEffective) {
		this.isEffective = isEffective;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	@Override
	public String toString() {
		return "QuoteOrderSummary [quoteOrderId=" + quoteOrderId + ", folio=" + folio + ", effectiveDate="
				+ effectiveDate + ", branch=" + branch + ", total=" + total + ", isConverter=" + isConverter
				+ ", isEffective=" + isEffective + "]";
	}

}
