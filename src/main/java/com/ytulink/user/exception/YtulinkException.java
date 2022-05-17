package com.ytulink.user.exception;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */
public class YtulinkException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1099302941325240995L;

	public YtulinkException(String message) {
		super(message);
	}

	public YtulinkException(String message, Throwable cause) {
		super(message, cause);
	}
}