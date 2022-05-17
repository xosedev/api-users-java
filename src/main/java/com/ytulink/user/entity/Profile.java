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
import com.sun.istack.NotNull;
import com.ytulink.user.constants.ContantsEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ytulink.com Propiedad de : Jose Miguel Vasquez Jose Toro Montencinos
 *         Pablo Staub Ramirez
 */
@Entity
@Table(name = "profile", schema = ContantsEntity.SCHEMA)
@Getter
@Setter
public class Profile extends AbstractEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3130602782229928524L;

	@Id
	@Column(name = "profile_id")
	@GenericGenerator(name = "profile_generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = ContantsEntity.SCHEMA + ".profile_seq"),
			@Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	@GeneratedValue(generator = "profile_generator")
	private Long id;

	@JsonIgnore
	@OneToOne(fetch = javax.persistence.FetchType.EAGER)
	@JoinColumn(nullable = false, referencedColumnName = "user_id", name = "user_id", insertable = true, updatable = true)
	@NotNull
	private User user;

	@Column(name = "shortcutsPosition")
	private String shortcutsPosition;

	@Column(name = "usernameColor")
	private String usernameColor;

	@Column(name = "shortcutColorBackground")
	private String shortcutColorBackground;

	@Column(name = "shortcutColorLabel")
	private String shortcutColorLabel;

	@Column(name = "linkColorLabel")
	private String linkColorLabel;

	@Column(name = "linkColorBackground")
	private String linkColorBackground;

	@Column(name = "linkColorBorder")
	private String linkColorBorder;

	@Column(name = "linkAnimation")
	private String linkAnimation;

	@Column(name = "linkBorderHeight")
	private String linkBorderHeight;

	@Column(name = "portfolioBackground")
	private String portfolioBackground;

}