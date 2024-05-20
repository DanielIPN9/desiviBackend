package mx.com.desivecore.domain.quote.models.document;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import mx.com.desivecore.domain.branches.models.BranchPhone;
import mx.com.desivecore.domain.clients.models.Client;
import mx.com.desivecore.domain.quote.models.QuoteOrder;
import mx.com.desivecore.domain.quote.models.QuoteProduct;

public class QuoteOrderDocument {

	private InputStream logo;

	private String folio;

	private String clientName;

	private String clientAddress;

	private String branchState;

	private String branchPhone;

	private String creationDate;

	private String effectiveDate;

	private String observation;

	private List<QuoteProductDocument> productDetail;

	private Double subTotal;

	private Double ivaTotal;

	private Double total;

	public QuoteOrderDocument(QuoteOrder quoteOrder, Client client) {
		
		folio = quoteOrder.getFolio();

		clientName = client.getBusinessName();
		clientAddress = client.getStreet() + " " + "NoInt. " + client.getInternalNumber() + " NoExt. "
				+ client.getExternalNumber() + " " + client.getColony() + " " + client.getMunicipality() + " "
				+ client.getCp();

		branchState = quoteOrder.getBranch().getState();

		branchPhone = "";
		if (quoteOrder.getBranch().getPhones() != null) {
			StringBuilder branchPhoneBuilder = new StringBuilder();
			for (BranchPhone phone : quoteOrder.getBranch().getPhones()) {
				branchPhoneBuilder.append(phone.getExtension()).append("-").append(phone.getPhone());
			}
			branchPhone = branchPhoneBuilder.toString();
		}

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		creationDate = simpleDateFormat.format(quoteOrder.getCreationDate());
		effectiveDate = simpleDateFormat.format(quoteOrder.getEffectiveDate());

		observation = quoteOrder.getObservation();

		productDetail = new ArrayList<>();
		for (QuoteProduct quoteProduct : quoteOrder.getProducts()) {
			productDetail.add(new QuoteProductDocument(quoteProduct));
		}

		subTotal = quoteOrder.getSubTotal();
		ivaTotal = quoteOrder.getIvaTotal();
		total = quoteOrder.getTotal();

	}

	public InputStream getLogo() {
		return logo;
	}

	public void setLogo(InputStream logo) {
		this.logo = logo;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	public String getBranchState() {
		return branchState;
	}

	public void setBranchState(String branchState) {
		this.branchState = branchState;
	}

	public String getBranchPhone() {
		return branchPhone;
	}

	public void setBranchPhone(String branchPhone) {
		this.branchPhone = branchPhone;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public List<QuoteProductDocument> getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(List<QuoteProductDocument> productDetail) {
		this.productDetail = productDetail;
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

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	@Override
	public String toString() {
		return "QuoteOrderDocument [logo=" + logo + ", folio=" + folio + ", clientName=" + clientName
				+ ", clientAddress=" + clientAddress + ", branchState=" + branchState + ", branchPhone=" + branchPhone
				+ ", creationDate=" + creationDate + ", effectiveDate=" + effectiveDate + ", observation=" + observation
				+ ", productDetail=" + productDetail + ", subTotal=" + subTotal + ", ivaTotal=" + ivaTotal + ", total="
				+ total + "]";
	}

}
