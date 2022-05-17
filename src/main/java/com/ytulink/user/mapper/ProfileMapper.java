package com.ytulink.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ytulink.user.dto.ProfileDTO;
import com.ytulink.user.entity.Profile;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */
@Mapper(componentModel = "spring")
public interface ProfileMapper extends EntityMapper<ProfileDTO, Profile> {
	ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

	@Mapping(source = "user.id",target = "userId")
	ProfileDTO toDto(Profile entity);
}