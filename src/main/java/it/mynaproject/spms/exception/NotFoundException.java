package it.mynaproject.spms.exception;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3939904389235614096L;

	private Integer code;

	public NotFoundException(String msg) {
		super(msg);
	}

	public NotFoundException(Integer code, String msg) {
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
