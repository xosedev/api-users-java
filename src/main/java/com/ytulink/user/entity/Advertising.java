package com.ytulink.user.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

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
@Table(name = "advertising", schema = ContantsEntity.SCHEMA)
@Getter
@Setter
public class Advertising extends AbstractEntity {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3130602782229928524L;

	
	@Id
	@Column(name = "advertising_id")
	@GenericGenerator(name = "advertising_generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = ContantsEntity.SCHEMA +".advertising_seq"),
			@Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	@GeneratedValue(generator = "advertising_generator")
    private Long id;


	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
		
	@Column(name = "text",columnDefinition="TEXT")
	private String text;
	
	@Column(name = "url_img")
	private String urlImg;
	
	@Column(name = "position")
	private String position;
		
	@Column(name = "init_date")
	private Date initDate;
		
	@Column(name = "end_date")
	private Date endDate;
	
	@Column(name = "plan_number")
	private Integer planNumber;
	
	
	
}