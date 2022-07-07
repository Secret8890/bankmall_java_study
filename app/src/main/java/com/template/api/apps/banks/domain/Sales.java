package com.template.api.apps.banks.domain;

import com.template.api.apps.banks.dto.BankDto;
import com.template.api.apps.banks.dto.BankDtoMapper;
import com.template.api.jpa.base.BaseDomainWithId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;


@Entity
@Getter
@Setter
public class Sales extends BaseDomainWithId {
    @ManyToOne(fetch = FetchType.LAZY)
    private Bank bank;

    private String discountCondition; // 할인항목

    private Double discountRate; // 할인금리
    public BankDto.SaleResponse toResponse() {
        return BankDtoMapper.INSTANCE.toResponse(this);
    }

}