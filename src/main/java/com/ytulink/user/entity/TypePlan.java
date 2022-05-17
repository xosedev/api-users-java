package com.ytulink.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.ytulink.user.constants.ContantsEntity;

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
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "type_plan", schema = ContantsEntity.SCHEMA)
public class TypePlan extends AbstractEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3209362727915894349L;

	@Id
	@Column(name = "type_plan_id")
	@GenericGenerator(name = "typePlan_generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = ContantsEntity.SCHEMA + ".type_plan_id_seq"),
			@Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	@GeneratedValue(generator = "typePlan_generator")
	@NotNull
	private Long id;

	@Column(name = "name")
	@NotNull
	private String name;
	
	@Column(name = "quantity_desktop")
	@NotNull
	private Integer quantityDesktop;
	
	@Column(name = "quantity_link")
	@NotNull
	private Integer quantityLink;
}