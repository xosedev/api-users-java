package com.ytulink.user.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.ytulink.user.entity.ShortCut;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */
@Transactional
public interface ShortCutRepository extends BasicRepository<ShortCut,Long> {

	@Query("select c from ShortCut c where c.deleted = false and c.user.id= :userId")
	Page<ShortCut> findAllByUserId(Long userId,Pageable pageable);
	
}