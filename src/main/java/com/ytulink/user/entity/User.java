package com.ytulink.user.entity;


import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "user", schema = ContantsEntity.SCHEMA)
@Getter
@Setter
public class User extends AbstractEntity {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3130602782229928524L;

	
	@Id
	@Column(name = "user_id")
	@GenericGenerator(name = "user_generator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = ContantsEntity.SCHEMA +".user_seq"),
			@Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1") })
	@GeneratedValue(generator = "user_generator")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "fire_base_unique_user_id")
    private String idFireBase;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "about",columnDefinition="TEXT")
    private String about;
    
    @Column(name = "user_name")
    private String userName;
    
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birthday")
    private String birthday;
    
    @Column(name = "phone_code")
    private String phoneCode;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "sex")
    private String sex;
    
    @Column(name = "correlativo")
    private String correlativo;
    
    
    @Column(name = "private_profile",columnDefinition = "boolean default false")
    private boolean privateProfile;
    
    @Column(name = "url_img_cover_page")
    private String urlImgCoverPage;
    
    @Column(name = "activate", columnDefinition = "boolean default false")
    private boolean activate;
    
    @Column(name = "register_complete", columnDefinition = "boolean default false")
    private boolean registerComplete;
    
    @OneToMany(mappedBy = "user")
	private Set<Image> image;
    
    @JsonIgnore
	@ManyToMany(mappedBy = "user")
	List<Desktop> desktop;
    
    @OneToOne(mappedBy = "user")
    private Profile profile;
    
}