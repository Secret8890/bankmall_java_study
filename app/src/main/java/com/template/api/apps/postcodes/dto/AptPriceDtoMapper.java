package com.template.api.apps.postcodes.dto;

import com.template.api.apps.postcodes.domain.AptComplex;
import com.template.api.apps.postcodes.domain.AptSizeType;
import com.template.api.utils.interfaces.BaseDtoMapper;
import com.template.api.apps.postcodes.dto.AptPriceDto.Simple;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Map;

@Mapper
public interface AptPriceDtoMapper extends BaseDtoMapper {

    AptPriceDtoMapper INSTANCE = Mappers.getMapper(AptPriceDtoMapper.class);

    AptPriceDto.Response mapToResponse(Map<String, String> item);

    AptComplexDto.Response entityToResponse(AptComplex aptComplex);

    Simple mapToSimple(AptSizeType type);

}
