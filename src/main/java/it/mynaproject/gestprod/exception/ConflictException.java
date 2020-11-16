package it.mynaproject.gestprod.exception;

public class ConflictException extends RuntimeException {

	private static final long serialVersionUID = -3939904389235614250L;

	private Integer code;
	
	private Integer error_code;

	public ConflictException(String msg) {
		super(msg);
	}

	public ConflictException(Integer code, Integer error_code, String msg) {
		super(msg);
		this.setCode(code);
		this.setError_code(error_code);
	}

	public ConflictException(Integer code, Integer error_code, String msg, Throwable cause) {
		super(msg, cause);
		this.setCode(code);
		this.setError_code(error_code);
	}
	
/** GETTERS and SETTERS **/

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getError_code() {
		return error_code;
	}

	public void setError_code(Integer error_code) {
		this.error_code = error_code;
	}
}
