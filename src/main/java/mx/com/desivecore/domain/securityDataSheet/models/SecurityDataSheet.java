package mx.com.desivecore.domain.securityDataSheet.models;

import java.util.List;

import mx.com.desivecore.domain.products.models.Product;

public class SecurityDataSheet {

	private Long securityDataSheetId;

	private Integer flammability;

	private Integer reactivity;

	private Integer health;

	private Integer specialHazard;

	private Product product;

	private String tradeName;

	private String chemicalFamily;

	private String chemicalFormula;

	private String synonym;

	private String molecularWeight;

	private List<ChemicalIdentification> chIdentifications;

	private List<ChemicalSpecification> chSpecifications;

	private String description;

	private String application;

	private String commercialUse;

	public Long getSecurityDataSheetId() {
		return securityDataSheetId;
	}

	public void setSecurityDataSheetId(Long securityDataSheetId) {
		this.securityDataSheetId = securityDataSheetId;
	}

	public Integer getFlammability() {
		return flammability;
	}

	public void setFlammability(Integer flammability) {
		this.flammability = flammability;
	}

	public Integer getReactivity() {
		return reactivity;
	}

	public void setReactivity(Integer reactivity) {
		this.reactivity = reactivity;
	}

	public Integer getHealth() {
		return health;
	}

	public void setHealth(Integer health) {
		this.health = health;
	}

	public Integer getSpecialHazard() {
		return specialHazard;
	}

	public void setSpecialHazard(Integer specialHazard) {
		this.specialHazard = specialHazard;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getChemicalFamily() {
		return chemicalFamily;
	}

	public void setChemicalFamily(String chemicalFamily) {
		this.chemicalFamily = chemicalFamily;
	}

	public String getChemicalFormula() {
		return chemicalFormula;
	}

	public void setChemicalFormula(String chemicalFormula) {
		this.chemicalFormula = chemicalFormula;
	}

	public String getSynonym() {
		return synonym;
	}

	public void setSynonym(String synonym) {
		this.synonym = synonym;
	}

	public String getMolecularWeight() {
		return molecularWeight;
	}

	public void setMolecularWeight(String molecularWeight) {
		this.molecularWeight = molecularWeight;
	}

	public List<ChemicalIdentification> getChIdentifications() {
		return chIdentifications;
	}

	public void setChIdentifications(List<ChemicalIdentification> chIdentifications) {
		this.chIdentifications = chIdentifications;
	}

	public List<ChemicalSpecification> getChSpecifications() {
		return chSpecifications;
	}

	public void setChSpecifications(List<ChemicalSpecification> chSpecifications) {
		this.chSpecifications = chSpecifications;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getCommercialUse() {
		return commercialUse;
	}

	public void setCommercialUse(String commercialUse) {
		this.commercialUse = commercialUse;
	}

	@Override
	public String toString() {
		return "SecurityDataSheet [securityDataSheetId=" + securityDataSheetId + ", flammability=" + flammability
				+ ", reactivity=" + reactivity + ", health=" + health + ", specialHazard=" + specialHazard
				+ ", product=" + product + ", tradeName=" + tradeName + ", chemicalFamily=" + chemicalFamily
				+ ", chemicalFormula=" + chemicalFormula + ", synonym=" + synonym + ", molecularWeight="
				+ molecularWeight + ", chIdentifications=" + chIdentifications + ", chSpecifications="
				+ chSpecifications + ", description=" + description + ", application=" + application
				+ ", commercialUse=" + commercialUse + "]";
	}

}
