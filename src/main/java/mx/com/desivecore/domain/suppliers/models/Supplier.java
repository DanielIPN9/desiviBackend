package mx.com.desivecore.domain.suppliers.models;

public class Supplier {

	private Long supplierId;

	private String businessName;

	private String rfc;

	private String contactName;

	private String contactNumber;

	private String email;

	private boolean status;

	private String street;

	private String externalNumber;

	private String internalNumber;

	private String municipality;

	private String colony;

	private String cp;

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getExternalNumber() {
		return externalNumber;
	}

	public void setExternalNumber(String externalNumber) {
		this.externalNumber = externalNumber;
	}

	public String getInternalNumber() {
		return internalNumber;
	}

	public void setInternalNumber(String internalNumber) {
		this.internalNumber = internalNumber;
	}

	public String getMunicipality() {
		return municipality;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	public String getColony() {
		return colony;
	}

	public void setColony(String colony) {
		this.colony = colony;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	@Override
	public String toString() {
		return "Supplier [supplierId=" + supplierId + ", businessName=" + businessName + ", rfc=" + rfc
				+ ", contactName=" + contactName + ", contactNumber=" + contactNumber + ", email=" + email + ", status="
				+ status + ", street=" + street + ", externalNumber=" + externalNumber + ", internalNumber="
				+ internalNumber + ", municipality=" + municipality + ", colony=" + colony + ", cp=" + cp + "]";
	}

}
