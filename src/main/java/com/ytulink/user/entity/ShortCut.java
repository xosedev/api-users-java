package com.ytulink.user.entity;

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
import com.google.firebase.database.annotations.NotNull;
import com.ytulink.user.constants.ContantsEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ytulink.com Propiedad de : Jose Miguel Vasquez Jose Toro Montencinos
 *         Pablo Staub Ramirez
 */
@Entity
@Table(name = "shortcut", schema = ContantsEntity.SCHEMA)
@Getter
@Setter
public class ShortCut extends AbstractEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3130602782229928524L;

	@Id
	@Column(name = "shortcut_id")
	@GenericGenerator(name = "shortcut_generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = ContantsEntity.SCHEMA + ".shortcut_seq"),
			@Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	@GeneratedValue(generator = "shortcut_generator")
	private Long id;

	@Column(name = "name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	

	@JsonIgnore
	@OneToOne(fetch = javax.persistence.FetchType.EAGER)
	@JoinColumn(nullable = false, referencedColumnName = "type_shortcut_id", name = "type_shortcut", insertable = true, updatable = true)
	@NotNull
	private TypeShortCut typeShortCut;
}