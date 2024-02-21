package mx.com.desivecore.domain.remissionEntry.models;

import java.util.Date;

import mx.com.desivecore.domain.users.models.UserModel;

public class RemissionEntryHistory {

	private Long id;

	private Long remissionEntryId;

	private String folio;

	private Date acctionDate;

	private String action;

	private String modifiedData;

	public void generateSummary(RemissionEntry remissionEntry, String action) {
		remissionEntryId = remissionEntry.getRemissionEntryId();
		folio = remissionEntry.getFolio();
		acctionDate = new Date();
		this.action = action;
		modifiedData = "";
	}
	
	public void generateSummaryByUpdate(RemissionEntry remissionEntry, String action, UserModel user) {
		remissionEntryId = remissionEntry.getRemissionEntryId();
		folio = remissionEntry.getFolio();
		acctionDate = new Date();
		this.action = action;
		modifiedData = "";
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getAcctionDate() {
		return acctionDate;
	}

	public void setAcctionDate(Date acctionDate) {
		this.acctionDate = acctionDate;
	}

	public String getModifiedData() {
		return modifiedData;
	}

	public void setModifiedData(String modifiedData) {
		this.modifiedData = modifiedData;
	}

	@Override
	public String toString() {
		return "RemissionEntryHistory [id=" + id + ", remissionEntryId=" + remissionEntryId + ", folio=" + folio
				+ ", acctionDate=" + acctionDate + ", action=" + action + ", modifiedData=" + modifiedData + "]";
	}

}
