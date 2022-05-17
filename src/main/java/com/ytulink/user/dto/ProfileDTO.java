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
public class ProfileDTO implements Serializable {
	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = -3489218516829033759L;

	private Long id;
	private Long userId;
	private String shortcutsPosition;
	private String usernameColor;
	private String shortcutColorBackground;
	private String shortcutColorLabel;
	private String linkColorLabel;
	private String linkColorBackground;
	private String linkColorBorder;
	private String linkAnimation;
	private String linkBorderHeight;
	private String portfolioBackground;
	
	
	
}
