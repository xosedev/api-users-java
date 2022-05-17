package com.ytulink.user.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;

import com.ytulink.user.entity.User;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */
@Transactional
public interface IUserRepository extends BasicRepository<User,Long> {
	Optional<User> findByIdFireBase(String idFireBase);
	Optional<User> findByEmail(String email);
	Optional<User> findByName(String name);
	Optional<User> findByUserName(String name);	
	
	@Query("select u from User u left join Image i on (i.user.id = u.id) where u.deleted = false and u.id= :userId ")
	Optional<User>  findByUserIdImg(Long userId);
	
}