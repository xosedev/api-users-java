package com.ytulink.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ytulink.user.dto.TypeShortCutDTO;
import com.ytulink.user.entity.TypeShortCut;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */
@Mapper(componentModel = "spring")
public interface TypeShortCutMapper extends EntityMapper<TypeShortCutDTO, TypeShortCut> {
	TypeShortCutMapper INSTANCE = Mappers.getMapper(TypeShortCutMapper.class);
	TypeShortCutDTO toDto(TypeShortCut out);
}