package com.ytulink.user.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */
public class UtilConstant {

	private UtilConstant() {
		throw new IllegalStateException("Utility class");
	}
	
	public static final String NOT_NUll = "No puede ser nulo";
	
    /**
     * Patron para validar email
     *
     * @param email
     * @return
     */
    public static boolean ValidarEmail(String email) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);
        return mather.find();
    }
}