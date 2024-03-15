package mx.com.desivecore.domain.reports.models.search;

import mx.com.desivecore.domain.branches.models.Branch;

public class InventoryParamsReport {

	private Branch barnch;

	public Branch getBarnch() {
		return barnch;
	}

	public void setBarnch(Branch barnch) {
		this.barnch = barnch;
	}

	@Override
	public String toString() {
		return "InventoryParamsReport [barnch=" + barnch + "]";
	}

}
