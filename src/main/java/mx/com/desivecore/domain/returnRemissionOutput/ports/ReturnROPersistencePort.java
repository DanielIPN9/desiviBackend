package mx.com.desivecore.domain.returnRemissionOutput.ports;

import java.util.List;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.returnRemissionOutput.models.ReturnProductOutput;
import mx.com.desivecore.domain.returnRemissionOutput.models.ReturnROSearchParams;
import mx.com.desivecore.domain.returnRemissionOutput.models.ReturnROSummary;
import mx.com.desivecore.domain.returnRemissionOutput.models.ReturnRemissionOutput;

public interface ReturnROPersistencePort {

	ReturnRemissionOutput generateReturnRemissionOutput(ReturnRemissionOutput returnRemissionOutput);

	ReturnRemissionOutput viewReturnRODetailById(Long returnROId);

	ResponseModel generateReturnRODocument();

	List<ReturnROSummary> viewAllReturnRemissionOutput();

	List<ReturnROSummary> searchReturnRemissionOutputByParams(ReturnROSearchParams returnROSearchParams);

	List<ReturnProductOutput> searchReturnProductByFolioRO(String folio);

}
