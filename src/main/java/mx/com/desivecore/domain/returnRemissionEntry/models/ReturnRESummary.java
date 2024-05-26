package mx.com.desivecore.domain.returnRemissionEntry.models;

import java.util.Date;

public class ReturnRESummary {

	private Long returnREId;

	private String folio;

	private Date creationDate;

	private Double total;

	public ReturnRESummary() {
		super();
	}

	public ReturnRESummary(Long returnREId, String folio, Date creationDate, Double total) {
		super();
		this.returnREId = returnREId;
		this.folio = folio;
		this.creationDate = creationDate;
		this.total = total;
	}

	public Long getReturnREId() {
		return returnREId;
	}

	public void setReturnREId(Long returnREId) {
		this.returnREId = returnREId;
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
		return "ReturnRESummary [returnREId=" + returnREId + ", folio=" + folio + ", creationDate=" + creationDate
				+ ", total=" + total + "]";
	}

}
