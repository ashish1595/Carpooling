package com.carpool.server.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.carpool.server.exception.ResponseCode;
import com.carpool.server.exception.UtilityException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

	@ExceptionHandler(value = {Exception.class})
	protected ResponseEntity<?> handleUnknownException(Exception ex, WebRequest request) {
		logger.error("handleUnknownException :: exception <<<," + ex.getMessage(), ex);
		return RestUtils.errorResponseEntity(ResponseCode.GENRAL_ERROR.getDescription(),
				UtilityException.DEFAULT_HTTP_STATUS);
	}

	@ExceptionHandler(value = {UtilityException.class})
	protected ResponseEntity<?> handleknownException(UtilityException ex, WebRequest request) {
		if(!CommonUtility.isNullObject(ex.getResponseCode())){
			logger.error("Custom Exception:: Error Code ::<<<<<<<< "+ ex.getResponseCode().getCode() + 
					"Custom Exception:: Error Description ::"+ ex.getResponseCode().getDescription());
		}else{
			logger.error("General Exception:: Error Description"+ex.getMessage());
		}
		return RestUtils.errorResponseData((CommonUtility.isNullObject(ex.getResponseCode())?
			ResponseCode.GENRAL_ERROR : ex.getResponseCode()), HttpStatus.OK);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<Object>(ResponseCode.GENRAL_ERROR.getDescription(), HttpStatus.BAD_REQUEST);
	}

}