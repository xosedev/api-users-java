package com.ytulink.user.exception;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */
public class FileStorageException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -791459364573589116L;

	public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}