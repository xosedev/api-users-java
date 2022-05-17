package com.ytulink.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.sun.istack.NotNull;
import com.ytulink.user.constants.ContantsEntity;

import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */
@Entity
@Table(name = "image", schema = ContantsEntity.SCHEMA)
@Getter
@Setter
public class Image extends AbstractEntity {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3130602782229928524L;

	@Id
	@Column(name = "image_id")
	@GenericGenerator(name = "imageId_generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = ContantsEntity.SCHEMA +".imageId_seq"),
			@Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	@GeneratedValue(generator = "imageId_generator")
    private Long id;
	
	@Column (name = "name")
	@NotNull
	private String name;
	
	@Column (name = "urlImg")
	private String urlImg;
	
	@Column(name = "principal", columnDefinition = "boolean default false")
    private boolean principal;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
