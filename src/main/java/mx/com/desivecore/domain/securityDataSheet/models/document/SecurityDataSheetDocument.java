package mx.com.desivecore.domain.securityDataSheet.models.document;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import mx.com.desivecore.domain.securityDataSheet.models.ChemicalIdentification;
import mx.com.desivecore.domain.securityDataSheet.models.ChemicalSpecification;
import mx.com.desivecore.domain.securityDataSheet.models.SecurityDataSheet;

public class SecurityDataSheetDocument {

	private InputStream logo;

	private InputStream figure;

	private Integer flammability;

	private Integer reactivity;

	private Integer health;

	private Integer specialHazard;

	private String productName;

	private String tradeName;

	private String chemicalFamily;

	private String chemicalFormula;

	private String synonym;

	private String molecularWeight;

	List<ChemicalIdentificationDoc> chIdentifications;

	List<ChemicalSpecificationDoc> chSpecifications;

	private String description;

	private String application;

	private String commercialUse;

	public SecurityDataSheetDocument(SecurityDataSheet securityDataSheet) {

		this.flammability = securityDataSheet.getFlammability();
		this.reactivity = securityDataSheet.getReactivity();
		this.health = securityDataSheet.getHealth();
		this.specialHazard = securityDataSheet.getSpecialHazard();
		this.productName = securityDataSheet.getProduct().getName();
		this.tradeName = securityDataSheet.getTradeName();
		this.chemicalFamily = securityDataSheet.getChemicalFamily();
		this.chemicalFormula = securityDataSheet.getChemicalFormula();
		this.synonym = securityDataSheet.getSynonym();
		this.molecularWeight = securityDataSheet.getMolecularWeight();

		List<ChemicalIdentificationDoc> chemicalIdentificationDocList = new ArrayList<>();
		for (ChemicalIdentification chemicalIdentification : securityDataSheet.getChIdentifications()) {
			chemicalIdentificationDocList.add(
					new ChemicalIdentificationDoc(chemicalIdentification.getName(), chemicalIdentification.getValue()));
		}
		this.chIdentifications = chemicalIdentificationDocList;

		List<ChemicalSpecificationDoc> chemicalSpecificationDocList = new ArrayList<>();
		for (ChemicalSpecification chemicalSpecification : securityDataSheet.getChSpecifications()) {
			chemicalSpecificationDocList.add(
					new ChemicalSpecificationDoc(chemicalSpecification.getName(), chemicalSpecification.getValue()));
		}
		this.chSpecifications = chemicalSpecificationDocList;

		this.description = securityDataSheet.getDescription();
		this.application = securityDataSheet.getApplication();
		this.commercialUse = securityDataSheet.getCommercialUse();
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public List<ChemicalIdentificationDoc> getChIdentifications() {
		return chIdentifications;
	}

	public void setChIdentifications(List<ChemicalIdentificationDoc> chIdentifications) {
		this.chIdentifications = chIdentifications;
	}

	public List<ChemicalSpecificationDoc> getChSpecifications() {
		return chSpecifications;
	}

	public void setChSpecifications(List<ChemicalSpecificationDoc> chSpecifications) {
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

	public InputStream getLogo() {
		return logo;
	}

	public void setLogo(InputStream logo) {
		this.logo = logo;
	}

	public InputStream getFigure() {
		return figure;
	}

	public void setFigure(InputStream figure) {
		this.figure = figure;
	}

	@Override
	public String toString() {
		return "SecurityDataSheetDocument [flammability=" + flammability + ", reactivity=" + reactivity + ", health="
				+ health + ", specialHazard=" + specialHazard + ", productName=" + productName + ", tradeName="
				+ tradeName + ", chemicalFamily=" + chemicalFamily + ", chemicalFormula=" + chemicalFormula
				+ ", synonym=" + synonym + ", molecularWeight=" + molecularWeight + ", chIdentifications="
				+ chIdentifications + ", chSpecifications=" + chSpecifications + ", description=" + description
				+ ", application=" + application + ", commercialUse=" + commercialUse + "]";
	}

}
