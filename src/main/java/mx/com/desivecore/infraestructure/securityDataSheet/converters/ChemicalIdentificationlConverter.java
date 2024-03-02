package mx.com.desivecore.infraestructure.securityDataSheet.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.securityDataSheet.models.ChemicalIdentification;
import mx.com.desivecore.infraestructure.securityDataSheet.entities.ChemicalIdentificationEntity;

@Component
public class ChemicalIdentificationlConverter {

	public ChemicalIdentificationEntity chemicalIdentificationToEntity(ChemicalIdentification chemicalIdentification,
			Long secDataSheetId) {

		ChemicalIdentificationEntity chemicalIdentificationEntity = new ChemicalIdentificationEntity();

		chemicalIdentificationEntity.setSecurityDataSheetId(secDataSheetId);
		chemicalIdentificationEntity.setName(chemicalIdentification.getName());
		chemicalIdentificationEntity.setValue(chemicalIdentification.getValue());

		return chemicalIdentificationEntity;
	}

	public ChemicalIdentification entityToChemicalidentification(
			ChemicalIdentificationEntity chemicalIdentificationEntity) {

		ChemicalIdentification chemicalIdentification = new ChemicalIdentification();

		chemicalIdentification.setSecurityDataSheet(chemicalIdentificationEntity.getSecurityDataSheetId());
		chemicalIdentification.setName(chemicalIdentificationEntity.getName());
		chemicalIdentification.setValue(chemicalIdentificationEntity.getValue());

		return chemicalIdentification;

	}

	public List<ChemicalIdentificationEntity> chemicalIdentificationListToEntityList(
			List<ChemicalIdentification> chemicalIdentificationList, Long secDataSheetId) {
		List<ChemicalIdentificationEntity> chemicalIdentificationEntityList = new ArrayList<>();
		for (ChemicalIdentification chemicalIdentification : chemicalIdentificationList) {
			chemicalIdentificationEntityList
					.add(chemicalIdentificationToEntity(chemicalIdentification, secDataSheetId));
		}
		return chemicalIdentificationEntityList;
	}

	public List<ChemicalIdentification> entityListToChemicalIdentificationList(
			List<ChemicalIdentificationEntity> chemicalIdentificationEntityList) {
		List<ChemicalIdentification> chemicalIdentificationList = new ArrayList<>();
		for (ChemicalIdentificationEntity chemicalIdentificationEntity : chemicalIdentificationEntityList) {
			chemicalIdentificationList.add(entityToChemicalidentification(chemicalIdentificationEntity));
		}
		return chemicalIdentificationList;
	}
}
