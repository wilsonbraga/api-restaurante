package com.wilson.braga.restaurante.exception;

public class OrdenacaoInvalidaException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public OrdenacaoInvalidaException(String message) {
		super(message);
	}

}
