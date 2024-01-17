package mx.com.desivecore.infraestructure.configuration.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.java.Log;
import mx.com.desivecore.commons.models.ResponseModel;

@Log
@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ InternalError.class })
	public ResponseEntity<ResponseModel> internalError(Exception ex) {
		return this.generateBody("Error Interno. Contacte con su administrador.", null, "500");
	}

	@ExceptionHandler({ ValidationError.class })
	public ResponseEntity<ResponseModel> validationError(Exception ex) {
		return this.generateBody(ex.getMessage(), null, "400");
	}

	private ResponseEntity<ResponseModel> generateBody(String message, Object data, String statusCode) {
		log.info(String.format("Exception: message: %s, statusCode: %s", message, statusCode));
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(message, data, statusCode));
	}
}
