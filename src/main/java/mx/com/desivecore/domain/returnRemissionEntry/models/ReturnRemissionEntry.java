package mx.com.desivecore.domain.returnRemissionEntry.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.domain.remissionEntry.models.ProductEntry;
import mx.com.desivecore.domain.remissionEntry.models.RemissionEntry;
import mx.com.desivecore.domain.suppliers.models.Supplier;
import mx.com.desivecore.domain.users.models.UserModel;

public class ReturnRemissionEntry {

	private Long returnREId;

	private String folio;

	private Date creationDate;

	private Branch branch;

	private Supplier supplier;

	private UserModel user;

	private String observations;

	private List<ReturnProductEntry> products;

	private Double ivaTotal;

	private Double subTotal;

	private Double total;

	public ReturnRemissionEntry() {
		super();
	}

	public ReturnRemissionEntry(RemissionEntry remissionEntry) {
		this.returnREId = null;
		this.folio = remissionEntry.getFolio();
		this.creationDate = new Date();
		this.branch = remissionEntry.getBranch();
		this.supplier = remissionEntry.getSupplier();
		this.user = remissionEntry.getUser();
		this.observations = remissionEntry.getObservations();

		products = new ArrayList<>();
		for (ProductEntry productEntry : remissionEntry.getProducts()) {
			products.add(new ReturnProductEntry(productEntry));
		}

		Double ivaTotal = 0.0;
		Double subTtal = 0.0;
		Double total = 0.0;

		for (ReturnProductEntry returnProductEntry : products) {
			ivaTotal += returnProductEntry.getNet() * returnProductEntry.getIva();
			subTtal += returnProductEntry.getNet();
			total += returnProductEntry.getTotal();
		}

		this.ivaTotal = (double) Math.round(ivaTotal * 100) / 100;
		this.subTotal = (double) Math.round(subTtal * 100) / 100;
		this.total = (double) Math.round(total * 100) / 100;
	}

	public Long getReturnREId() {
		return returnREId;
	}

	public void setReturnREId(Long returnREId) {
		this.returnREId = returnREId;
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

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public List<ReturnProductEntry> getProducts() {
		return products;
	}

	public void setProducts(List<ReturnProductEntry> products) {
		this.products = products;
	}

	public Double getIvaTotal() {
		return ivaTotal;
	}

	public void setIvaTotal(Double ivaTotal) {
		this.ivaTotal = ivaTotal;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "ReturnRemissionEntry [returnREId=" + returnREId + ", folio=" + folio + ", creationDate=" + creationDate
				+ ", branch=" + branch + ", supplier=" + supplier + ", user=" + user + ", observations=" + observations
				+ ", products=" + products + ", ivaTotal=" + ivaTotal + ", subTotal=" + subTotal + ", total=" + total
				+ "]";
	}

}
