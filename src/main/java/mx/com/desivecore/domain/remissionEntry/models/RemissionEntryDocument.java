package mx.com.desivecore.domain.remissionEntry.models;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RemissionEntryDocument {

	private InputStream logo;

	private InputStream sing;

	private String supplierName;

	private String street;

	private String colony;

	private String municipality;

	private String externalNumber;

	private String cp;

	private String folio;

	private String creationDate;

	private String requestDate;

	private String conditions;

	private String observations;

	private Double subTotal;

	private Double ivaTotal;

	private Double total;

	private List<RemissionEntryDetail> remissionDetail;

	public RemissionEntryDocument(RemissionEntry remissionEntry) {

		supplierName = remissionEntry.getSupplier().getBusinessName();

		street = remissionEntry.getBranch().getStreet();
		colony = remissionEntry.getBranch().getColony();
		municipality = remissionEntry.getBranch().getMunicipality();
		externalNumber = remissionEntry.getBranch().getExternalNumber();
		cp = remissionEntry.getBranch().getCp();

		folio = remissionEntry.getFolio();

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		creationDate = simpleDateFormat.format(remissionEntry.getCreationDate());
		requestDate = simpleDateFormat.format(remissionEntry.getRequestDate());

		conditions = remissionEntry.getConditions();
		observations = remissionEntry.getObservations();

		subTotal = 0.0;
		remissionDetail = new ArrayList<>();
		for (ProductEntry reEntry : remissionEntry.getProducts()) {
			subTotal += reEntry.getNet();
			remissionDetail.add(new RemissionEntryDetail(reEntry));
		}
		
		subTotal = (double) Math.round(subTotal * 100) / 100;
		ivaTotal = remissionEntry.getIvaTotal();
		total = remissionEntry.getRemissionTotal();

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

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getColony() {
		return colony;
	}

	public void setColony(String colony) {
		this.colony = colony;
	}

	public String getMunicipality() {
		return municipality;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	public String getExternalNumber() {
		return externalNumber;
	}

	public void setExternalNumber(String externalNumber) {
		this.externalNumber = externalNumber;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
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

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
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

	public List<RemissionEntryDetail> getRemissionDetail() {
		return remissionDetail;
	}

	public void setRemissionDetail(List<RemissionEntryDetail> remissionDetail) {
		this.remissionDetail = remissionDetail;
	}

	@Override
	public String toString() {
		return "RemissionEntryDocument [logo=" + logo + ", sing=" + sing + ", supplierName=" + supplierName
				+ ", street=" + street + ", colony=" + colony + ", municipality=" + municipality + ", externalNumber="
				+ externalNumber + ", cp=" + cp + ", folio=" + folio + ", creationDate=" + creationDate
				+ ", requestDate=" + requestDate + ", conditions=" + conditions + ", observations=" + observations
				+ ", subTotal=" + subTotal + ", ivaTotal=" + ivaTotal + ", total=" + total + ", remissionDetail="
				+ remissionDetail + "]";
	}

}
