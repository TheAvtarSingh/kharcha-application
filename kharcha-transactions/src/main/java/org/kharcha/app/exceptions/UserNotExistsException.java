package org.kharcha.app.exceptions;

public class UserNotExistsException extends RuntimeException {
private static final long serialVersionUID = 1L;

public UserNotExistsException(String message) {
	super(message);
}
}
