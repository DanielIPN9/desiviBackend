package mx.com.desivecore.domain.remissionOutput.ports;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutput;
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutputSearchParams;

public interface RemissionOutputServicePort {
	
	ResponseModel createRemissionOutput(RemissionOutput remissionOutput);
	
	ResponseModel viewRemissionById(Long remssionOutputId);
	
	ResponseModel searchRemissionOutputByParams(RemissionOutputSearchParams remissionOutputSearchParams);
	
	ResponseModel updateRemissionOutput(RemissionOutput remissionOutput);
	
	ResponseModel generateRemissionDocumentById(Long remissionOutputId);
	
	ResponseModel viewClientActiveList();
	
	ResponseModel viewBranchActiveList();
	
	ResponseModel viewProductActiveList(Long branchId);
	
	ResponseModel viewAllByUserLogin(String emailUser);

}
