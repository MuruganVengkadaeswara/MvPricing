package com.onebill.pricing.controllers;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.onebill.pricing.dto.ResponseDto;
import com.onebill.pricing.exceptions.PricingConflictsException;
import com.onebill.pricing.exceptions.PricingException;
import com.onebill.pricing.exceptions.PricingNotFoundException;

import javassist.NotFoundException;

import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class PricingExceptionHandler {

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	ResponseDto handler(Exception e) {

		ResponseDto resp = new ResponseDto();

		resp.setError(true);

		resp.setResponse(e.getMessage());

		return resp;
	}

	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(ConstraintViolationException.class)
	ResponseDto handleInvalidData(Exception e) {
		ResponseDto resp = new ResponseDto();

		resp.setError(true);

		resp.setResponse("Some fields are either missing or Invalid");

		return resp;
	}

	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(PersistenceException.class)
	ResponseDto handleDuplicateData(Exception e) {
		ResponseDto resp = new ResponseDto();

		resp.setError(true);

		resp.setResponse(e.getMessage());

		return resp;
	}

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	ResponseDto handleNotFound(Exception e) {
		ResponseDto resp = new ResponseDto();

		resp.setError(true);

		resp.setResponse(e.getMessage());

		return resp;
	}

	@ExceptionHandler(PricingNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	ResponseDto handlePricingNotFound(Exception e) {
		ResponseDto resp = new ResponseDto();

		resp.setError(true);

		resp.setResponse(e.getMessage());

		return resp;
	}

	@ExceptionHandler(PricingException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	ResponseDto handleCommon(Exception e) {
		ResponseDto resp = new ResponseDto();

		resp.setError(true);

		resp.setResponse(e.getMessage());

		return resp;

	}

	@ExceptionHandler(PricingConflictsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	ResponseDto handleConflicts(Exception e) {
		ResponseDto resp = new ResponseDto();

		resp.setError(true);

		resp.setResponse(e.getMessage());

		return resp;

	}

}
