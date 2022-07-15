package com.template.api.apps.partnerlogo.service;


import com.amazonaws.services.kms.model.NotFoundException;
import com.template.api.apps.partnerlogo.domain.PartnerLogo;
import com.template.api.apps.partnerlogo.domain.PartnerLogoRepository;
import com.template.api.apps.partnerlogo.dto.PartnerLogoDto;
import com.template.api.apps.partnerlogo.dto.PartnerLogoDtoMapper;
import com.template.api.jpa.Restrictions;
import com.template.api.utils.dtos.PagableDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Primary
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Qualifier("PartnerServiceImpl")
public class PartnerLogoServiceImpl implements PartnerLogoService{

    private final PartnerLogoRepository partnerLogoRepository;


    @Override
    public PagableDto.Response<PartnerLogoDto.Response> getPagePartnerLogos(PartnerLogoDto.Request request) {

        Restrictions r = new Restrictions();
        r.eq("isActive", true);

        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLimit());
        Page<PartnerLogo> items = partnerLogoRepository.findAll(r.output(), pageRequest);
        PagableDto.Response<PartnerLogoDto.Response> pages = PagableDto.Response.of(items);

        return pages;
    }

    @Override
    @Transactional
    public void createPartnerLogo(PartnerLogoDto.Create create) {

        PartnerLogo partnerLogo = PartnerLogoDtoMapper.INSTANCE.create(create);
        partnerLogoRepository.save(partnerLogo);
    }

    @Override
    public void updatePartnerLogo(Long id, PartnerLogoDto.Update update) throws NotFoundException {
        PartnerLogo partnerLogo = partnerLogoRepository.findById(id).orElseThrow(()->new NotFoundException("없는 id 입니다"));
        PartnerLogoDtoMapper.INSTANCE.update(update, partnerLogo);
    }

    @Override
    public void delete(Long id) {
        partnerLogoRepository.deleteById(id);
    }
}
