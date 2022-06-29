package com.engineeringwithramaa.inmemorydatagrid.advice;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.engineeringwithramaa.inmemorydatagrid.exception.OrderNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(OrderNotFoundException.class)
	public Map<String, String> handleOrderNotFoundException(OrderNotFoundException ex) {
		// include error handling
		Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return errorMap;
		
	}
}