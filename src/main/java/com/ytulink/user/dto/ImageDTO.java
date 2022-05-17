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
public class ImageDTO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -482599430395459714L;
	private Long id;
	private String urlImg;
	private boolean principal;
}