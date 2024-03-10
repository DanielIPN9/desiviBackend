package mx.com.desivecore.domain.remissionOutput.models;

import java.util.Date;

public class RemissionOutputSummary {

	private Long remissionOutputId;

	private String folio;

	private Date creationDate;

	private Date requestDate;

	private String clientBusinessName;

	private String branchName;

	private Double remissionTotal;

	public RemissionOutputSummary(Long remissionOutputId, String folio, Date creationDate, Date requestDate,
			String clientBusinessName, String branchName, Double remissionTotal) {
		super();
		this.remissionOutputId = remissionOutputId;
		this.folio = folio;
		this.creationDate = creationDate;
		this.requestDate = requestDate;
		this.clientBusinessName = clientBusinessName;
		this.branchName = branchName;
		this.remissionTotal = remissionTotal;
	}

	public Long getRemissionOutputId() {
		return remissionOutputId;
	}

	public void setRemissionOutputId(Long remissionOutputId) {
		this.remissionOutputId = remissionOutputId;
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

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getClientBusinessName() {
		return clientBusinessName;
	}

	public void setClientBusinessName(String clientBusinessName) {
		this.clientBusinessName = clientBusinessName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Double getRemissionTotal() {
		return remissionTotal;
	}

	public void setRemissionTotal(Double remissionTotal) {
		this.remissionTotal = remissionTotal;
	}

	@Override
	public String toString() {
		return "RemissionOutputSummary [remissionOutputId=" + remissionOutputId + ", folio=" + folio + ", creationDate="
				+ creationDate + ", requestDate=" + requestDate + ", clientBusinessName=" + clientBusinessName
				+ ", branchName=" + branchName + ", remissionTotal=" + remissionTotal + "]";
	}

}
