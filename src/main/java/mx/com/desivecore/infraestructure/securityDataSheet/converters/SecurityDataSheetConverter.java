package mx.com.desivecore.infraestructure.securityDataSheet.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.securityDataSheet.models.SecurityDataSheet;
import mx.com.desivecore.infraestructure.securityDataSheet.entities.SecurityDataSheetEntity;

@Component
public class SecurityDataSheetConverter {

	public SecurityDataSheetEntity securityDataSheetToEntity(SecurityDataSheet securityDataSheet) {

		SecurityDataSheetEntity securityDataSheetEntity = new SecurityDataSheetEntity();

		securityDataSheetEntity.setSecurityDataSheetId(securityDataSheet.getSecurityDataSheetId());
		securityDataSheetEntity.setFlammability(securityDataSheet.getFlammability());
		securityDataSheetEntity.setReactivity(securityDataSheet.getReactivity());
		securityDataSheetEntity.setHealth(securityDataSheet.getHealth());
		securityDataSheetEntity.setSpecialHazard(securityDataSheet.getSpecialHazard());
		securityDataSheetEntity.setProductId(securityDataSheet.getProduct().getProductId());
		securityDataSheetEntity.setTradeName(securityDataSheet.getTradeName());
		securityDataSheetEntity.setChemicalFamily(securityDataSheet.getChemicalFamily());
		securityDataSheetEntity.setChemicalFormula(securityDataSheet.getChemicalFormula());
		securityDataSheetEntity.setSynonym(securityDataSheet.getSynonym());
		securityDataSheetEntity.setMolecularWeight(securityDataSheet.getMolecularWeight());
		securityDataSheetEntity.setDescription(securityDataSheet.getDescription());
		securityDataSheetEntity.setApplication(securityDataSheet.getApplication());
		securityDataSheetEntity.setCommercialUse(securityDataSheet.getCommercialUse());

		return securityDataSheetEntity;
	}

	public SecurityDataSheet entityToSecurityDataSheet(SecurityDataSheetEntity securityDataSheetEntity) {

		SecurityDataSheet securityDataSheet = new SecurityDataSheet();

		securityDataSheet.setSecurityDataSheetId(securityDataSheetEntity.getSecurityDataSheetId());
		securityDataSheet.setFlammability(securityDataSheetEntity.getFlammability());
		securityDataSheet.setReactivity(securityDataSheetEntity.getReactivity());
		securityDataSheet.setHealth(securityDataSheetEntity.getHealth());
		securityDataSheet.setSpecialHazard(securityDataSheetEntity.getSpecialHazard());
		securityDataSheet.setTradeName(securityDataSheetEntity.getTradeName());
		securityDataSheet.setChemicalFamily(securityDataSheetEntity.getChemicalFamily());
		securityDataSheet.setChemicalFormula(securityDataSheetEntity.getChemicalFormula());
		securityDataSheet.setSynonym(securityDataSheetEntity.getSynonym());
		securityDataSheet.setMolecularWeight(securityDataSheetEntity.getMolecularWeight());
		securityDataSheet.setDescription(securityDataSheetEntity.getDescription());
		securityDataSheet.setApplication(securityDataSheetEntity.getApplication());
		securityDataSheet.setCommercialUse(securityDataSheetEntity.getCommercialUse());

		return securityDataSheet;
	}

	public List<SecurityDataSheetEntity> securityDataSheetListToEntityList(
			List<SecurityDataSheet> securityDataSheetList) {
		List<SecurityDataSheetEntity> securityDataSheetEntityList = new ArrayList<>();
		for (SecurityDataSheet securityDataSheet : securityDataSheetList) {
			securityDataSheetEntityList.add(securityDataSheetToEntity(securityDataSheet));
		}
		return securityDataSheetEntityList;
	}

	public List<SecurityDataSheet> entityListToSecurityDataSheetList(
			List<SecurityDataSheetEntity> securityDataSheetEntityList) {
		List<SecurityDataSheet> securityDataSheetList = new ArrayList<>();
		for (SecurityDataSheetEntity securityDataSheetEntity : securityDataSheetEntityList) {
			securityDataSheetList.add(entityToSecurityDataSheet(securityDataSheetEntity));
		}
		return securityDataSheetList;
	}
}
