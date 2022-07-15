package com.template.api.apps.partnerlogo.dto;

import com.template.api.apps.partnerlogo.domain.PartnerLogo;
import com.template.api.utils.interfaces.BaseDtoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PartnerLogoDtoMapper extends BaseDtoMapper<PartnerLogo, PartnerLogoDto.Response, PartnerLogoDto.Create, PartnerLogoDto.Update> {

    PartnerLogoDtoMapper INSTANCE = Mappers.getMapper(PartnerLogoDtoMapper.class);
    PartnerLogoDto.Response toResponse(PartnerLogo partnerLogo);
    PartnerLogo create(PartnerLogoDto.Create create);
    List<PartnerLogoDto.Response> toResponse(List<PartnerLogoDto> partnerLogos);
    void update(PartnerLogoDto.Update update,@MappingTarget PartnerLogo partnerLogo);



}
