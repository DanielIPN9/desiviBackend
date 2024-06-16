package mx.com.desivecore.domain.reports.models.search;

import java.util.Date;

import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.domain.products.models.Product;
import mx.com.desivecore.domain.suppliers.models.Supplier;

public class RemissionEntryParamsReport {

	private Date dateFrom;

	private Date dateTo;

	private Supplier supplier;

	private Product product;

	private Branch branch;

	private String format;

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

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
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

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@Override
	public String toString() {
		return "RemissionEntryParamsReport [dateFrom=" + dateFrom + ", dateTo=" + dateTo + ", supplier=" + supplier
				+ ", product=" + product + ", branch=" + branch + ", format=" + format + "]";
	}

}
