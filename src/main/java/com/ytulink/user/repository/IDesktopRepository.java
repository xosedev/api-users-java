package com.ytulink.user.repository;

import javax.transaction.Transactional;

import com.ytulink.user.entity.Desktop;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */
@Transactional
public interface IDesktopRepository extends BasicRepository<Desktop,Long> {
}