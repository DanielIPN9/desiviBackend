package mx.com.desivecore.domain.returnRemissionOutput.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.domain.clients.models.ClientSummary;
import mx.com.desivecore.domain.remissionOutput.models.ProductOutput;
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutput;
import mx.com.desivecore.domain.users.models.UserModel;

public class ReturnRemissionOutput {

	private Long returnROId;

	private String folio;

	private Date creationDate;

	private Branch branch;

	private ClientSummary client;

	private UserModel user;

	private String obserbations;

	private List<ReturnProductOutput> products;

	private Double iva;

	private Double subTotal;

	private Double total;

	public ReturnRemissionOutput() {
		super();
	}

	public ReturnRemissionOutput(RemissionOutput remissionOutput) {
		super();
		this.returnROId = null;
		this.folio = remissionOutput.getFolio();
		this.creationDate = new Date();
		this.branch = remissionOutput.getBranch();
		this.client = remissionOutput.getClient();
		this.user = remissionOutput.getUser();
		this.obserbations = remissionOutput.getObserbations();

		products = new ArrayList<>();
		for (ProductOutput productOutput : remissionOutput.getProducts()) {
			products.add(new ReturnProductOutput(productOutput));
		}

		Double ivaTotal = 0.0;
		Double subTtal = 0.0;
		Double total = 0.0;

		for (ReturnProductOutput returnProductOutput : products) {
			ivaTotal += returnProductOutput.getNet() * returnProductOutput.getIva();
			subTtal += returnProductOutput.getNet();
			total += returnProductOutput.getTotal();
		}

		this.iva = (double) Math.round(ivaTotal * 100) / 100;
		this.subTotal = (double) Math.round(subTtal * 100) / 100;
		this.total = (double) Math.round(total * 100) / 100;
	}

	public Long getReturnROId() {
		return returnROId;
	}

	public void setReturnROId(Long returnROId) {
		this.returnROId = returnROId;
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

	public ClientSummary getClient() {
		return client;
	}

	public void setClient(ClientSummary client) {
		this.client = client;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public String getObserbations() {
		return obserbations;
	}

	public void setObserbations(String obserbations) {
		this.obserbations = obserbations;
	}

	public List<ReturnProductOutput> getProducts() {
		return products;
	}

	public void setProducts(List<ReturnProductOutput> products) {
		this.products = products;
	}

	public Double getIva() {
		return iva;
	}

	public void setIva(Double iva) {
		this.iva = iva;
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
		return "ReturnRemissionOutput [returnROId=" + returnROId + ", folio=" + folio + ", creationDate=" + creationDate
				+ ", branch=" + branch + ", client=" + client + ", user=" + user + ", obserbations=" + obserbations
				+ ", products=" + products + ", iva=" + iva + ", subTotal=" + subTotal + ", total=" + total + "]";
	}

}
