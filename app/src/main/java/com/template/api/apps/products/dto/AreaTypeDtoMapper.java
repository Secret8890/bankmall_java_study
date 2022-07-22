package com.template.api.apps.products.dto;

import com.template.api.apps.products.domain.AreaType;
import com.template.api.utils.interfaces.BaseDtoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AreaTypeDtoMapper extends BaseDtoMapper {

    AreaTypeDtoMapper INSTANCE = Mappers.getMapper(AreaTypeDtoMapper.class);

    AreaTypeDto.Response entityToResponse(AreaType areaType);

    @Mapping(target = "type", source = "type")
    void updateFromDto(AreaTypeDto.Update dto, @MappingTarget AreaType areaType);

}
