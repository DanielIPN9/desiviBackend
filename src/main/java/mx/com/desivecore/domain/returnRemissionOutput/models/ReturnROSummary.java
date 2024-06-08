package mx.com.desivecore.domain.returnRemissionOutput.models;

import java.util.Date;

public class ReturnROSummary {

	private Long returnROId;

	private String folio;

	private Date creationDate;

	private Double total;

	public ReturnROSummary(Long returnROId, String folio, Date creationDate, Double total) {
		super();
		this.returnROId = returnROId;
		this.folio = folio;
		this.creationDate = creationDate;
		this.total = total;
	}

	public Long getReturnROId() {
		return returnROId;
	}

	public void setReturnROId(Long returnROId) {
		this.returnROId = returnROId;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "ReturnROSummary [returnROId=" + returnROId + ", folio=" + folio + ", creationDate=" + creationDate
				+ ", total=" + total + "]";
	}

}
