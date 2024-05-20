package mx.com.desivecore.domain.remissionOutput.models;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.domain.clients.models.ClientSummary;
import mx.com.desivecore.domain.users.models.UserModel;

public class RemissionOutput {

	private Long remissionOutputId;

	private String folio;

	private Date creationDate;

	private Date requestDay;

	private Branch branch;

	private ClientSummary client;

	private UserModel user;

	private List<ProductOutput> products;

	private Double ivaTotal;

	private Double remissionTotal;

	private boolean status;

	public void generateRemissionOutputSummary(UserModel user, String serialNumber) {
		Calendar calendar = Calendar.getInstance();
		creationDate = calendar.getTime();
		folio = "RS DIV " + calendar.get(Calendar.DAY_OF_MONTH) + "-" + serialNumber;
		this.user = user;

		remissionTotal = 0.0;
		ivaTotal = 0.0;
		for (ProductOutput productOutput : products) {
			productOutput.generateRemissionSummary();
			ivaTotal += productOutput.getNet() * productOutput.getIva();
			remissionTotal += productOutput.getTotal();
		}
		ivaTotal = (double) Math.round(ivaTotal * 100) / 100;
		remissionTotal = (double) Math.round(remissionTotal * 100) / 100;
		status = true;
	}

	public void generateRemissionOutputToUpdate(RemissionOutput remissionOutputSaved) {
		remissionOutputId = remissionOutputSaved.getRemissionOutputId();
		folio = remissionOutputSaved.getFolio();
		creationDate = remissionOutputSaved.getCreationDate();
		user = remissionOutputSaved.getUser();

		remissionTotal = 0.0;
		ivaTotal = 0.0;
		for (ProductOutput productOutput : products) {
			productOutput.generateRemissionSummary();
			ivaTotal += productOutput.getNet() * productOutput.getIva();
			remissionTotal += productOutput.getTotal();
		}
		ivaTotal = (double) Math.round(ivaTotal * 100) / 100;
		remissionTotal = (double) Math.round(remissionTotal * 100) / 100;
		status = true;
	}


	public Long getRemissionOutputId() {
		return remissionOutputId;
	}

	public void setRemissionOutputId(Long remissionOutputId) {
		this.remissionOutputId = remissionOutputId;
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

	public Date getRequestDay() {
		return requestDay;
	}

	public void setRequestDay(Date requestDay) {
		this.requestDay = requestDay;
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

	public Double getIvaTotal() {
		return ivaTotal;
	}

	public void setIvaTotal(Double ivaTotal) {
		this.ivaTotal = ivaTotal;
	}

	public Double getRemissionTotal() {
		return remissionTotal;
	}

	public void setRemissionTotal(Double remissionTotal) {
		this.remissionTotal = remissionTotal;
	}

	public List<ProductOutput> getProducts() {
		return products;
	}

	public void setProducts(List<ProductOutput> products) {
		this.products = products;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "RemissionOutput [remissionOutputId=" + remissionOutputId + ", folio=" + folio + ", creationDate="
				+ creationDate + ", requestDay=" + requestDay + ", branch=" + branch + ", client=" + client + ", user="
				+ user + ", products=" + products + ", ivaTotal=" + ivaTotal + ", remissionTotal=" + remissionTotal
				+ ", status=" + status + "]";
	}

}
