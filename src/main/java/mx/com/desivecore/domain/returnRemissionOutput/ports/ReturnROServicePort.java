package mx.com.desivecore.domain.returnRemissionOutput.ports;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutput;
import mx.com.desivecore.domain.returnRemissionOutput.models.ReturnROSearchParams;

public interface ReturnROServicePort {

	ResponseModel searchRemissionOutputSummaryByFolio(String folio);

	ResponseModel generateReturnRemissionOutput(RemissionOutput remissionOutput);

	ResponseModel generateReturnRODocumentById(Long returnROId);

	ResponseModel viewReturnRODetailById(Long returnROId);

	ResponseModel viewAllReturnRemissionOutput();

	ResponseModel searchReturnRemissionOutputByParams(ReturnROSearchParams returnROSearchParams);
	
	ResponseModel viewClientActiveList();
	
	ResponseModel viewBranchActiveList();

}
