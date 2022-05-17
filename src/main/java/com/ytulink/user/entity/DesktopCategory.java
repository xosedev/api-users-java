package com.ytulink.user.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.sun.istack.NotNull;
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
@Table(name = "desktop_category", schema = ContantsEntity.SCHEMA)
public class DesktopCategory extends AbstractEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2048622214010110704L;

	@Id
	@Column(name = "desktop_category_id")
	@GenericGenerator(name = "desktopCategoryId_generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = ContantsEntity.SCHEMA +".desktop_category_id_seq"),
			@Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	@GeneratedValue(generator = "desktopCategoryId_generator")
	@NotNull
	private Long id;

	@Column (name = "name")
	@NotNull
	private String name;

	@OneToMany(mappedBy = "desktopCategory")
	private Set<DesktopSubCategory> desktopSubCategory;

}