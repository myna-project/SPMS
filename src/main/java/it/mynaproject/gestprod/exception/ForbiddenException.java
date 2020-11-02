package it.mynaproject.gestprod.exception;

public class ForbiddenException extends RuntimeException {

	private static final long serialVersionUID = 3221366047232938662L;

	private Integer code;

	public ForbiddenException(String msg) {
		super(msg);
	}

	public ForbiddenException(Integer code, String msg) {
		super(msg);
		this.setCode(code);
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}
