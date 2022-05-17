package com.ytulink.user.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.ytulink.user.utils.UtilConstant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewUserDTO implements Serializable {
	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = -3489218516829033759L;

		private Long id;
		
		@NotEmpty
		@Email
		@NotNull(message = UtilConstant.NOT_NUll)
		private String email;
		
		@NotEmpty
		@NotNull(message = UtilConstant.NOT_NUll)
		private String password;
		
		private String idFireBase;
		private String name;
		@NotEmpty
		@NotNull(message = UtilConstant.NOT_NUll)
		private String userName;
		
		private String lastName;
		private String birthday;
		private String phoneCode;
		private String phone;
		private String sex;
		private boolean privateProfile;
		private String urlImgCoverPage;
		private boolean  activo;
		private boolean registerComplete;
		private String iconDesk;
}
