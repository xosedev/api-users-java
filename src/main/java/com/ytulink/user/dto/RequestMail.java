package com.ytulink.user.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestMail implements Serializable{
	/**
	 * serialVersionUID 
	 */	
	private static final long serialVersionUID = -5562094935456366411L;
		private String toEmail; 
		private String subject; 
		private String message;
		private String template;
}