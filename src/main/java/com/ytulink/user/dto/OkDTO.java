package com.ytulink.user.dto;

import java.io.Serializable;

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
public class OkDTO implements Serializable {
	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = -3489218516829033759L;

   private String ok;
}
