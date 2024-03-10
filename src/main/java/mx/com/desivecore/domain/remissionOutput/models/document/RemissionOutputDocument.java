package mx.com.desivecore.domain.remissionOutput.models.document;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import mx.com.desivecore.domain.clients.models.Client;
import mx.com.desivecore.domain.remissionOutput.models.ProductOutput;
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutput;

public class RemissionOutputDocument {

	private InputStream logo;

	private InputStream sing;

	private String clientBusinessName;

	private String clientFullAddress;

	private String creationDate;

	private String requestDate;

	private String folio;

	private Double subTotal;

	private Double ivaTotal;

	private Double total;

	private List<RemissionOutputDetailDocument> remissionDetail;

	public RemissionOutputDocument(RemissionOutput remissionOutput, Client client) {

		clientBusinessName = client.getBusinessName();
		clientFullAddress = client.getStreet() + " " + "NoInt. " + client.getInternalNumber() + " NoExt. "
				+ client.getExternalNumber() + " " + client.getColony() + " " + client.getMunicipality() + " "
				+ client.getCp();
		folio = remissionOutput.getFolio();

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		creationDate = simpleDateFormat.format(remissionOutput.getCreationDate());
		requestDate = simpleDateFormat.format(remissionOutput.getRequestDay());

		subTotal = 0.0;
		remissionDetail = new ArrayList<>();
		for (ProductOutput productOutput : remissionOutput.getProducts()) {
			subTotal += productOutput.getNet();
			remissionDetail.add(new RemissionOutputDetailDocument(productOutput));
		}

		subTotal = (double) Math.round(subTotal * 100) / 100;
		ivaTotal = remissionOutput.getIvaTotal();
		total = remissionOutput.getRemissionTotal();
	}

	public InputStream getLogo() {
		return logo;
	}

	public void setLogo(InputStream logo) {
		this.logo = logo;
	}

	public InputStream getSing() {
		return sing;
	}

	public void setSing(InputStream sing) {
		this.sing = sing;
	}

	public String getClientBusinessName() {
		return clientBusinessName;
	}

	public void setClientBusinessName(String clientBusinessName) {
		this.clientBusinessName = clientBusinessName;
	}

	public String getClientFullAddress() {
		return clientFullAddress;
	}

	public void setClientFullAddress(String clientFullAddress) {
		this.clientFullAddress = clientFullAddress;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
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

	public List<RemissionOutputDetailDocument> getRemissionDetail() {
		return remissionDetail;
	}

	public void setRemissionDetail(List<RemissionOutputDetailDocument> remissionDetail) {
		this.remissionDetail = remissionDetail;
	}

	@Override
	public String toString() {
		return "RemissionOutputDocument [logo=" + logo + ", sing=" + sing + ", clientBusinessName=" + clientBusinessName
				+ ", clientFullAddress=" + clientFullAddress + ", creationDate=" + creationDate + ", requestDate="
				+ requestDate + ", folio=" + folio + ", subTotal=" + subTotal + ", ivaTotal=" + ivaTotal + ", total="
				+ total + ", remissionDetail=" + remissionDetail + "]";
	}

}
