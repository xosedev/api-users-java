package com.ytulink.user.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ytulink.user.entity.Image;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */
@Repository
public interface IImageRepository extends BasicRepository<Image, Long> {
	
	List<Image> findByUserId(Long id);
}