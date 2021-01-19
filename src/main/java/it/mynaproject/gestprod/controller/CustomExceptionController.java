package it.mynaproject.gestprod.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import it.mynaproject.gestprod.exception.ConflictException;
import it.mynaproject.gestprod.exception.ForbiddenException;
import it.mynaproject.gestprod.exception.GenericException;
import it.mynaproject.gestprod.exception.NotFoundException;
import it.mynaproject.gestprod.model.ErrorResponse;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ControllerAdvice
public class CustomExceptionController {

	final private Logger log = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public <T extends Exception> ResponseEntity<ErrorResponse> handleException(T exception, HttpServletResponse response) {

		log.error(exception.getMessage(), exception);

		if (exception instanceof JsonParseException || exception.getCause() instanceof JsonParseException
				|| exception instanceof UnrecognizedPropertyException || exception.getCause() instanceof UnrecognizedPropertyException
				|| exception instanceof JsonMappingException || exception.getCause() instanceof JsonMappingException
				|| exception instanceof ConversionFailedException || exception.getCause() instanceof ConversionFailedException
				|| exception instanceof MethodArgumentNotValidException || exception.getCause() instanceof MethodArgumentNotValidException) {
			return new ResponseEntity<>(new ErrorResponse(400, "Cannot perform this request!"), HttpStatus.BAD_REQUEST);
		}

		if (exception instanceof HttpRequestMethodNotSupportedException || exception.getCause() instanceof HttpRequestMethodNotSupportedException)
			return new ResponseEntity<>(new ErrorResponse(405, "Method not allowed!"), HttpStatus.METHOD_NOT_ALLOWED);

		return new ResponseEntity<>(new ErrorResponse(500, "Error occurred!"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ForbiddenException.class)
	@ResponseBody
	public <T extends ForbiddenException> ResponseEntity<ErrorResponse> handle403Exception(T exception) {

		log.error(exception.getMessage(), exception.getCause());

		return new ResponseEntity<>(new ErrorResponse(exception.getCode(), exception.getMessage()), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(NotFoundException.class)
	@ResponseBody
	public <T extends NotFoundException> ResponseEntity<ErrorResponse> handle404Exception(T exception) {

		log.error(exception.getMessage(), exception.getCause());

		return new ResponseEntity<>(new ErrorResponse(exception.getCode(), exception.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ConflictException.class)
	@ResponseBody
	public <T extends ConflictException> ResponseEntity<ErrorResponse> handle409Exception(T exception) {

		log.error(exception.getMessage(), exception.getCause());

		return new ResponseEntity<>(new ErrorResponse(exception.getCode(), exception.getMessage()), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(GenericException.class)
	@ResponseBody
	public <T extends GenericException> ResponseEntity<ErrorResponse> handle500Exception(T exception) {

		log.error(exception.getMessage(), exception.getCause());

		return new ResponseEntity<>(new ErrorResponse(exception.getCode(), exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}