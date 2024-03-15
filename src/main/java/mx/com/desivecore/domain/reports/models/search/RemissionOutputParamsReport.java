package mx.com.desivecore.domain.reports.models.search;

import java.util.Date;

import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.domain.clients.models.ClientSummary;
import mx.com.desivecore.domain.products.models.Product;

public class RemissionOutputParamsReport {

	private Date dateFrom;

	private Date dateTo;

	private ClientSummary client;

	private Product product;

	private Branch branch;

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public ClientSummary getClient() {
		return client;
	}

	public void setClient(ClientSummary client) {
		this.client = client;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	@Override
	public String toString() {
		return "RemissionOutputParamsReport [dateFrom=" + dateFrom + ", dateTo=" + dateTo + ", client=" + client
				+ ", product=" + product + ", branch=" + branch + "]";
	}

}
