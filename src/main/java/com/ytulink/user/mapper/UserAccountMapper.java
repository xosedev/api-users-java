package com.ytulink.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ytulink.user.dto.UserAccountDTO;
import com.ytulink.user.entity.User;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */
@Mapper(componentModel = "spring")
public interface UserAccountMapper extends EntityMapper<UserAccountDTO, User> {
	UserAccountMapper INSTANCE = Mappers.getMapper(UserAccountMapper.class);
}