package mx.com.desivecore.domain.returnRemissionEntry.models;

import java.util.Date;

import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.domain.suppliers.models.Supplier;

public class ReturnRESearchParams {

	private String folio;

	private Supplier supplier;

	private Branch branch;

	private Date creationDate;

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
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
		return "ReturnRESearchParams [folio=" + folio + ", supplier=" + supplier + ", branch=" + branch
				+ ", creationDate=" + creationDate + "]";
	}

}
