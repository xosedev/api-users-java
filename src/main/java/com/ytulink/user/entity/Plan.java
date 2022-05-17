package com.ytulink.user.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "plan", schema = ContantsEntity.SCHEMA)
@Getter
@Setter
public class Plan extends AbstractEntity {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5698319494350289214L;


	@Id
	@Column(name = "plan_id")
	@GenericGenerator(name = "planId_generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = ContantsEntity.SCHEMA +".plan_id_seq"),
			@Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	@GeneratedValue(generator = "planId_generator")
	@NotNull
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "start_date")
	private Date startDate;
	
	@Column(name = "end_date")
	private Date endDate;

	@JsonIgnore
	@OneToOne(fetch = javax.persistence.FetchType.EAGER)
	@JoinColumn(nullable = false, referencedColumnName = "type_plan_id", name = "type_plan", insertable = true, updatable = true)
	@NotNull
	private TypePlan typePlan;
}