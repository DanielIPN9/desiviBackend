package mx.com.desivecore.infraestructure.quarantine.converters;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import mx.com.desivecore.domain.quarantine.models.QuarantineAction;
import mx.com.desivecore.infraestructure.quarantine.entities.QuarantineActionEntity;

@Component
public class QuarantineActionConverter {

	public QuarantineAction entityToQuarantineAction(QuarantineActionEntity quarantineActionEntity) {

		QuarantineAction quarantineAction = new QuarantineAction();

		quarantineAction.setId(quarantineActionEntity.getId());
		quarantineAction.setActionCode(quarantineActionEntity.getActionCode());
		quarantineAction.setDescription(quarantineActionEntity.getDescription());

		return quarantineAction;
	}

	public List<QuarantineAction> entityListToQuarantineActionList(
			List<QuarantineActionEntity> quarantineActionEntityList) {
		List<QuarantineAction> quarantineActionList = quarantineActionEntityList.stream()
				.map(quarantineActionEntity -> entityToQuarantineAction(quarantineActionEntity))
				.collect(Collectors.toList());
		return quarantineActionList;
	}
}
