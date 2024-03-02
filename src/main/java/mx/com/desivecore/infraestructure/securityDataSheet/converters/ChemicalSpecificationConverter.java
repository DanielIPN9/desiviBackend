package mx.com.desivecore.infraestructure.securityDataSheet.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.securityDataSheet.models.ChemicalSpecification;
import mx.com.desivecore.infraestructure.securityDataSheet.entities.ChemicalSpecificationEntity;

@Component
public class ChemicalSpecificationConverter {

	public ChemicalSpecificationEntity chemicalSpecificationToEntity(ChemicalSpecification chemicalSpecification,
			Long secDataSheetId) {

		ChemicalSpecificationEntity chemicalSpecificationEntity = new ChemicalSpecificationEntity();

		chemicalSpecificationEntity.setSecurityDataSheetId(secDataSheetId);
		chemicalSpecificationEntity.setName(chemicalSpecification.getName());
		chemicalSpecificationEntity.setValue(chemicalSpecification.getValue());

		return chemicalSpecificationEntity;
	}

	public ChemicalSpecification entityToChemicalSpecification(
			ChemicalSpecificationEntity chemicalSpecificationEntity) {

		ChemicalSpecification chemicalSpecification = new ChemicalSpecification();

		chemicalSpecification.setSecurityDataSheet(chemicalSpecificationEntity.getSecurityDataSheetId());
		chemicalSpecification.setName(chemicalSpecificationEntity.getName());
		chemicalSpecification.setValue(chemicalSpecificationEntity.getValue());

		return chemicalSpecification;
	}

	public List<ChemicalSpecificationEntity> chemicalSpecificationListToEntityList(
			List<ChemicalSpecification> chemicalSpecificationList, Long secDataSheetId) {
		List<ChemicalSpecificationEntity> chemicalSpecificationEntityList = new ArrayList<>();
		for (ChemicalSpecification chemicalSpecification : chemicalSpecificationList) {
			chemicalSpecificationEntityList.add(chemicalSpecificationToEntity(chemicalSpecification, secDataSheetId));
		}
		return chemicalSpecificationEntityList;
	}

	public List<ChemicalSpecification> entityListToChemicalSpecificationList(
			List<ChemicalSpecificationEntity> chemicalSpecificationEntityList) {
		List<ChemicalSpecification> chemicalSpecificationList = new ArrayList<>();
		for (ChemicalSpecificationEntity chemicalSpecificationEntity : chemicalSpecificationEntityList) {
			chemicalSpecificationList.add(entityToChemicalSpecification(chemicalSpecificationEntity));
		}
		return chemicalSpecificationList;
	}
}
