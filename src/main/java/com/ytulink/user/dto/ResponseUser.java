package com.ytulink.user.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
public class ResponseUser implements Serializable {
	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = -3489218516829033759L;

		private Long id;
		private String email;
		private String idFireBase;
		private String name;
		private String lastName;
		private String birthday;
		private String phoneCode;
		private String about;
		private String userName;
		private String phone;
		private String sex;
		private Date insertDate;
		private boolean privateProfile;
		private String imgCoverPage;
		private boolean  activo;
		private boolean registerComplete;
		private List<ImageDTO> userImg;
}
