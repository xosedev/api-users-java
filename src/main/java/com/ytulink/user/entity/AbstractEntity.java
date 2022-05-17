package com.ytulink.user.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

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
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class AbstractEntity  implements Serializable {
	
	/**
	 *serialVersionUID 
	 */
	private static final long serialVersionUID = 4093847594626385662L;

	@Column(name = "insert_date", nullable = false)
	private Date insertDate = new Date();

	@Column(name = "deleted", nullable = false)
	private boolean deleted = false;

	@Column(name = "update_date")
	private Date updateDate;

	@Version
	@Column(name = "version", nullable = false)
	private Integer version;

	abstract public Long getId();
}