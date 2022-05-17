package com.ytulink.user.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ytulink.user.entity.TypePlan;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */
@Repository
public interface ITypePlanRepository extends BasicRepository<TypePlan, Long>, JpaSpecificationExecutor<TypePlan> {
}