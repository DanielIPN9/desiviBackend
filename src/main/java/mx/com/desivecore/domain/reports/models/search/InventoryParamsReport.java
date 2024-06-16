package mx.com.desivecore.domain.reports.models.search;

import mx.com.desivecore.domain.branches.models.Branch;

public class InventoryParamsReport {

	private Branch barnch;

	private String format;

	public Branch getBarnch() {
		return barnch;
	}

	public void setBarnch(Branch barnch) {
		this.barnch = barnch;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@Override
	public String toString() {
		return "InventoryParamsReport [barnch=" + barnch + ", format=" + format + "]";
	}

}
