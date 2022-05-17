package com.ytulink.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ytulink.user.dto.ImageDTO;
import com.ytulink.user.entity.Image;
/**
 * 
 * @author ytulink.com
 * Propiedad de : 
 * Jose Miguel Vasquez
 * Jose Toro Montencinos
 * Pablo Staub Ramirez
 */
@Mapper(componentModel = "spring")
public interface ImageMapper extends EntityMapper<ImageDTO, Image> {
	ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);
}	