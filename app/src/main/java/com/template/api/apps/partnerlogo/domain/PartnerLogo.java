package com.template.api.apps.partnerlogo.domain;

import com.bankmall.apps.partnerlogo.dto.PartnerLogoDto;
import com.bankmall.apps.partnerlogo.dto.PartnerLogoDtoMapper;
import com.bankmall.jpa.base.BaseDomainWithId;
import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class PartnerLogo extends BaseDomainWithId {

    private String partnerName ; // 파트너사 명칭

    private String URL; // URL 주소

    private  Boolean isActive; //

    private String pcImage ; // PC이미지

    private String moImage; // 모바일 이미지

    @Transactional

    public PartnerLogoDto.Response toResponse() {
        return PartnerLogoDtoMapper.INSTANCE.toResponses(this);
    }
}


