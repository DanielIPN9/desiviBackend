package mx.com.desivecore.commons.models;

public class ResponseModel {

	private static final String STATUS_CODE_SUCCESS = "200";

	private String message;

	private Object data;

	private String statusCode;
	
	public ResponseModel(Object data) {
		super();
		this.message = "Operación completada";
		this.data = data;
		this.statusCode = STATUS_CODE_SUCCESS;
	}

	public ResponseModel(String message, Object data, String statusCode) {
		super();
		this.message = message;
		this.data = data;
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

}
