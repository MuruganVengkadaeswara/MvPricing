package com.onebill.pricing.controllers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.onebill.pricing.dto.ResponseDto;

@RestControllerAdvice
public class PricingExceptionHandler {

	@ExceptionHandler
	ResponseDto handler(Exception e) {

		ResponseDto resp = new ResponseDto();

		resp.setError(true);

		resp.setResponse(e.getMessage());

		return resp;
	}

}
