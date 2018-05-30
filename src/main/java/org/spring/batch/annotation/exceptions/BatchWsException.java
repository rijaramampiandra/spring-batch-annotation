package org.spring.batch.annotation.exceptions;

/**
 * Checked exception when we have error.
 * 
 * @author Rija RAMAMPIANDRA.
 *
 */
public class BatchWsException extends Exception {

	private static final long serialVersionUID = -4540332502721104884L;

	public BatchWsException(String message) {
		super(message);
	}

	public BatchWsException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BatchWsException(Throwable arg0) {
		super(arg0);
	}

}
