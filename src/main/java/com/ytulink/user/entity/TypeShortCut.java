package com.ytulink.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.ytulink.user.constants.ContantsEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ytulink.com Propiedad de : Jose Miguel Vasquez Jose Toro Montencinos
 *         Pablo Staub Ramirez
 */
@Entity
@Table(name = "type_shortcut", schema = ContantsEntity.SCHEMA)
@Getter
@Setter
public class TypeShortCut extends AbstractEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3130602782229928524L;

	@Id
	@Column(name = "type_shortcut_id")
	@GenericGenerator(name = "type_shortcut_generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = ContantsEntity.SCHEMA + ".type_shortcut_seq"),
			@Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	@GeneratedValue(generator = "type_shortcut_generator")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "icon")
	private String icon;
	

	@Column(name = "url")
	private String url;
}