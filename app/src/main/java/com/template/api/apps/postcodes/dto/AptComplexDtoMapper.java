package com.template.api.apps.postcodes.dto;

import com.template.api.apps.postcodes.domain.AptComplex;
import com.template.api.utils.interfaces.BaseDtoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Map;

@Mapper
public interface AptComplexDtoMapper extends BaseDtoMapper {

    AptComplexDtoMapper INSTANCE = Mappers.getMapper(AptComplexDtoMapper.class);

    AptComplexDto.Response mapToResponse(Map<String, String> item);
    AptComplexDto.KbResponse toKbResponse(AptComplex aptComplex);

}
