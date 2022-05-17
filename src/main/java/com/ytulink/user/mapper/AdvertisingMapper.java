package com.ytulink.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ytulink.user.dto.AdvertisingDTO;
import com.ytulink.user.entity.Advertising;
import com.ytulink.user.entity.ShortCut;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */
@Mapper(componentModel = "spring")
public interface AdvertisingMapper extends EntityMapper<AdvertisingDTO, Advertising> {
	AdvertisingMapper INSTANCE = Mappers.getMapper(AdvertisingMapper.class);
	@Mapping(source = "user.id",target = "userId")
	AdvertisingDTO toDto(ShortCut entity);
}