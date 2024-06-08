package mx.com.desivecore.domain.returnRemissionOutput.models;

import java.util.Date;

import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.domain.clients.models.ClientSummary;

public class ReturnROSearchParams {

	private String folio;

	private ClientSummary client;

	private Branch branch;

	private Date creationDate;

	public ReturnROSearchParams() {
		super();
	}

	public ReturnROSearchParams(String folio, ClientSummary client, Branch branch, Date creationDate) {
		super();
		this.folio = folio;
		this.client = client;
		this.branch = branch;
		this.creationDate = creationDate;
	}

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
		return "ReturnROSearchParams [folio=" + folio + ", client=" + client + ", branch=" + branch + ", creationDate="
				+ creationDate + "]";
	}

}
