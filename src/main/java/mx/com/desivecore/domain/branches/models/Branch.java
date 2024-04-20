package mx.com.desivecore.domain.branches.models;

import java.util.List;

public class Branch {

	private Long branchId;

	private String name;

	private String street;

	private String externalNumber;

	private String municipality;

	private String colony;

	private String cp;

	private String state;

	private List<BranchPhone> phones;

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<BranchPhone> getPhones() {
		return phones;
	}

	public void setPhones(List<BranchPhone> phones) {
		this.phones = phones;
	}

	@Override
	public String toString() {
		return "Branch [branchId=" + branchId + ", name=" + name + ", street=" + street + ", externalNumber="
				+ externalNumber + ", municipality=" + municipality + ", colony=" + colony + ", cp=" + cp + ", state="
				+ state + ", phones=" + phones + "]";
	}

}
