package it.mynaproject.gestprod.exception;

public class GenericException extends RuntimeException {

	private static final long serialVersionUID = -3939904389235614250L;

	private Integer code;

	public GenericException(Integer code, String msg) {
		super(msg);
		this.setCode(code);
	}

	public GenericException(Integer code, String msg, Throwable cause) {
		super(msg, cause);
		this.setCode(code);
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}