package mx.com.desivecore.domain.quote.models;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.com.desivecore.domain.branches.models.Branch;
import mx.com.desivecore.domain.clients.models.ClientSummary;
import mx.com.desivecore.domain.users.models.UserModel;

public class QuoteOrder {

	private Long quoteOrderId;

	private String folio;

	private Date creationDate;

	private Date effectiveDate;

	private Branch branch;

	private ClientSummary client;

	private UserModel user;

	private List<QuoteProduct> products;

	private Double subTotal;

	private Double ivaTotal;

	private Double total;

	private String observation;

	private Boolean isConverter;

	private Boolean isEffective;

	public void generateQuoteOrderSummary(UserModel user, String serialNumber) {
		Calendar calendar = Calendar.getInstance();
		creationDate = calendar.getTime();
		folio = "CO DIV " + calendar.get(Calendar.DAY_OF_MONTH) + "-" + serialNumber;
		this.user = user;

		subTotal = 0.0;
		ivaTotal = 0.0;
		total = 0.0;

		for (QuoteProduct quoteProduct : products) {
			quoteProduct.generateQuoteSummary();
			subTotal += quoteProduct.getNet();
			ivaTotal += subTotal * quoteProduct.getIva();
			total += quoteProduct.getTotal();
		}

		subTotal = (double) Math.round(subTotal * 100) / 100;
		ivaTotal = (double) Math.round(ivaTotal * 100) / 100;
		total = (double) Math.round(total * 100) / 100;

		isConverter = false;
		isEffective = true;

	}

	public void generateQuoteOrderToUpdate(QuoteOrder quoteOrderSaved) {
		quoteOrderId = quoteOrderSaved.getQuoteOrderId();
		folio = quoteOrderSaved.getFolio();
		creationDate = quoteOrderSaved.getCreationDate();
		user = quoteOrderSaved.getUser();

		subTotal = 0.0;
		ivaTotal = 0.0;
		total = 0.0;

		for (QuoteProduct quoteProduct : products) {
			quoteProduct.generateQuoteSummary();
			subTotal += quoteProduct.getNet();
			ivaTotal += subTotal * quoteProduct.getIva();
			total += quoteProduct.getTotal();
		}

		subTotal = (double) Math.round(subTotal * 100) / 100;
		ivaTotal = (double) Math.round(ivaTotal * 100) / 100;
		total = (double) Math.round(total * 100) / 100;

		isConverter = false;
		isEffective = true;

	}

	public Long getQuoteOrderId() {
		return quoteOrderId;
	}

	public void setQuoteOrderId(Long quoteOrderId) {
		this.quoteOrderId = quoteOrderId;
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

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
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

	public List<QuoteProduct> getProducts() {
		return products;
	}

	public void setProducts(List<QuoteProduct> products) {
		this.products = products;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public Double getIvaTotal() {
		return ivaTotal;
	}

	public void setIvaTotal(Double ivaTotal) {
		this.ivaTotal = ivaTotal;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Boolean getIsConverter() {
		return isConverter;
	}

	public void setIsConverter(Boolean isConverter) {
		this.isConverter = isConverter;
	}

	public Boolean getIsEffective() {
		return isEffective;
	}

	public void setIsEffective(Boolean isEffective) {
		this.isEffective = isEffective;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	@Override
	public String toString() {
		return "QuoteOrder [quoteOrderId=" + quoteOrderId + ", folio=" + folio + ", creationDate=" + creationDate
				+ ", effectiveDate=" + effectiveDate + ", branch=" + branch + ", client=" + client + ", user=" + user
				+ ", products=" + products + ", subTotal=" + subTotal + ", ivaTotal=" + ivaTotal + ", total=" + total
				+ ", observation=" + observation + ", isConverter=" + isConverter + ", isEffective=" + isEffective
				+ "]";
	}

}
