package mx.com.desivecore.domain.remissionEntry.models;

import java.util.Date;

public class RemissionEntrySummary {

	private Long remissionEntryId;

	private String folio;

	private Date creationDate;

	private Date requestDate;

	private String supplierName;

	private String branchName;

	private Double remissionTotal;

	public RemissionEntrySummary(Long remissionEntryId, String folio, Date creationDate, Date requestDate,
			String supplierName, String branchName, Double remissionTotal) {
		super();
		this.remissionEntryId = remissionEntryId;
		this.folio = folio;
		this.creationDate = creationDate;
		this.requestDate = requestDate;
		this.supplierName = supplierName;
		this.branchName = branchName;
		this.remissionTotal = remissionTotal;
	}

	public Long getRemissionEntryId() {
		return remissionEntryId;
	}

	public void setRemissionEntryId(Long remissionEntryId) {
		this.remissionEntryId = remissionEntryId;
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

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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
		return "RemissionEntrySummary [remissionEntryId=" + remissionEntryId + ", folio=" + folio + ", creationDate="
				+ creationDate + ", requestDate=" + requestDate + ", supplierName=" + supplierName + ", branchName="
				+ branchName + ", remissionTotal=" + remissionTotal + "]";
	}

}
