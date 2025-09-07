package org.kharcha.app.exceptions;

public class TransactionNotExistsException extends RuntimeException {
private static final long serialVersionUID = 1L;

public TransactionNotExistsException(String message) {
	super(message);
}
}
