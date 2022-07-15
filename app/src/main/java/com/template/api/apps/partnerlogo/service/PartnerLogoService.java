package com.template.api.apps.partnerlogo.service;

import com.template.api.apps.partnerlogo.domain.PartnerLogoRepository;
import com.template.api.apps.partnerlogo.dto.PartnerLogoDto;
import com.template.api.utils.dtos.PagableDto;

public interface PartnerLogoService {

    PagableDto.Response<PartnerLogoDto.Response>getPagePartnerLogos(PartnerLogoDto.Request request);
    void createPartnerLogo(PartnerLogoDto.Create create);
    void updatePartnerLogo(Long id, PartnerLogoDto.Update update);
    void delete(Long id);

}
