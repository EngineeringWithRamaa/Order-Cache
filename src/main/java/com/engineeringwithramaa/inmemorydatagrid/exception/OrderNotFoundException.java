package com.engineeringwithramaa.inmemorydatagrid.exception;


public class OrderNotFoundException extends RuntimeException {
	/**
	 * Create the exception.
	 * @param message reason for the exception.
	 */
	public OrderNotFoundException(String message) {
		super(message);
	}
}
