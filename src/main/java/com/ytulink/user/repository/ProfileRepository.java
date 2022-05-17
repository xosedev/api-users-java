package com.ytulink.user.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.ytulink.user.entity.Profile;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */
@Transactional
public interface ProfileRepository extends BasicRepository<Profile,Long> {

	@Query("select p from Profile p where p.deleted = false and p.user.deleted=false and  p.user.id= :userId")
	Page<Profile> findAllByUserId(Long userId,Pageable pageable);
	
	@Query("select p from Profile p where p.deleted = false and p.user.deleted=false and  p.user.id= :userId")
	Optional<Profile> findByUserId(Long userId);
	
	
}