package com.ytulink.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ytulink.user.entity.Plan;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */
@Repository
public interface IPlanRepository extends BasicRepository<Plan, Long>, JpaSpecificationExecutor<Plan> {
	
	
	@Query("select p from Plan p  LEFT JOIN User u on (u.id = p.user.id) where u.id = :userId "
			+ "and p.deleted = false and u.deleted = false")
	Optional<Plan> planByUserId(@Param("userId") Long userId);
	
	
	
	
	
	
	
}