package org.kharcha.app.exceptions;

public class TransactionAlreadyExistsException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public TransactionAlreadyExistsException(String message) {
		super(message);
	}
}
