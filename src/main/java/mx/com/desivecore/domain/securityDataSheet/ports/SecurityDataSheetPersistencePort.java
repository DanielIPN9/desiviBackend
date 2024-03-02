package mx.com.desivecore.domain.securityDataSheet.ports;

import java.util.List;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.securityDataSheet.models.SecurityDataSheet;
import mx.com.desivecore.domain.securityDataSheet.models.document.SecurityDataSheetDocument;

public interface SecurityDataSheetPersistencePort {

	SecurityDataSheet saveSecurityDataSheet(SecurityDataSheet securityDataSheet);

	SecurityDataSheet viewSecurityDataSheetById(Long secDataSheetId);

	SecurityDataSheet viewSecurityDataSheetByProductId(Long productId);

	SecurityDataSheet viewSecurityDataSheetByProductIdAndSecDataSheetIdNot(Long secDataSheetId, Long productId);

	List<SecurityDataSheet> viewAllSecurityDataSheet();

	ResponseModel generateDocumentSecurityDataSheet(SecurityDataSheetDocument securityDataSheetDocument);
}
