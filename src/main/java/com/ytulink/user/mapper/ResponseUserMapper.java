package com.ytulink.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ytulink.user.dto.ResponseUser;
import com.ytulink.user.entity.User;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */
@Mapper(uses= {ImageMapper.class},componentModel = "spring")
public interface ResponseUserMapper extends EntityMapper<ResponseUser, User> {
	ResponseUserMapper INSTANCE = Mappers.getMapper(ResponseUserMapper.class);
	@Mapping(source = "image",target = "userImg")
	@Mapping(source = "urlImgCoverPage",target = "imgCoverPage")
	ResponseUser toDto(User entity);
}