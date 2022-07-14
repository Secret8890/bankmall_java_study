package com.template.api.apps.partnerlogo.dto;

import com.bankmall.apps.partnerlogo.domain.PartnerLogo;
import com.bankmall.dto.BaseDtoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface PartnerLogoDtoMapper extends BaseDtoMapper{

    PartnerLogoDtoMapper INSTANCE = Mappers.getMapper(PartnerLogoDtoMapper.class);

    List<PartnerLogoDto.Response> toResponses(List <PartnerLogo> partnerlogo);

    PartnerLogo create(PartnerLogoDto.Create create);

    PartnerLogoDto.Response toResponses(PartnerLogo partnerLogo);

     PartnerLogo update(PartnerLogoDto.Update update, @MappingTarget PartnerLogo partnerlogo);

    PartnerLogoDto detail(@PathVariable Long id, PartnerLogo partnerlogo);
}
