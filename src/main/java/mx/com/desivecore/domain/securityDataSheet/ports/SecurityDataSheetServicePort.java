package mx.com.desivecore.domain.securityDataSheet.ports;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.securityDataSheet.models.SecurityDataSheet;

public interface SecurityDataSheetServicePort {
	
	ResponseModel viewProductActiveList();
	
	ResponseModel createSecurityDataShet(SecurityDataSheet securityDataSheet);
	
	ResponseModel viewSecurityDataSheetById(Long secuDataSheetId);
	
	ResponseModel viewAllSecurityDataSheet();
	
	ResponseModel updateSecurityDataSheetById(SecurityDataSheet securityDataSheet);
	
	ResponseModel generateDocumentSecurityDataSheetById(Long secuDataSheetId);

}
