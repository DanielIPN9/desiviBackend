package mx.com.desivecore.domain.remissionOutput.models;

import java.util.Date;

import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.domain.clients.models.ClientSummary;

public class RemissionOutputSearchParams {

	private String folio;

	private ClientSummary client;

	private Branch branch;

	private Date creationDate;

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public ClientSummary getClient() {
		return client;
	}

	public void setClient(ClientSummary client) {
		this.client = client;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "RemissionOutputSearchParams [folio=" + folio + ", client=" + client + ", branch=" + branch
				+ ", creationDate=" + creationDate + "]";
	}

}
