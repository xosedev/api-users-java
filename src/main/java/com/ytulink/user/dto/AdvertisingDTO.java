package com.ytulink.user.dto;

import java.io.Serializable;
import java.util.Date;

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
public class AdvertisingDTO implements Serializable {
	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = -3489218516829033759L;

	private Long id;
	
	@NotEmpty
	@NotNull(message = UtilConstant.NOT_NUll)	
	private long userId;
	
	@NotEmpty
	@NotNull(message = UtilConstant.NOT_NUll)	
	private String text;

	@NotEmpty
	@NotNull(message = UtilConstant.NOT_NUll)	
	private String urlImg;
	
	@NotEmpty
	@NotNull(message = UtilConstant.NOT_NUll)	
	private String position;
	
	@NotEmpty
	@NotNull(message = UtilConstant.NOT_NUll)	
	private Date initDate;
		
	@NotEmpty
	@NotNull(message = UtilConstant.NOT_NUll)	
	private Date endDate;
	
	@NotEmpty
	@NotNull(message = UtilConstant.NOT_NUll)	
	private Integer planNumber;
	
	
	
	
}
