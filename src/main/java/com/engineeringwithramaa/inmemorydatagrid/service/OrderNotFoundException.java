package com.engineeringwithramaa.inmemorydatagrid.service;


public class OrderNotFoundException extends RuntimeException {
	/**
	 * Create the exception.
	 * @param message reason for the exception.
	 */
	public OrderNotFoundException(String message) {
		super(message);
	}
}
