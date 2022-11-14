package com.cognixia.jump.exception;

public class DuplicateCypherException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public DuplicateCypherException(String msg) {
		super(msg);
	}
}
