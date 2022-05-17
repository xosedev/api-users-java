package com.ytulink.user.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.ytulink.user.utils.UtilConstant;

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
public class RequestreCoverPass implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1162251507043207992L;

		@NotEmpty
		@Email
		@NotNull(message = UtilConstant.NOT_NUll)
		private String email; 
		
		@NotEmpty
		@NotNull(message = UtilConstant.NOT_NUll)
		private String password;
		
		@NotEmpty
		@NotNull(message = UtilConstant.NOT_NUll)
		private String confirm;
		
		
		@NotEmpty
		@NotNull(message = UtilConstant.NOT_NUll)
		private String code;

}