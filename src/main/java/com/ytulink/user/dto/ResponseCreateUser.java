package com.ytulink.user.dto;

import java.io.Serializable;
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
public class ResponseCreateUser implements Serializable{

	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = 8022584417401515689L;
	private List<UploadFileResponse>  imgs;
}