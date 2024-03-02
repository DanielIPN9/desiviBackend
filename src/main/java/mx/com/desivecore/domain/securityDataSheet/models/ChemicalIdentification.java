package mx.com.desivecore.domain.securityDataSheet.models;

public class ChemicalIdentification {

	private Long securityDataSheet;

	private String name;

	private String value;

	public Long getSecurityDataSheet() {
		return securityDataSheet;
	}

	public void setSecurityDataSheet(Long securityDataSheet) {
		this.securityDataSheet = securityDataSheet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ChemicalIdentification [securityDataSheet=" + securityDataSheet + ", name=" + name + ", value=" + value
				+ "]";
	}

}
