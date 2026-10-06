package org.emoflon.gips.core.api;

public class TimeoutException extends RuntimeException {

	private static final long serialVersionUID = 846161817310284615L;

	public TimeoutException() {
		super();
	}

	public TimeoutException(String message) {
		super(message);
	}

	public TimeoutException(String message, Throwable cause) {
		super(message, cause);
	}

	public TimeoutException(Throwable cause) {
		super(cause);
	}
}
