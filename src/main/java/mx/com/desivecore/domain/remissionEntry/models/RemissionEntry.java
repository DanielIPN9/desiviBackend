package mx.com.desivecore.domain.remissionEntry.models;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.domain.suppliers.models.Supplier;
import mx.com.desivecore.domain.users.models.UserModel;

public class RemissionEntry {

	private Long remissionEntryId;

	private String folio;

	private Date creationDate;

	private Date requestDate;

	private Branch branch;

	private Supplier supplier;

	private UserModel user;

	private String observations;

	private String conditions;

	private List<ProductEntry> products;

	private Double remissionTotal;

	private Double ivaTotal;

	private boolean status;

	public void generateRemissionEntrySummary(UserModel user, String serialNumber) {
		Calendar calendar = Calendar.getInstance();
		creationDate = calendar.getTime();
		folio = "OC DIV " + calendar.get(Calendar.DAY_OF_MONTH) + "-" + serialNumber;
		remissionTotal = 0.0;
		ivaTotal = 0.0;
		this.user = user;
		for (ProductEntry productEntry : products) {
			productEntry.generateRemissionSummary();
			ivaTotal += productEntry.getNet() * productEntry.getIva();
			remissionTotal += productEntry.getTotal();
		}

		ivaTotal = (double) Math.round(ivaTotal * 100) / 100;
		remissionTotal = (double) Math.round(remissionTotal * 100) / 100;
		status = true;
	}

	public void generateRemissionEntrySummaryToUpdate(RemissionEntry remissionEntrySaved) {

		remissionEntryId = remissionEntrySaved.getRemissionEntryId();
		folio = remissionEntrySaved.getFolio();
		creationDate = remissionEntrySaved.getCreationDate();
		user = remissionEntrySaved.getUser();

		remissionTotal = 0.0;
		ivaTotal = 0.0;
		for (ProductEntry productEntry : products) {
			productEntry.generateRemissionSummary();
			ivaTotal += productEntry.getNet() * productEntry.getIva();
			remissionTotal += productEntry.getTotal();
		}
		ivaTotal = (double) Math.round(ivaTotal * 100) / 100;
		remissionTotal = (double) Math.round(remissionTotal * 100) / 100;
		status = true;
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

	public Long getRemissionEntryId() {
		return remissionEntryId;
	}

	public void setRemissionEntryId(Long remissionEntryId) {
		this.remissionEntryId = remissionEntryId;
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

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
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

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public List<ProductEntry> getProducts() {
		return products;
	}

	public void setProducts(List<ProductEntry> products) {
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
		return "RemissionEntry [remissionEntryId=" + remissionEntryId + ", folio=" + folio + ", creationDate="
				+ creationDate + ", requestDate=" + requestDate + ", branch=" + branch + ", supplier=" + supplier
				+ ", user=" + user + ", observations=" + observations + ", conditions=" + conditions + ", products="
				+ products + ", remissionTotal=" + remissionTotal + ", ivaTotal=" + ivaTotal + ", status=" + status
				+ "]";
	}

}
