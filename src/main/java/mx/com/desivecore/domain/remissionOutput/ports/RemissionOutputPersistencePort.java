package mx.com.desivecore.domain.remissionOutput.ports;

import java.math.BigInteger;
import java.util.List;

import mx.com.desivecore.commons.models.ResponseModel;
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutput;
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutputSearchParams;
import mx.com.desivecore.domain.remissionOutput.models.RemissionOutputSummary;
import mx.com.desivecore.domain.remissionOutput.models.document.RemissionOutputDocument;

public interface RemissionOutputPersistencePort {

	RemissionOutput saveRemissionOutput(RemissionOutput remissionOutput);

	RemissionOutput updateRemissionOutput(RemissionOutput remissionOutput);

	BigInteger getConsecutiveByCode(String code);

	RemissionOutput viewRemissionById(Long remissionOutputId);

	List<RemissionOutputSummary> searchRemissionOutputByParams(RemissionOutputSearchParams outputSearchParams);

	ResponseModel generateRemissionDocument(RemissionOutputDocument remissionOutputDocument);

}