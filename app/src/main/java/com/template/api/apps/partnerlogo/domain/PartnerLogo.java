package com.template.api.apps.partnerlogo.domain;


import com.template.api.apps.partnerlogo.dto.PartnerLogoDto;
import com.template.api.apps.partnerlogo.dto.PartnerLogoDtoMapper;
import com.template.api.jpa.base.BaseDomainWithId;
import com.template.api.jpa.base.DomainWithMapper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter @Setter
@Entity
@Table
public class PartnerLogo extends BaseDomainWithId implements DomainWithMapper<PartnerLogoDto.Response> {

    @Column(nullable = false, unique = true)
    private String partnerName ; // 파트너사 명칭
    private String URL; // URL 주소
    private Boolean isActive = Boolean.TRUE; //
    private String pcImage ; // PC이미지
    private String moImage; // 모바일 이미지


    @Override
    public PartnerLogoDto.Response toResponse() {
        return PartnerLogoDtoMapper.INSTANCE.toResponse(this);
    }
}
