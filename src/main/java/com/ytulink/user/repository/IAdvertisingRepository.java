package com.ytulink.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ytulink.user.entity.Advertising;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */
@Repository
public interface IAdvertisingRepository extends BasicRepository<Advertising, Long> {
	
	@Query("select a from Advertising a where a.deleted = false and a.user.id= :userId")
	Page<Advertising> findAllByUserId(Long userId,Pageable pageable);
}