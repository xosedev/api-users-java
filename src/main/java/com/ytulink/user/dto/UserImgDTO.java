package com.ytulink.user.dto;

import java.io.Serializable;
import java.util.List;

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
public class UserImgDTO implements Serializable {
	/**
	 *serialVersionUID
	 */
	private static final long serialVersionUID = -1046946857968550955L;

		private Long id;
		@NotEmpty
		@Email
		@NotNull(message = UtilConstant.NOT_NUll)
		private String email;
		private String name;
		@NotEmpty
		@NotNull(message = UtilConstant.NOT_NUll)
		private String userName;
		private String about;
		private String lastName;
		private String birthday;
		private String phoneCode;
		private String phone;
		private String sex;
		private boolean privateProfile;
		private String imgCoverPage;
		private boolean activate;
		private boolean registerComplete;
		private List<ImageDTO> userImg;
}