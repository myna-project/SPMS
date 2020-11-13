package it.mynaproject.gestprod.model;

public class ErrorResponse {

	private Integer errorCode;

	private String errorMessage;

	public ErrorResponse(Integer errorCode, String errorMessage) {
		this.setErrorCode(errorCode);
		this.setErrorMessage(errorMessage);
	}
	
/** GETTERS and SETTERS **/
	
	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
