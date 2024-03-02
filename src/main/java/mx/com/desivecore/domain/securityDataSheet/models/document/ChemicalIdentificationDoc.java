package mx.com.desivecore.domain.securityDataSheet.models.document;

public class ChemicalIdentificationDoc {

	private String name;

	private String value;

	public ChemicalIdentificationDoc(String name, String value) {
		super();
		this.name = name;
		this.value = value;
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
		return "ChemicalIdentificationDoc [name=" + name + ", value=" + value + "]";
	}

}
