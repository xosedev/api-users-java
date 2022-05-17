package com.ytulink.user.exception;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */
public class FTPErrors extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4733141478626673007L;
	private ErrorMessage errorMessage;

    public FTPErrors(ErrorMessage errorMessage) {
        super(errorMessage.getErrormessage());
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}