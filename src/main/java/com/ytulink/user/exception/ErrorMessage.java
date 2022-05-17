package com.ytulink.user.exception;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */
public class ErrorMessage {
	private int errorcode;
	private String errormessage;

	public ErrorMessage(int errorcode, String errormessage) {
		this.errorcode = errorcode;
		this.errormessage = errormessage;
	}

	public int getErrorcode() {
		return errorcode;
	}

	public String getErrormessage() {
		return errormessage;
	}

	@Override
	public String toString() {
		return "ErrorMessage{" + "errorcode=" + errorcode + ", errormessage='" + errormessage + '\'' + '}';
	}
}
