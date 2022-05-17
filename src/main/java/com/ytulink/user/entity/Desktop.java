package com.ytulink.user.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "desktop", schema = ContantsEntity.SCHEMA)
public class Desktop extends AbstractEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3209362727915894349L;

	@Id
	@Column(name = "desktop_id")
	@GenericGenerator(name = "desktop_generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = ContantsEntity.SCHEMA + ".desktop_id_seq"),
			@Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	@GeneratedValue(generator = "desktop_generator")
	@NotNull
	private Long id;

	@Column(name = "name")
	@NotNull
	private String name;


	@Column(name = "private")
	@NotNull
	private boolean privado;

	@JsonIgnore
	@OneToOne(fetch = javax.persistence.FetchType.EAGER)
	@JoinColumn(nullable = false, referencedColumnName = "desktop_sub_category_id", name = "desktop_sub_category", insertable = true, updatable = true)
	@NotNull
	private DesktopSubCategory desktopSubCategory;


	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(schema = ContantsEntity.SCHEMA, name = "desktop_user", joinColumns = @JoinColumn(name = "desktop_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false))
	List<User> user;

	@ManyToOne
	@JoinColumn(name = "create_user_id")
	private User createUser;
	
    @Column(name = "main_desk", columnDefinition = "boolean default false")
    private boolean mainDesk;
    
    @Column(name = "icon_desk")
    private String iconDesk;
	

	public void addUser(User user){
		if(this.user == null){
			this.user = new ArrayList<>();
		}

		this.user.add(user);
	}

}