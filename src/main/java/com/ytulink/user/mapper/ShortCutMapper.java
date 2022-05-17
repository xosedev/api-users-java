package com.ytulink.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ytulink.user.dto.ShortCutDTO;
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
public interface ShortCutMapper extends EntityMapper<ShortCutDTO, ShortCut> {
	ShortCutMapper INSTANCE = Mappers.getMapper(ShortCutMapper.class);
	@Mapping(source = "user.id",target = "userId")
	@Mapping(source = "typeShortCut.id",target = "typeShortcutId")
	@Mapping(source = "typeShortCut.icon",target = "typeShorcutIcon")
	@Mapping(source = "typeShortCut.name",target = "typeShorcutName")
	@Mapping(source = "typeShortCut.url",target = "typeShorcutUrl")
	ShortCutDTO toDto(ShortCut entity);
}